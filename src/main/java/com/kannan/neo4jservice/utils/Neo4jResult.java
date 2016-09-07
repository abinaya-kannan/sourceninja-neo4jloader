
package com.kannan.neo4jservice.utils;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;

@SuppressWarnings("restriction")
@Generated("org.jsonschema2pojo")
public class Neo4jResult {

    @Expose
    private String commit;
    @Expose
    private List<Result> results = new ArrayList<Result>();
    @Expose
    private Transaction transaction;
    @Expose
    private List<Object> errors = new ArrayList<Object>();

    /**
     * 
     * @return
     *     The commit
     */
    public String getCommit() {
        return commit;
    }

    /**
     * 
     * @param commit
     *     The commit
     */
    public void setCommit(String commit) {
        this.commit = commit;
    }

    /**
     * 
     * @return
     *     The results
     */
    public List<Result> getResults() {
        return results;
    }

    /**
     * 
     * @param results
     *     The results
     */
    public void setResults(List<Result> results) {
        this.results = results;
    }

    /**
     * 
     * @return
     *     The transaction
     */
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * 
     * @param transaction
     *     The transaction
     */
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    /**
     * 
     * @return
     *     The errors
     */
    public List<Object> getErrors() {
        return errors;
    }

    /**
     * 
     * @param errors
     *     The errors
     */
    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }

}
