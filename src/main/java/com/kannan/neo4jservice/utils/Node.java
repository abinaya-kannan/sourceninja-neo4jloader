
package com.kannan.neo4jservice.utils;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Node {

    @Expose
    private String id;
    @Expose
    private List<String> labels = new ArrayList<String>();
    @Expose
    private Properties properties;

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The labels
     */
    public List<String> getLabels() {
        return labels;
    }

    /**
     * 
     * @param labels
     *     The labels
     */
    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    /**
     * 
     * @return
     *     The properties
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * 
     * @param properties
     *     The properties
     */
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

}
