
package com.kannan.neo4jservice.utils;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Relationship {

    @Expose
    private String id;
    @Expose
    private String type;
    @Expose
    private String startNode;
    @Expose
    private String endNode;
    @Expose
    private Properties_ properties;

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
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The startNode
     */
    public String getStartNode() {
        return startNode;
    }

    /**
     * 
     * @param startNode
     *     The startNode
     */
    public void setStartNode(String startNode) {
        this.startNode = startNode;
    }

    /**
     * 
     * @return
     *     The endNode
     */
    public String getEndNode() {
        return endNode;
    }

    /**
     * 
     * @param endNode
     *     The endNode
     */
    public void setEndNode(String endNode) {
        this.endNode = endNode;
    }

    /**
     * 
     * @return
     *     The properties
     */
    public Properties_ getProperties() {
        return properties;
    }

    /**
     * 
     * @param properties
     *     The properties
     */
    public void setProperties(Properties_ properties) {
        this.properties = properties;
    }

}
