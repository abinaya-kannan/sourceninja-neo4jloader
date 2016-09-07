
package com.kannan.neo4jservice.utils;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Transaction {

    @Expose
    private String expires;

    /**
     * 
     * @return
     *     The expires
     */
    public String getExpires() {
        return expires;
    }

    /**
     * 
     * @param expires
     *     The expires
     */
    public void setExpires(String expires) {
        this.expires = expires;
    }

}
