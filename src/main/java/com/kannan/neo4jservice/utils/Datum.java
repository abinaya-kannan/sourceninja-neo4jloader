
package com.kannan.neo4jservice.utils;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;

@SuppressWarnings({ "restriction", "restriction" })
@Generated("org.jsonschema2pojo")
public class Datum {

    @Expose
    private Graph graph;

    /**
     * 
     * @return
     *     The graph
     */
    public Graph getGraph() {
        return graph;
    }

    /**
     * 
     * @param graph
     *     The graph
     */
    public void setGraph(Graph graph) {
        this.graph = graph;
    }

}
