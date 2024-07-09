/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.builder;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.crypto.NoSuchMechanismException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.internal.compiler.env.INameEnvironment;
import org.xml.sax.SAXException;

import net.sf.jasperreports.components.table.StandardTable;
import net.sf.jasperreports.data.AbstractClasspathAwareDataAdapterService;
import net.sf.jasperreports.eclipse.builder.jdt.JRJdtCompiler;
import net.sf.jasperreports.eclipse.builder.jdt.NameEnvironement;
import net.sf.jasperreports.eclipse.classpath.JavaProjectClassLoader;
import net.sf.jasperreports.eclipse.messages.Messages;
import net.sf.jasperreports.eclipse.util.FileExtension;
import net.sf.jasperreports.eclipse.util.FileUtils;
import net.sf.jasperreports.eclipse.util.xml.SourceLocation;
import net.sf.jasperreports.eclipse.util.xml.SourceTraceDigester;
import net.sf.jasperreports.engine.JRChild;
import net.sf.jasperreports.engine.JRElementGroup;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.design.JRCompilationSourceCode;
import net.sf.jasperreports.engine.design.JRCompilationUnit;
import net.sf.jasperreports.engine.design.JRCompiler;
import net.sf.jasperreports.engine.design.JRDesignComponentElement;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRValidationException;
import net.sf.jasperreports.engine.design.JRValidationFault;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRClassLoader;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRReportSaxParserFactory;
import net.sf.jasperreports.engine.xml.JRSaxParserFactory;
import net.sf.jasperreports.engine.xml.JRXmlDigesterFactory;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/*
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JasperReportCompiler.java 24 2007-03-09 17:50:27Z lucianc $
 */
public class JasperReportCompiler {
	private IProject project;
	private JasperReportErrorHandler errorHandler;
	private JRSaxParserFactory parserFactory;
	private SourceTraceDigester digester;
	private Map<String, JRCompiler> map = new HashMap<>();
	private Map<String, Boolean> mpack = new HashMap<>();
	private Map<String, byte[]> mtype = new HashMap<>();
	private JasperReportsContext jasperReportsContext;
	public static final String JSS_COMPATIBILITY_COMPILER_VERSION = "com.jaspersoft.studio.jr.compile.version"; //$NON-NLS-1$

	public JasperReportCompiler() {
	}

