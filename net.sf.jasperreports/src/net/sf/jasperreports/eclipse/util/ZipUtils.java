/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * offers the method to zip a file or a folder, and eventually unzip also
 */
public class ZipUtils {

    /**
     * Unzip a zip file
     * 
     * @param zipFile input zip file
     * @param outputFolder the output folder
     */
    public void unZipFiles(String zipFile, String outputFolder){

     byte[] buffer = new byte[1024];
    	
     try{
    		
    	//create output directory is not exists
    	File folder = new File(outputFolder);
    	if(!folder.exists()){
    		folder.mkdir();
    	}
    		
    	//get the zip file content
    	ZipInputStream zis =  new ZipInputStream(new FileInputStream(zipFile));
    	//get the zipped file list entry
    	ZipEntry ze = zis.getNextEntry();
    		
    	while(ze!=null){
    			
    	   String fileName = ze.getName();
           File newFile = new File(outputFolder + File.separator + fileName);
                
            //create all non exists folders
            //else you will hit FileNotFoundException for compressed folder
            new File(newFile.getParent()).mkdirs();
              
            FileOutputStream fos = new FileOutputStream(newFile);             

            int len;
            while ((len = zis.read(buffer)) > 0) {
       		fos.write(buffer, 0, len);
            }
        		
            fos.close();   
            ze = zis.getNextEntry();
    	}
        zis.closeEntry();
    	zis.close();
    }catch(IOException ex){
       ex.printStackTrace(); 
    }
   } 
	
	/**
	 * Zip function zip a folder and all its content
	 * 
	 * @param srcFolder source folder, the folder itself will be included inside the file
	 * 
	 * @param destZipFile destination file, must contain the filename.zip
	 * @return true if the operation was sucessfull, otherwise false
	 */
    public boolean zipFiles(String srcFolder, String destZipFile) {
    	List<String> folderList = new ArrayList<String>();
    	folderList.add(srcFolder);
    	return zipFiles(folderList, destZipFile);
    }
    
	public boolean zipFiles(List<String> srcFolders, String destZipFile) {
		boolean result = false;
		try {
			zipFolder(srcFolders, destZipFile);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Zip function zip a content of a folder and all the subchildren also
	 * 
	 * @param srcFolder source folder, the folder itself will not be included inside the file
	 * 
	 * @param destZipFile destination file, must contain the filename.zip
	 * @return true if the operation was sucessfull, otherwise false
	 */
	public boolean zipFolderContent(String srcFolder, String destZipFile) {
		boolean result = false;
		ZipOutputStream zip = null;
		FileOutputStream fileWriter = null;
		try {			
			//create the output stream to zip file result
			fileWriter = new FileOutputStream(destZipFile);
			zip = new ZipOutputStream(fileWriter);
			
			File folder = new File(srcFolder);
			//list the files in the folder
			for (String fileName : folder.list()) {
				addFileToZip("", srcFolder + "/" + fileName, zip, false);
			}
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			FileUtils.closeStream(zip);
			FileUtils.closeStream(fileWriter);
		}
		return result;
	}


	/**
	 * Zip function zip all files and folders
	 * 
	 * @param srcFolder source folder
	 * @param destZipFile destination file, must contain the filename.zip
	 */
	private void zipFolder(List<String> srcFolders, String destZipFile)
			throws Exception {
		ZipOutputStream zip = null;
		FileOutputStream fileWriter = null;
		
		//create the output stream to zip file result
		fileWriter = new FileOutputStream(destZipFile);
		zip = new ZipOutputStream(fileWriter);
		
		for(String srcFolder : srcFolders){
			//add the folder to the zip
			addFolderToZip("", srcFolder, zip);
		}
		
		// close the zip objects
		zip.flush();
		zip.close();
	}

	/**
	 * recursively add files to the zip files
	 * 
	 * @param flag true if the file added is an empty folder, false otherwise
	 */
	private void addFileToZip(String path, String srcFile, ZipOutputStream zip, boolean flag) throws Exception {
		//create the file object for inputs
		File folder = new File(srcFile);

		//if the folder is empty add empty folder to the Zip file
		if (flag == true) {
			zip.putNextEntry(new ZipEntry(path + "/" + folder.getName() + "/"));
		} else { 
			//if the current name is directory, recursively traverse it to
			//get the files
			if (folder.isDirectory()) {
				//if folder is not empty
				addFolderToZip(path, srcFile, zip);
			} else {
				//write the file to the output
				byte[] buf = new byte[1024];
				int len;
				FileInputStream in = new FileInputStream(srcFile);
				zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
				while ((len = in.read(buf)) > 0) {
					//Write the Result
					zip.write(buf, 0, len);
				}
				in.close();
			}
		}
	}

	/**
	 * add folder to the zip file
	 */
	private void addFolderToZip(String path, String srcFolder,ZipOutputStream zip) throws Exception {
		File folder = new File(srcFolder);

		//check the empty folder
		if (folder.list().length == 0) {
			addFileToZip(path, srcFolder, zip, true);
		} else {
			//list the files in the folder
			for (String fileName : folder.list()) {
				if (path.equals("")) {
					addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip, false);
				} else {
					addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip, false);
				}
			}
		}
	}
}
