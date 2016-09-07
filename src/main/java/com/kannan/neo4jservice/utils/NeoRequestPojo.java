package com.kannan.neo4jservice.utils;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class NeoRequestPojo {

@Expose
private List<Statement> statements = new ArrayList<Statement>();

/**
* 
* @return
* The statements
*/
public List<Statement> getStatements() {
return statements;
}

/**
* 
* @param statements
* The statements
*/
public void setStatements(List<Statement> statements) {
this.statements = statements;
}

}