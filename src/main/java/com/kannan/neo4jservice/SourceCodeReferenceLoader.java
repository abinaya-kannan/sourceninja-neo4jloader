package com.kannan.neo4jservice;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SourceCodeReferenceLoader {
	
	private static List<String[]> fileList = new ArrayList<String[]>();
			
	private static boolean foundSrc = false;
	private static List<String> extList = new ArrayList<String>();
	static{
		
		extList.add("java");
		extList.add("properties");
	}
	

	public static void main(String[] args) {
		/* MySqlLoader loader = new MySqlLoader();
		
		///Users/arunjanarthnam/Projects/ele2.7.0/hadoop/hadoop-yarn-project/hadoop-yarn/hadoop-yarn-server/hadoop-yarn-server-resourcemanager/src/main/java/org/apache/hadoop/yarn/server/resourcemanager/reservation/PlanView.java

		try {
			List<String> ls = loader.getSrcLocation("org/apache/hadoop/yarn/server/resourcemanager/reservation/PlanView.java");
			System.out.println(ls.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		findFileReferences();
		/*for (String name: fileMap.keySet()){

            String key =name.toString();
            String value = fileMap.get(name).toString();  
            System.out.println(key + " " + value);  


		} */
		
	    //send the map to db
	    MySqlLoader loader = new MySqlLoader();
	    try {
			loader.loadSourceLocation(fileList);
			System.out.println(fileList.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

	}
	

	private static void findFileReferences(){
		walkThroughFiles("/Users/arunjanarthnam/Projects/ele2.7.0/hadoop", false);
	}
	
	private static boolean checkIfValidFormat(String fileName){
		String extension = "";

		int i = fileName.lastIndexOf('.');
		if (i > 0) {
		    if (extList.contains(fileName.substring(i+1))){
		    	return true;
		    }
		}
		return false;
	}
	
	private static void  walkThroughFiles(String srcDirectoryName, boolean foundSrc){
			

		    File directory = new File(srcDirectoryName);
		    //TODO avoid unnecessary files and folders...try reading from src folder and avoid test folders alone
		    // get all the files from a directory
		    File[] fList = directory.listFiles();
		    for (File file : fList) {
		        if (file.isFile() & checkIfValidFormat(file.getName())) {
		        	String[] tass = {file.getName(), file.getAbsolutePath()};
		        	SourceCodeReferenceLoader.fileList.add(tass);
		        } else if (file.isDirectory()) {
		        	
		        	if(!foundSrc && file.getName().equalsIgnoreCase("src")){
		        		foundSrc = true;
		        	}
		        	walkThroughFiles(file.getAbsolutePath(),foundSrc );
		        	if(foundSrc){
		        		foundSrc = false;
		        	}
		        }
		    }
		    

		
	}

}
