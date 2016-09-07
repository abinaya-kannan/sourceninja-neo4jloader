package com.kannan.neo4jservice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.objectweb.asm.Type;


public class TraceParser {
	
	private static Stack<String[]> stack = new Stack<String[]>();
	private static Stack<String> shortstack = new Stack<String>();
	private String path = "";
	private static AtomicInteger callRank = new AtomicInteger();
	private static long scount = 0;

	public static void main(String[] args) {
		
		//TODO: Remove all the trim()...performance issue
		// TODO Auto-generated method stub
		String s = "1::main::C::org/apache/hadoop/util/RunJar.<clinit>::D:: ()V ::ILN::# ::IC::#";
		//System.out.println(" split " + Arrays.deepToString(s.split("::")));
		
		//String target =  "(IJJILcomAssd;LcomString;ZZ)Ljava/util/Map";
		
		//String desc = "([Ljava/lang/String;Ljava/io/File;Ljava/util/Map;J)V ";// "([Ljava/lang/String;Ljava/io/File;Ljava/util/Map;J)V";
		/*SignatureReader signatureReader = new SignatureReader(desc);
		StringBuilder sb = new StringBuilder();
	    signatureReader.accept(new TraceSignatureVisitor(sb));*/
	    
		//System.out.println(getJavaValues(target ));
		
/*		Pattern pattern = Pattern.compile("\\[*L[^;]+;|\\[[ZBCSIFDJ]|[ZBCSIFDJ]"); //Regex for desc \[*L[^;]+;|\[[ZBCSIFDJ]|[ZBCSIFDJ]
		Matcher matcher = pattern.matcher(desc);

	    int counter = 0;
	    while(matcher.find()) {
	        System.out.println(matcher.group());
	        counter += 1;
	    }
	    
	    System.out.println("-----------------");*/
	    
	   //System.out.println( splitMethodDesc(desc).toString());
		

		try {
			//parse("/Users/arunjanarthnam/Projects/logs/testlog.log");
			//parse("/Users/arunjanarthnam/Tools/ambari-vagrant/ubuntu12.4/profile.log");
			parse("/Users/arunjanarthnam/Projects/orcMLog/profile1.log");
			//parse("/Users/arunjanarthnam/Tools/ambari-vagrant/ubuntu12.4/smallSmaple.log");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	

	 
	public static StringBuilder splitMethodDesc(String desc) {
		StringBuilder sb = new StringBuilder();
		int beginIndex = desc.indexOf('(');
		int endIndex = desc.lastIndexOf(')');
		if((beginIndex == -1 && endIndex != -1) || (beginIndex != -1 && endIndex == -1)) {
			System.err.println(beginIndex);
			System.err.println(endIndex);
			throw new RuntimeException();
		}
		String x0, x1;
		if(beginIndex == -1 && endIndex == -1) {
			x0 = desc;
		}
		else {
			x0 = desc.substring(beginIndex + 1, endIndex);
		}
		if(endIndex != -1){
			x1 = desc.substring(endIndex+1, desc.length());
		}else{
			x1 = "";
		}
		Pattern pattern = Pattern.compile("\\[*L[^;]+;|\\[[ZBCSIFDJ]|[ZBCSIFDJ]"); //Regex for desc \[*L[^;]+;|\[[ZBCSIFDJ]|[ZBCSIFDJ]
	    Matcher matcher = pattern.matcher(x0);

	    sb.append("Method Params:" );
	    while(matcher.find()) {
	    	// System.out.println(matcher.group());
	    	String p = matcher.group();
	    	String fp = null;
	    	if(p.startsWith("[")){ //array
	    		if(p.startsWith("[L")){ //object []
	    			String[] sArr = p.substring(2, p.length()-1).split("\\/");
	    			//System.out.println(Arrays.deepToString(sArr));
	    			fp = sArr[sArr.length-1]+"[]";
	    		}else{ //primtive []
	    			fp = p.substring(1, p.length())+"[]";
	    		}
	    		
	    	}else if(p.startsWith("L")){ //object
	    		String[] sArr = p.substring(2, p.length()-1).split("\\/");
    			fp = sArr[sArr.length-1];
	    	}else{//primitive
	    		fp = p.substring(0, p.length());
	    	}
	    	 sb.append(fp ).append(",");

	    }
	  //  System.out.println(" now return ");
	    sb.deleteCharAt(sb.lastIndexOf(","));
		Pattern pattern1 = Pattern.compile("\\[*L[^;]+;|\\[[ZBCSIFDJV]|[ZBCSIFDJV]"); //Regex for desc \[*L[^;]+;|\[[ZBCSIFDJ]|[ZBCSIFDJ]
	     Matcher mnatcher = pattern1.matcher(x1);


	    while(mnatcher.find()) {
	    	// System.out.println(mnatcher.group());
	    	 sb.append("return:"+ mnatcher.group());

	    }
	    
	    return sb;
	}
	
	
	//TODO: remove trim calls...break method and class name at profiler level
	
	private static void parse(String filename) throws Exception{
		
		try {
			String sCurrentLine = null;
			BufferedReader br = openFile(filename);
			boolean multiCallFlag = false;
			int multiCallCount = 1;
			int multiCallLastRet = 0;
			int multiCallFirstCall = 0;
			boolean returnNotFound = false;
			boolean fileEndMet = false;
			long oc = 0;
				while (!fileEndMet) {
					oc++;
					if(returnNotFound){
						System.out.println(sCurrentLine);
						returnNotFound = false;
					}else{
						sCurrentLine = br.readLine();
						if(sCurrentLine == null){
							fileEndMet = true;
							break;
						}
					}
					

					String callingClass = null;
					String callingMethod = null;
					String callingMethodDesc = "";
					//parse the string
					String[] sArr = null;
					try {
						sArr = sCurrentLine.split("::");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println(" in error " + oc + " " +  sCurrentLine);
						throw e;
					}
					//System.out.println("sCurrentLine : " + sCurrentLine);
					if (sArr.length > 5 && sArr[1].trim().equalsIgnoreCase("main")) {
						String type = sArr[2];
						if (type.equalsIgnoreCase("c")) {//if call, put it in stack
							stack.push(sArr);

						} else if (type.equalsIgnoreCase("r")) {//handling return
							//get the corresponding call from stack
							String[] currentStackArr = null;
							if (!stack.isEmpty()) { currentStackArr = stack.pop();}
							String[] tarray = null;
							//check if we have invoking class
							if (null!=currentStackArr && currentStackArr.length >= 9 && null != currentStackArr[9] && !currentStackArr[9].trim().equalsIgnoreCase("#")) {
								tarray = currentStackArr[9].split("\\.");
								//callingMethodDesc = currentStackArr[5].trim();
								//need to peek to get meth desc
								//TODO - Make TraceParser print in invoking class desc itself or find a better way
								
								if (!stack.isEmpty()) {
									//System.out.println(" happening");
									String[] fatherEntry = stack.peek();
									callingMethodDesc = fatherEntry[5].trim();
									
									//System.out.println( Type.getObjectType(callingMethodDesc).getClassName());
								}

							} else {
								//if not peek from prev entry and get the value
								if (!stack.isEmpty()) {
									//System.out.println(" happening");
									String[] fatherEntry = stack.peek();
									tarray = fatherEntry[3].split("\\.");
									callingMethodDesc = fatherEntry[5].trim();
									//System.out.println( Type.getObjectType(callingMethodDesc).getClassName());
								}else {
									tarray = new String[2];
									tarray[0] = "HiveWriteToORCTable";
									tarray[1] = "orcWriteUseCase";
									//callingMethodDesc = "noDesc";
									
									//if (!stack.isEmpty()) System.out.println(" inside funny error " + Arrays.deepToString(stack.peek()));
								}

							}
							
							
							callingClass = tarray[0];
							List<String> fullMethodDesc = getJavaValues(callingMethodDesc);
							
							if(null == fullMethodDesc || fullMethodDesc.isEmpty()){
								callingMethod =  tarray[1]+"("+callingMethodDesc+")";
							}else{
								callingMethod = fullMethodDesc.get(1) + " " + tarray[1]+"("+fullMethodDesc.get(0)+")";
							}
							//callingMethod = fullMethodDesc.get(1) + "  " + tarray[1]+"("+fullMethodDesc.get(0)+")";
							int iln = 0, rln = 0;
							//iln is invoking line num
							//rln is reteuning line num
							if (!currentStackArr[7].trim().equalsIgnoreCase("#")) {

								iln = Integer.parseInt(currentStackArr[7].trim());
							}
							if (!sArr[5].trim().equalsIgnoreCase("#")) {
								rln = Integer.parseInt(sArr[5].trim());
							}
							//paired call and return
							if (currentStackArr[3].equals(sArr[3])) {

								//	System.out.println(" found match : " + currentStackArr[3] );
								//scenario 1 : no issues found our call-return combo
								//check to see for calls on loop
								//check for c1-r1, c1-r1, .... etc
								//TODO: HANDLE repititive better call by looking for loops
								if (!shortstack.isEmpty()) {
									String[] ssArray = shortstack.peek().split(
											"\\.");
									/*System.out.println(" short stack : " + Arrays.deepToString(ssArray));
									System.out.println(" caling class : " + callingClass);
									System.out.println(" callingMethod : " + callingMethod);
									System.out.println(" currentStackArr[3] : " + currentStackArr[3]);*/

									if (ssArray[0].trim().equalsIgnoreCase(callingMethod)
											&& ssArray[1].trim().equalsIgnoreCase(callingClass)
											&& (ssArray[2] + "." + ssArray[3]).equalsIgnoreCase(currentStackArr[3].trim())
											&& ssArray[8].trim().equalsIgnoreCase(currentStackArr[5].trim())){//this last cond is to check for method overloading
										//last cond now redundant
										multiCallFlag = true;
										multiCallCount++;
										multiCallLastRet = Integer.parseInt(ssArray[7]);
										if(  multiCallFirstCall == 0) multiCallFirstCall = Integer.parseInt(ssArray[6]);
										//scenario 1 A -  multiple call
										//System.out.println(" multiple call ");
									} else {
										//System.out.println(" NOT multiple call ");
										if (multiCallFlag) {
											//update neo4j call count	
											//System.out.println(" inside multi call flag " + Arrays.deepToString(ssArray));
											
											saveToNeo(ssArray, multiCallCount, multiCallFirstCall, multiCallLastRet);
											multiCallLastRet = 0;
											multiCallFirstCall = 0;
											multiCallCount = 1;
											multiCallFlag = false;
										}
										shortstack.pop();
										shortstack.push(callingMethod + "."
												+ callingClass + "."
												+ currentStackArr[3] + "."
												+ iln + "." + rln + "."
												+ currentStackArr[0] + "." + sArr[0]+ "." + currentStackArr[5].trim());

										saveToNeo(callingClass, callingMethod,currentStackArr, sArr, 1, false);
									}

								} else {//where no prev repitive stack is available

									shortstack.push(callingMethod + "."
											+ callingClass + "."
											+ currentStackArr[3] + "." + iln
											+ "." + rln + "."
											+ currentStackArr[0] + "." + sArr[0]+ "." + currentStackArr[5].trim());
									//send to neo4j
									saveToNeo(callingClass, callingMethod,currentStackArr, sArr, 1, false);
								}
							} else {
								System.out.println(" ONE WAY CALL IDENTIFIED " + Arrays.deepToString(currentStackArr));
								System.out.println(" ONE WAY CALL sarr " + Arrays.deepToString(sArr));
								//lets see if we can find a matching call
								//sometimes I get a return without a matching call...disturbing....need more analysis on why this is happening
								String[] tempOne = stack.peek();
								Stack<String[]> ts = new Stack<String[]>();
								boolean mf = false;
								int ic =0;
								while (null!=tempOne || !stack.isEmpty()){
										ic++;
										tempOne = stack.peek();
										ts.push(stack.pop());

									if (tempOne[3].equals(sArr[3])) {
									//	System.out.println( " one way call - currentr line match found " + ic);
										mf = true;
										break;
										}
									tempOne = null;
									}//end while
								
									while (!ts.isEmpty()){
										stack.push(ts.pop());
									}
									if(!mf) {
										//System.out.println( " match r was not found " + sCurrentLine) ;
										//ignore this sCurrentline move onto next
										//current stack arr remains as it is ...nect loop will handle it..so push it back
										stack.push(currentStackArr);
									}else{
										//match r was found 
										// this means, there was a call without r
										//save that call
								
											//submit currentStackArr to neo4j
											if(!currentStackArr[9].equalsIgnoreCase("#")){
												String[] otArray = currentStackArr[9].split("\\.");
												callingClass = otArray[0];
												callingMethod = otArray[1];
											}
											
											saveToNeo(callingClass, callingMethod,
													currentStackArr, currentStackArr, 1, true);
			
											//System.out.println(Arrays.deepToString(currentStackArr));
											//System.out.println("-------------------");
											
											returnNotFound = true;
									}

							}
						} else {
							//error scenario
							System.out.println(" invalid call type");
							break;
						}
					}

					//if return, write it to neo4j

					//if return, but doesn't match with call, mark the call as one-way
					//load teh one-way call
					//manage the original return

					/*
					 * merge (c:Class { name:'Class2' }) merge(m:Method { name:'getClass2' })   merge (c)-[r:calls]->(m) ON CREATE SET r.count = 1
						ON MATCH SET r.count = r.count+1
							RETURN r.count;

					 */
					
					

				}
			
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	private static boolean saveToNeo(String callingClass, 	String callingMethod, String[] currentStackArr, String[] sArr, int count, boolean noReturn){
//		System.out.println(" currentStackArr " + Arrays.deepToString(currentStackArr));
//		System.out.println(" sArr " + Arrays.deepToString(sArr));
		//merge (c:Class { name:'Class2' }) merge(m:Method { name:'getClass2' })   merge (c)-[r:calls {order:3}]->(m)  SET r.count = 20;
		//get the calling method name
		//get the calling class name
		//get the called method name
		//get the called class name
		//get order
		StringBuilder sb = new StringBuilder();
		String[] callingClassMeth = currentStackArr[3].split("\\.");
		String[] calledClassMeth = sArr[3].split("\\.");
		sb.append("merge (sc:Class { name:\'").append(callingClass).append("\' }) ");//calling meth
		//sb.append(System.getProperty("line.separator"));
		sb.append("merge (sm:Method { name:\'").append(callingMethod).append("\' , parent:\'" + callingClass +  "\' }) ");//calling class
		 // sb.append(System.getProperty("line.separator"));
		sb.append("merge (ec:Class{ name:\'").append(calledClassMeth[0]).append("\' }) ");//called class
		//sb.append(System.getProperty("line.separator"));
		List<String> fullMethodDesc = getJavaValues(currentStackArr[5].trim());
		if(null == fullMethodDesc || fullMethodDesc.isEmpty()){
			sb.append("merge (em:Method { name:\'").append( calledClassMeth[1]+"("+currentStackArr[5].trim()+")").append("\', parent:\'" + calledClassMeth[0] +  "\' }) ");//called method
			
		}else{
		//callingMethod = fullMethodDesc.get(1) + "  " + tarray[1]+"("+fullMethodDesc.get(0)+")";
			sb.append("merge (em:Method { name:\'").append(fullMethodDesc.get(1) + " " + calledClassMeth[1]+"("+fullMethodDesc.get(0)+")").append("\', parent:\'" + calledClassMeth[0] +  "\' }) ");//called method
		}
		int iln=0, rln = 0;
		if(!currentStackArr[7].trim().equalsIgnoreCase("#")){
			
			iln = Integer.parseInt(currentStackArr[7].trim());
		}
		
		if(!noReturn && !sArr[5].trim().equalsIgnoreCase("#")){
			rln =Integer.parseInt(sArr[5].trim());
		}else{
			rln = -1;
		}
		//sb.append(System.getProperty("line.separator"));
		if(noReturn){
			sb.append("merge (sm)-[r:").append("calls_worc").append(" {cOrder:"+currentStackArr[0]+", rOrder:"+sArr[0]+", noReturn:"+noReturn+", iln:"+iln+", rln:"+rln).append("}]->(em) ");//actual call
		}else{
			sb.append("merge (sm)-[r:").append("calls_worc").append(" {cOrder:"+currentStackArr[0]+", rOrder:"+sArr[0]+", iln:"+iln+", rln:"+rln).append("}]->(em) ");//actual call
			
		}
			sb.append(" SET r.count = ").append(count).append(" ");
		//sb.append(System.getProperty("line.separator"));
		sb.append("merge (sc)-[r1:").append("contains").append(" ]->(sm) "); //class relationship
		sb.append(" SET r1.count = ").append(count).append(" ");
		//sb.append(System.getProperty("line.separator"));
		sb.append("merge (ec)-[r2:").append("contains").append(" ]->(em) "); //class relationship
		sb.append(" SET r2.count = ").append(count).append(";");
		System.out.println(sb.toString());
		Neo4jService neo = new Neo4jService();
		//neo.runNeoQuery(sb.toString());
		System.out.println( ++scount);
		//System.out.println( "---------------------------");
		return false;
		
	}
	
	private static boolean saveToNeo(String[] arr, 	int count, int multiCallFirstCall, int multiCallLastRet){
		System.out.println(  Arrays.deepToString(arr) );
		System.out.println( multiCallFirstCall + "  " + multiCallLastRet + " " + count );
		StringBuilder sb = new StringBuilder();
		//String[] calledClassMeth = arr[2].split("\\.");

		sb.append("merge (sc:Class { name:\'").append(arr[1]).append("\' }) ");//calling meth
		//sb.append(System.getProperty("line.separator"));
		sb.append("merge (sm:Method { name:\'").append(arr[0]).append("\' , parent:\'" + arr[1] +  "\' }) ");//calling class
		 // sb.append(System.getProperty("line.separator"));
		sb.append("merge (ec:Class{ name:\'").append(arr[2]).append("\' }) ");//called class
		//sb.append(System.getProperty("line.separator"));
		List<String> fullMethodDesc = getJavaValues(arr[8].trim());
		if(null == fullMethodDesc || fullMethodDesc.isEmpty()){
			sb.append("merge (em:Method { name:\'").append(arr[3]+"("+arr[8].trim()+")").append("\', parent:\'" + arr[2] +  "\' }) ");//called method
		}else{
			sb.append("merge (em:Method { name:\'").append( fullMethodDesc.get(1)+ " " + arr[3]+"("+fullMethodDesc.get(0)+")").append("\', parent:\'" + arr[2] +  "\' }) ");//called method
		}
		
		

		//sb.append(System.getProperty("line.separator"));
		sb.append("merge (sm)-[r:").append("calls_worc").append(" {cOrder:"+multiCallFirstCall+", rOrder:"+multiCallLastRet+", iln:"+arr[4]+", rln:"+arr[5]).append("}]->(em) ");//actual call
		sb.append(" SET r.count = ").append(count).append(" ");
		//sb.append(System.getProperty("line.separator"));
		sb.append("merge (sc)-[r1:").append("contains").append(" ]->(sm) "); //class relationship
		sb.append(" SET r1.count = ").append("1").append(" ");
		//sb.append(System.getProperty("line.separator"));
		sb.append("merge (ec)-[r2:").append("contains").append(" ]->(em) "); //class relationship
		sb.append(" SET r2.count = ").append("1").append(";");
		//System.out.println(" neo " + sb.toString());
		Neo4jService neo = new Neo4jService();
		neo.runNeoQuery(sb.toString());
		System.out.println( ++scount);
	//	System.out.println( "---------------");
		return false;
	}
	
	private  static  BufferedReader openFile(String filename){
		
		BufferedReader br = null;
		 
		try {

			br = new BufferedReader(new FileReader(filename));

		} catch (IOException e) {
			e.printStackTrace();
		} /*finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}*/
		return br;
		
	}
	
	

	
	
	
	public static List<String> getJavaValues(String s ){
		List<String>  methodDescList = new ArrayList<String>();
		
		//String s = "([Ljava/lang/String;Ljava/io/File;Ljava/util/Map;J)V ";// "([Ljava/lang/String;Ljava/io/File;Ljava/util/Map;J)V";
		//String s =  "(IJJILcomAssd;LcomString;ZZ)Ljava/util/Map;";
		int beginIndex = s.indexOf('(');
		int endIndex = s.lastIndexOf(')');
		if((beginIndex == -1 && endIndex != -1) || (beginIndex != -1 && endIndex == -1)) {
			System.err.println(beginIndex);
			System.err.println(endIndex);
			throw new RuntimeException();
		}
		String params, returnType;
		if(beginIndex == -1 && endIndex == -1) {
			params= s;
		}
		else {
			params = s.substring(beginIndex + 1, endIndex);
		}
		if(endIndex != -1){
			returnType = s.substring(endIndex+1, s.length());
		}else{
			returnType = "";
		}

		methodDescList.add(resolveJavaType(params));
		methodDescList.add(resolveJavaType(returnType));

		

		return methodDescList;
	}
	
	private static  String resolveJavaType(String s){
		s = s.trim();
		StringBuilder sb = new StringBuilder();
		
		boolean arrayFound = false;
		for(int i = 0; i < s.length(); i++) {
			boolean objEndFound = false;
		    char c = s.charAt(i);
		    if( c == 'L'){
		    	//loop till you find ;
		    	while(!objEndFound){
		    		i++;
		    		c = s.charAt(i);		    		
		    		if ( c == ';'){
		    			objEndFound = true;
		    			
		    			if(arrayFound){
		    				sb.append("[]");
		    				arrayFound = false;
		    			}
		    			sb.append(",");
		    		}else{
		    			sb.append(c);
		    		}
		    	}		    	
		    }else if ( c == '['){
		    	arrayFound = true;
		    	
		    }else{
		    	
		    	switch (c) {
		        case 'V':
		            sb.append("void");
		            break;
		        case 'B':
		            sb.append("byte");
		            break;
		        case 'J':
		            sb.append("long");
		            break;
		        case 'Z':
		            sb.append("boolean");
		            break;
		        case 'I':
		            sb.append("int");
		            break;
		        case 'S':
		            sb.append("short");
		            break;
		        case 'C':
		            sb.append("char");
		            break;
		        case 'F':
		            sb.append("float");
		            break;
		        // case 'D':
		        default:
		            sb.append("double");
		            break;
		    }
		    	if(arrayFound){
		    		sb.append("[]");
		    		arrayFound = false;
		    	}
		    	sb.append(",");
		    }
		}
		//remove the last comma
		if(sb.length()>1)
			sb.deleteCharAt(sb.length()-1);
		
		return sb.toString();
		
	}

}
