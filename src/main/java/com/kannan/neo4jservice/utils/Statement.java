package com.kannan.neo4jservice.utils;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Statement {

@Expose
private String statement;
@Expose
private List<String> resultDataContents = new ArrayList<String>();
@Expose
private Boolean includeStats;

/**
* 
* @return
* The statement
*/
public String getStatement() {
return statement;
}

/**
* 
* @param statement
* The statement
*/
public void setStatement(String statement) {
this.statement = statement;
}

/**
* 
* @return
* The resultDataContents
*/
public List<String> getResultDataContents() {
return resultDataContents;
}

/**
* 
* @param resultDataContents
* The resultDataContents
*/
public void setResultDataContents(List<String> resultDataContents) {
this.resultDataContents = resultDataContents;
}

/**
* 
* @return
* The includeStats
*/
public Boolean getIncludeStats() {
return includeStats;
}

/**
* 
* @param includeStats
* The includeStats
*/
public void setIncludeStats(Boolean includeStats) {
this.includeStats = includeStats;
}

}