	// FIXME - review the way inner try-catch works involving different classloaders
	public JasperReport compileReport(JasperReportsContext jasperReportsContext, IFile jrxml, IProgressMonitor monitor)
			throws CoreException {
		try {
			if (this.jasperReportsContext != jasperReportsContext) {
				clean();
				this.jasperReportsContext = jasperReportsContext;
			}
			String str = jasperReportsContext.getProperty(JSS_COMPATIBILITY_COMPILER_VERSION);
			if (str != null && !str.isEmpty() && !str.equals("last") && !str.equals(JasperDesign.class.getPackage().getImplementationVersion())) {
				CompatibilityManager cm = CompatibilityManager.getInstance();
				String p = cm.getJRPath(str);
				if (p != null) {
					ClassLoader cl = cm.getClassLoader(str, jrxml.getProject());
					if (cl != null) {
						try {
							String fjrxml = jrxml.getRawLocation().toOSString();
							String fjasper = FileExtension.getCompiledFileName(fjrxml);
							ClassLoader oldLoader = Thread.currentThread().getContextClassLoader();
							try {
								Thread.currentThread().setContextClassLoader(cl);
								Class<?> cjcm = cl.loadClass(JasperCompileManager.class.getName());
								// Testing for more recent versions
								if(tryCompilationWithNewerMode(cjcm)) {
									Method m = cjcm.getDeclaredMethod("getDefaultInstance", new Class[] {});
									m.setAccessible(true);
									Object obj = m.invoke(cjcm, new Object[] {});
									m = cjcm.getDeclaredMethod("compileToFile", new Class[] { String.class, String.class });
									m.invoke(obj, new Object[] { fjrxml, fjasper });
								}
								else {
									// let's try compiling the old way
									Method oldM = cjcm.getMethod("compileReportToFile", String.class);
									oldM.invoke(null, fjrxml);
								}
							} finally {
								Thread.currentThread().setContextClassLoader(oldLoader);
							}
							// Be sure the newly built .jasper file is visible 
							jrxml.getParent().refreshLocal(IProject.DEPTH_ONE, new NullProgressMonitor());
							IPath jasperFilePath = jrxml.getFullPath().removeFileExtension().addFileExtension( FileExtension.JASPER);
							IFile jasperFile = ResourcesPlugin.getWorkspace().getRoot().getFile(jasperFilePath);
							jasperFile.setDerived(true, new NullProgressMonitor());
							
							return (JasperReport) JRLoader.loadObjectFromFile(fjasper);
						} catch (ClassNotFoundException | SecurityException | NoSuchMethodException |  
								IllegalArgumentException | IllegalAccessException e) {
							// something went wrong let's add a marker for that
							errorHandler.addMarker(new RuntimeException("Problem compiling the report, please check the console view for details"));
						} catch (InvocationTargetException e) {
							final Throwable ex = e.getTargetException();
							if (ex.getClass().getName().equals(JRValidationException.class.getName())) {
								JasperDesign jasperDesign = loadJasperDesign(jasperReportsContext, jrxml);
								setValidationMarkers(convertJRValidationException(ex), jasperDesign);
							} else if (ex.getClass().getName().equals(JRException.class.getName())) {
								errorHandler.addMarker(convertJRException(ex));
							} else {
								throw new RuntimeException(ex);
							}
							return null;
						}
					}
					// Fallback solution running manually the command and just in case prompting a JRConsole to show errors
					ConsoleExecuter consoleExecuter = new ConsoleExecuter(jrxml, p, jrxml.getParent().getLocation().toOSString());
					consoleExecuter.runCompilation();
					return null;
				}
			}
			setProject(jrxml.getProject());
			JasperDesign jasperDesign = loadJasperDesign(jasperReportsContext, jrxml);
			return compileReport(jasperReportsContext, jasperDesign, monitor);
		} catch (Exception e) {
			errorHandler.addMarker(e);
		}
		return null;
	}
	
	/*
	 * Utility method to check we can use the JasperReportsContext.
	 */
	private boolean tryCompilationWithNewerMode(Class<?> jasperCompilerManager) {
		try {
			jasperCompilerManager.getDeclaredMethod("getDefaultInstance");
		} catch (NoSuchMethodException e) {
			return false;
		}
		return true;
	}
	
	// FIXME - review the way the #compileReport method handles exception.
	// Different classloaders are involved that's why we are forced to use this.
	private static JRValidationException convertJRValidationException(Throwable e) {
		JRValidationException jrex = null;
		try {
			String msg = e.getMessage();
			Collection<JRValidationFault> arg = (Collection<JRValidationFault>) e.getClass()
					.getMethod("getFaults", new Class[] {}).invoke(e, new Object[] {});
			jrex = new JRValidationException(msg, arg);
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		}
		if (jrex == null)
			jrex = new JRValidationException(e.getMessage(), new ArrayList<JRValidationFault>());
		return jrex;
	}

	// FIXME - review the way the #compileReport method handles exception.
	// Different classloaders are involved that's why we are forced to use this.
	private static JRException convertJRException(Throwable e) {
		JRException jrex = null;
		try {
			String msg = e.getMessage();
			Object[] arg = (Object[]) e.getClass().getMethod("getArgs", new Class[] {}).invoke(e, new Object[] {});
			Throwable t = (Throwable) e.getClass().getMethod("getCause", new Class[] {}).invoke(e, new Object[] {});
			jrex = new JRException(msg, arg, t);
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		}
		if (jrex == null)
			jrex = new JRException(e.getMessage());
		return jrex;
	}

