These libraries are needed only to generate the HTML documentation of Jaspersoft Studio.
Using the build.xml ANT script in the plugin root folder you can generate the proper html folder after the processing of the docbook one.

Create a proper "ANT build" entry in the runtime configurations adding the following jars in the classpath:
- serializer-2.7.2.jar
- xalan-2.7.2.jar
- xercesImpl-2.12.1.jar

You can kick the execution using clean, build-html as targets for the task.
