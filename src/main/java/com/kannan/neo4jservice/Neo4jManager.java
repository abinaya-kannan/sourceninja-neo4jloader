package com.kannan.neo4jservice;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.Transaction;
//
//import com.kannan.neo4jservice.utils.Datum;
//import com.kannan.neo4jservice.utils.Neo4jResult;
//import com.kannan.neo4jservice.utils.NeoRequestPojo;
//import com.kannan.neo4jservice.utils.Node;
//import com.kannan.neo4jservice.utils.Statement;
import com.google.gson.Gson;

public class Neo4jManager {/*
	private static ExecutionEngine engine  = null;
	private static GraphDatabaseService graphDb = null;
	
	
	   private static final String NEO4J_URL = "http://localhost:7474/db/data/transaction";
	   private static final List<String> graphReturnFormat = new ArrayList<String>();
	   

	    

	   public static void main(String[] args){
		   
	   }
	   
	   public String handleTwoQueryScenario(String query){
		   
		   String firstString = this.runQuery(query);
		   StringBuffer uniqueNodeString = new StringBuffer();
		   Map<String, String> uniqueNodeMap = new HashMap<String, String>();
		   if(null!= firstString){
			   //get all unique Id'
			   Gson gson = new Gson();
			  Neo4jResult result =  gson.fromJson(firstString, Neo4jResult.class);
			  //get the nodes
			  List<Datum> dataList = result.getResults().get(0).getData();
			  //loop through datum
			  for(Datum data:dataList){
				  //get the node list
				  List<Node> nodeList = data.getGraph().getNodes();
				  for(Node node:nodeList){
					  String id = node.getId();
					  if(!uniqueNodeMap.containsKey(id)){
						  uniqueNodeMap.put(id, id);
						  uniqueNodeString.append(id + " ,");
					  }
					  
				  }

			  }
			  
			  //remove the last comma
			  uniqueNodeString.delete((uniqueNodeString.length()-1), uniqueNodeString.length()) ;
			  System.out.println(" uniqueNodeString " + uniqueNodeString.toString());
			  String uniqueNodeStr = uniqueNodeString.toString();
			  String secondQuery = "START a = node(" + uniqueNodeStr + "), b = node(" + uniqueNodeStr+ " )MATCH a -[r]-> b RETURN r;";
  			  String output  = this.runQuery(secondQuery);
  			  System.out.println(" output  " + output);
			  return output;
			   
		   }
		   return null;
		   
	   }
	public  String runQuery( String query){
		//runQuery("");
		//graphDb.shutdown();
		
		Gson gson = new Gson();
		NeoRequestPojo req = new NeoRequestPojo();
		Statement  queryStatement = new Statement();
		queryStatement.setIncludeStats(true);
		//queryStatement.setStatement("MATCH (e:Feed)-[r:contains*]-()-[r1:contains*]-(d:DataSet) RETURN e,r,r1, d");
		queryStatement.setStatement(query);
		graphReturnFormat.add("graph");
		queryStatement.setResultDataContents(graphReturnFormat);
		List<Statement>  statementList = new ArrayList<Statement>();
		statementList.add(queryStatement);
		req.setStatements(statementList);
		String reqStr = gson.toJson(req);
		System.out.println(" reqStr " + reqStr);
		HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost(NEO4J_URL);
	    System.out.println("Requesting : " + httppost.getURI());

	    try {
	      @SuppressWarnings("deprecation")
		StringEntity entity = new StringEntity(reqStr);

	      httppost.addHeader("Accept" , "application/json; charset=UTF-8");
	      httppost.addHeader("Content-Type", "application/json");
	      httppost.setEntity(entity);


	      
			//Execute and get the response.
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity respEntity = response.getEntity();
			System.out.println(" after calling mds");
			String output = "";
			StringBuffer sb = new StringBuffer();
			if (respEntity != null) {

				
				BufferedReader in = new BufferedReader(new InputStreamReader(respEntity.getContent()));
				String inputLine;
				while ((inputLine = in.readLine()) != null)
				    sb.append(inputLine );
				in.close();
			}
			
			output = sb.toString();
			System.out.println("output " + output);
			return output;

	    } catch (UnsupportedEncodingException e) {
	      e.printStackTrace();
	    } catch (ClientProtocolException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    } finally {
	      httpclient.getConnectionManager().shutdown();
	    }
	    return null;
		
	}
	
	public static void runQuery(String query){

		ExecutionResult result;
		try ( Transaction ignored = getNeo4jDB().beginTx() )
		{
			System.out.println(" start ");
		    result = getExecutionEngine().execute( "MATCH (e:Feed)-[*1]-(d:DataElement) where e.name=\"ConnectedCar\" RETURN e, d" );
		  //  System.out.println( " dump " + result.dumpToString());
		    String rows= "sjhk";
		    for ( Map<String, Object> row : result )
		    {
		        for ( Entry<String, Object> column : row.entrySet() )
		        {
		            System.out.println(" column.getKey() " + column.getKey());
		        	rows += column.getKey() + ": " + column.getValue() + "; ";
		        }
		        rows += "\n";
		    }
		    
		    System.out.println(rows);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
		
		public static ExecutionEngine getExecutionEngine() throws Exception{
			if(null == engine){

				engine = new ExecutionEngine( getNeo4jDB() );
			}
			return engine;
		}

	public static GraphDatabaseService getNeo4jDB() throws Exception{

		
		 try {
			 if(null == graphDb){
		 
			graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( "/Users/bdcoe/Metadata_Project/neo4j-community-2.1.2/data/graph.db" );
			registerShutdownHook( graphDb );
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("GraphDb is not available");
		}
		return graphDb;
	}
	
	
	private static void registerShutdownHook( final GraphDatabaseService graphDb )
	{
	    // Registers a shutdown hook for the Neo4j instance so that it
	    // shuts down nicely when the VM exits (even if you "Ctrl-C" the
	    // running application).
	    Runtime.getRuntime().addShutdownHook( new Thread()
	    {
	        @Override
	        public void run()
	        {
	            graphDb.shutdown();
	        }
	    } );
	}
	
	
*/}