	public JasperReport compileReport(JasperReportsContext jasperReportsContext, JasperDesign jasperDesign,
			IProgressMonitor monitor) throws CoreException {
		if (this.jasperReportsContext != jasperReportsContext) {
			clean();
			this.jasperReportsContext = jasperReportsContext;
		}
		long start = System.currentTimeMillis();
		long bcomp = start;
		JasperReport report = null;
		String language = jasperDesign.getLanguage();
		try {
			createDigester();
			if (language == null || language.isEmpty()) {
				jasperDesign.setLanguage(JRReport.LANGUAGE_GROOVY);
				language = JRReport.LANGUAGE_GROOVY;
			}
			JRCompiler compiler = map.get(language);
			if (compiler == null) {
				if (JRReport.LANGUAGE_JAVA.equals(language)) {
					compiler = new JRJdtCompiler(jasperReportsContext) {

						@Override
						protected CompilerRequestor getCompilerRequestor(JRCompilationUnit[] units) {
							return new LocalCompilerRequestor(jasperReportsContext, this, units);
						}

						@Override
						protected INameEnvironment getNameEnvironment(JRCompilationUnit[] units) {
							return new NameEnvironement(this, units) {
								@Override
								protected boolean isPackage(String result) {
									// return super.isPackage(result);
									if (result.isEmpty())
										return true;
									Boolean isPack = mpack.get(result);
									if (isPack == null) {
										// System.out.println(result);
										isPack = super.isPackage(result);
										mpack.put(result, isPack);
									}
									return isPack;
								}

								@Override
								protected byte[] getResource(String name) throws JRException {
									if (mtype.containsKey(name))
										return mtype.get(name);
									byte[] bt = super.getResource(name);
									mtype.put(name, bt);
									return bt;
								}
							};

						}
					};
				} else {
					String compilerClassName = JRPropertiesUtil.getInstance(jasperReportsContext)
							.getProperty(JRCompiler.COMPILER_PREFIX + language);
					try {
						Class clazz = null;
						ClassLoader cl = (ClassLoader) jasperReportsContext
								.getValue(AbstractClasspathAwareDataAdapterService.CURRENT_CLASS_LOADER);
						if (cl != null)
							clazz = cl.loadClass(compilerClassName);
						else
							clazz = JRClassLoader.loadClassForName(compilerClassName);
						try {
							Constructor c = clazz.getDeclaredConstructor(JasperReportsContext.class, boolean.class);
							compiler = (JRCompiler) c.newInstance(jasperReportsContext, false);
						} catch (NoSuchMethodException nsme) {
							try {
								Constructor c = clazz.getDeclaredConstructor(JasperReportsContext.class);
								compiler = (JRCompiler) c.newInstance(jasperReportsContext);
							} catch (NoSuchMechanismException e1) {
								compiler = (JRCompiler) clazz.newInstance();
							}
						}
					} catch (Exception e) {
						throw new JRException(
								Messages.JasperReportCompiler_ErrorInitializationReportCompiler + compilerClassName, e);
					}
				}
				map.put(language, compiler);
			}
			bcomp = System.currentTimeMillis();

			ClassLoader oldLoader = Thread.currentThread().getContextClassLoader();
			try {
				ClassLoader cl = null;
				if (jasperReportsContext != null) {
					cl = (ClassLoader) jasperReportsContext
							.getValue(JavaProjectClassLoader.JAVA_PROJECT_CLASS_LOADER_KEY);
				}
				if (jasperReportsContext != null && cl != null)
					Thread.currentThread().setContextClassLoader(cl);
				report = compiler.compileReport(jasperDesign);
			} finally {
				Thread.currentThread().setContextClassLoader(oldLoader);
			}
		} catch (JRValidationException e) {
			setValidationMarkers(e, jasperDesign);
			// JasperReportsPlugin.getDefault().logInfo(
			// "Classloader: " +
			// StringDumpUtils.dumpClassloader(Thread.currentThread().getContextClassLoader()));
		} catch (JRException e) {
			errorHandler.addMarker(e);
			// JasperReportsPlugin.getDefault().logInfo(
			// "Classloader: " +
			// StringDumpUtils.dumpClassloader(Thread.currentThread().getContextClassLoader()));
		} catch (Throwable e) {
			// JasperReportsPlugin.getDefault().logInfo(
			// "Classloader: " +
			// StringDumpUtils.dumpClassloader(Thread.currentThread().getContextClassLoader()));
			throw new RuntimeException(e);
		}
		long end = System.currentTimeMillis();
		System.out.println("Compiled: " + language + " " + (bcomp - start) + " ms " + (end - start) + " ms");
		return report;
	}

