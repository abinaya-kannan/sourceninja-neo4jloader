
package com.kannan.neo4jservice.utils;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;

@SuppressWarnings("restriction")
@Generated("org.jsonschema2pojo")
public class Graph {

    @Expose
    private List<Node> nodes = new ArrayList<Node>();
    @Expose
    private List<Relationship> relationships = new ArrayList<Relationship>();

    /**
     * 
     * @return
     *     The nodes
     */
    public List<Node> getNodes() {
        return nodes;
    }

    /**
     * 
     * @param nodes
     *     The nodes
     */
    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    /**
     * 
     * @return
     *     The relationships
     */
    public List<Relationship> getRelationships() {
        return relationships;
    }

    /**
     * 
     * @param relationships
     *     The relationships
     */
    public void setRelationships(List<Relationship> relationships) {
        this.relationships = relationships;
    }

}
