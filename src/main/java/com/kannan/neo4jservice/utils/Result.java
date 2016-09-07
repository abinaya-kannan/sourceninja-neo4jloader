
package com.kannan.neo4jservice.utils;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Result {

    @Expose
    private List<String> columns = new ArrayList<String>();
    @Expose
    private List<Datum> data = new ArrayList<Datum>();
    @Expose
    private Stats stats;

    /**
     * 
     * @return
     *     The columns
     */
    public List<String> getColumns() {
        return columns;
    }

    /**
     * 
     * @param columns
     *     The columns
     */
    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    /**
     * 
     * @return
     *     The data
     */
    public List<Datum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<Datum> data) {
        this.data = data;
    }

    /**
     * 
     * @return
     *     The stats
     */
    public Stats getStats() {
        return stats;
    }

    /**
     * 
     * @param stats
     *     The stats
     */
    public void setStats(Stats stats) {
        this.stats = stats;
    }

}
