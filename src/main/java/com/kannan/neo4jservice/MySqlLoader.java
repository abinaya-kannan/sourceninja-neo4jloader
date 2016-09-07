package com.kannan.neo4jservice;

import java.sql.Blob;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MySqlLoader {
	
	private String url = "jdbc:mysql://localhost:3306/javabase";
	private String username = "java";
	private String password = "password";
	private  Connection connection = null;


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MySqlLoader loader = new MySqlLoader();
		try {
			loader.getSrcLocation("org/apache/hadoop/yarn/server/nodemanager/NodeManager.java");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	  private Statement statement = null;
	  private PreparedStatement preparedStatement = null;
	  private ResultSet resultSet = null;
	  
	  private  void getConnection(){
		try {
			// This will load the MySQL driver, each DB has its own driver
			  Class.forName("com.mysql.jdbc.Driver");
			  // Setup the connection with the DB
			  //root@127.0.0.1:3306
			  //jdbc:mysql://127.0.0.1:3306/?user=root
			   this.connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/?user=root");
			  System.out.println(" connection successfull");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	      
	  }

	  public  List<String> getSrcLocation(String sourceName) throws Exception {
	    try {
	    	if(this.connection == null){
	    		this.getConnection();
	    	}
	     //just get the file name
	    	int i = sourceName.lastIndexOf('/');
	    	String base = sourceName.substring(0, i+1);
	    	String fileName  = sourceName.substring(i+1);
	    	System.out.println(" sourceName " + sourceName);
	    	System.out.println(" fileName " + fileName);
	    	System.out.println(" base " + base);
	      preparedStatement = connection.prepareStatement("SELECT srcLocation FROM sourcewalker.Code_loc_table where sourceName = ?");
	      preparedStatement.setString(1, fileName);
	      ResultSet rs = preparedStatement.executeQuery();
	      List<String> srcLocList = new ArrayList<String>();
	      int rsc = 0;
	      while(rs.next()){
	    	  String s = rs.getString(1);
	    	  System.out.println(" rsc " + s);
	    	  Blob blob = rs.getBlob(1);
	    	  byte[] bdata = blob.getBytes(1, (int) blob.length());
	    	   s = new String(bdata);
	    	   System.out.println(" rsc " + s);
	    	  
	    	  if(null!=s && s.contains(base) ){
	    		  System.out.println(" foubd match");
	    		  srcLocList.add(s);
	    		  rsc++;
	    	  }
	    	  
	      }
	      System.out.println(" rsc " + srcLocList.get(0));
	      
	      return srcLocList;

	    } catch (Exception e) {
	      throw e;
	    } finally {
	      close();
	    }

	  }

	  public void loadSourceLocation(List<String[]> fileList) throws SQLException {
		  String sql = " INSERT INTO `sourcewalker`.`Code_loc_table` (`sourceName`,`groupId`,`srcLocation`,`srcLocationType`,`srcLocationId`) VALUES (?, ?, ? ,?,?)";

		  if(this.connection == null){
	    		this.getConnection();
	    	}
		  PreparedStatement ps = connection.prepareStatement(sql);
		   
		  final int batchSize = 1000;
		  int count = 0;
		  Iterator it = fileList.iterator();
		    while (it.hasNext()) {
		        String[] pair = (String[])it.next();
		       // System.out.println(pair.getKey() + " = " + pair.getValue());

				   
			      ps.setString(1, (String)pair[0]);
			      ps.setInt(2, 1);
			      Blob blob = connection.createBlob();
			      blob.setBytes(1, ((String) pair[1]).getBytes());
			      ps.setBlob(3, blob);
			      ps.setString(4, "localDisk");
			      ps.setString(5, "1");
			      ps.addBatch();
			       
			      if(++count % batchSize == 0) {
			          ps.executeBatch();
			      }
			      
				  
			  
		    }
		    
		    ps.executeBatch(); // insert remaining records
			  ps.close();
			  connection.close();


	  }



	  // You need to close the resultSet
	  private void close() {
	    try {
	      if (resultSet != null) {
	        resultSet.close();
	      }

	      if (statement != null) {
	        statement.close();
	      }

	      if (connection != null) {
	        connection.close();
	      }
	    } catch (Exception e) {

	    }
	  }
	

}