	protected void setValidationMarkers(JRValidationException e, JasperDesign design) {
		for (JRValidationFault fault : e.getFaults()) {
			String message = fault.getMessage();
			SourceLocation location = null;
			Object source = fault.getSource();
			if (source != null) {
				location = digester.getLocation(source);
				if (location == null)
					message = message + " --- " + source.toString(); //$NON-NLS-1$
			}
			if (source instanceof StandardTable) {
				JRDesignElement componentElement = getElementFromTable(design.getAllBands(), (StandardTable) source);
				if (componentElement != null)
					source = componentElement;
			}
			if (location == null && source instanceof JRDesignElement)
				errorHandler.addMarker(message, location, (JRDesignElement) source);
			else
				errorHandler.addMarker(message, location);
		}
	}

	private JRDesignElement getElementFromTable(JRChild[] childs, StandardTable table) {
		for (JRChild child : childs) {
			if (child instanceof JRDesignComponentElement && ((JRDesignComponentElement) child).getComponent() == table)
				return (JRDesignElement) child;
			if (child instanceof JRElementGroup) {
				JRElementGroup group = (JRElementGroup) child;
				JRDesignElement value = getElementFromTable(group.getElements(), table);
				if (value != null)
					return value;
			}
		}
		return null;
	}

	protected JasperDesign loadJasperDesign(JasperReportsContext jasperReportsContext, final IFile file)
			throws JRException, CoreException {
		InputStream in = file.getContents();
		try {
			return new JRXmlLoader(jasperReportsContext, createDigester()).loadXML(in);
		} finally {
			FileUtils.closeStream(in);
		}
	}

	protected SourceTraceDigester createDigester() throws JRException {
		if (digester == null) {
			if (parserFactory == null) {
				parserFactory = new JRReportSaxParserFactory(jasperReportsContext);
			}
			SAXParser parser = parserFactory.createParser();
			digester = new SourceTraceDigester(parser);
			try {
				JRXmlDigesterFactory.setComponentsInternalEntityResources(jasperReportsContext, digester);
				JRXmlDigesterFactory.configureDigester(jasperReportsContext, digester);
			} catch (SAXException e) {
				throw new JRException(e);
			} catch (ParserConfigurationException e) {
				throw new JRException(e);
			}
		}
		return digester;
	}

	public JasperReportErrorHandler getErrorHandler() {
		return errorHandler;
	}

	public void setErrorHandler(JasperReportErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

	public void setProject(IProject project) {
		if (this.project != project)
			clean();
		this.project = project;
	}

	public void clean() {
		// System.out.println("------------------------------------------");
		map = new HashMap<String, JRCompiler>();
		mtype = new HashMap<String, byte[]>();
		mpack = new HashMap<String, Boolean>();
	}

	/**
	 * 
	 */
	protected class LocalCompilerRequestor extends JRJdtCompiler.CompilerRequestor {
		private Set expressions = new HashSet();

		protected LocalCompilerRequestor(final JasperReportsContext jasperReportsContext, final JRJdtCompiler compiler,
				final JRCompilationUnit[] units) {
			super(jasperReportsContext, compiler, units);
		}

		@Override
		public void processProblems() {
			for (int i = 0; i < units.length; i++) {
				JRCompilationSourceCode sourceCode = units[i].getCompilationSource();
				IProblem[] problems = unitResults[i].getProblems();
				if (problems != null) {
					String lastMessage = null;
					for (int j = 0; j < problems.length; j++) {
						IProblem problem = problems[j];
						int line = problem.getSourceLineNumber();
						JRExpression expression = sourceCode.getExpressionAtLine(line);
						// in some cases, problems are triplicated, and
						// expression line could not be determened correctly
						if (expression == null && lastMessage != null && lastMessage.equals(problem.getMessage()))
							continue;
						if (expression == null)
							errorHandler.addMarker(problem, null);
						else if (!addExpressionError(expression))
							errorHandler.addMarker(problem, digester.getLocation(expression), expression);
						lastMessage = problem.getMessage();
					}
				}
			}
		}

		protected boolean addExpressionError(JRExpression expression) {
			boolean b = expressions.contains(expression);
			if (!b)
				expressions.add(expression);
			return b;
		}
	}
}
