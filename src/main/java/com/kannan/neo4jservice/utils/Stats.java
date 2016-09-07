
package com.kannan.neo4jservice.utils;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Stats {

    @SerializedName("contains_updates")
    @Expose
    private Boolean containsUpdates;
    @SerializedName("nodes_created")
    @Expose
    private Integer nodesCreated;
    @SerializedName("nodes_deleted")
    @Expose
    private Integer nodesDeleted;
    @SerializedName("properties_set")
    @Expose
    private Integer propertiesSet;
    @SerializedName("relationships_created")
    @Expose
    private Integer relationshipsCreated;
    @SerializedName("relationship_deleted")
    @Expose
    private Integer relationshipDeleted;
    @SerializedName("labels_added")
    @Expose
    private Integer labelsAdded;
    @SerializedName("labels_removed")
    @Expose
    private Integer labelsRemoved;
    @SerializedName("indexes_added")
    @Expose
    private Integer indexesAdded;
    @SerializedName("indexes_removed")
    @Expose
    private Integer indexesRemoved;
    @SerializedName("constraints_added")
    @Expose
    private Integer constraintsAdded;
    @SerializedName("constraints_removed")
    @Expose
    private Integer constraintsRemoved;

    /**
     * 
     * @return
     *     The containsUpdates
     */
    public Boolean getContainsUpdates() {
        return containsUpdates;
    }

    /**
     * 
     * @param containsUpdates
     *     The contains_updates
     */
    public void setContainsUpdates(Boolean containsUpdates) {
        this.containsUpdates = containsUpdates;
    }

    /**
     * 
     * @return
     *     The nodesCreated
     */
    public Integer getNodesCreated() {
        return nodesCreated;
    }

    /**
     * 
     * @param nodesCreated
     *     The nodes_created
     */
    public void setNodesCreated(Integer nodesCreated) {
        this.nodesCreated = nodesCreated;
    }

    /**
     * 
     * @return
     *     The nodesDeleted
     */
    public Integer getNodesDeleted() {
        return nodesDeleted;
    }

    /**
     * 
     * @param nodesDeleted
     *     The nodes_deleted
     */
    public void setNodesDeleted(Integer nodesDeleted) {
        this.nodesDeleted = nodesDeleted;
    }

    /**
     * 
     * @return
     *     The propertiesSet
     */
    public Integer getPropertiesSet() {
        return propertiesSet;
    }

    /**
     * 
     * @param propertiesSet
     *     The properties_set
     */
    public void setPropertiesSet(Integer propertiesSet) {
        this.propertiesSet = propertiesSet;
    }

    /**
     * 
     * @return
     *     The relationshipsCreated
     */
    public Integer getRelationshipsCreated() {
        return relationshipsCreated;
    }

    /**
     * 
     * @param relationshipsCreated
     *     The relationships_created
     */
    public void setRelationshipsCreated(Integer relationshipsCreated) {
        this.relationshipsCreated = relationshipsCreated;
    }

    /**
     * 
     * @return
     *     The relationshipDeleted
     */
    public Integer getRelationshipDeleted() {
        return relationshipDeleted;
    }

    /**
     * 
     * @param relationshipDeleted
     *     The relationship_deleted
     */
    public void setRelationshipDeleted(Integer relationshipDeleted) {
        this.relationshipDeleted = relationshipDeleted;
    }

    /**
     * 
     * @return
     *     The labelsAdded
     */
    public Integer getLabelsAdded() {
        return labelsAdded;
    }

    /**
     * 
     * @param labelsAdded
     *     The labels_added
     */
    public void setLabelsAdded(Integer labelsAdded) {
        this.labelsAdded = labelsAdded;
    }

    /**
     * 
     * @return
     *     The labelsRemoved
     */
    public Integer getLabelsRemoved() {
        return labelsRemoved;
    }

    /**
     * 
     * @param labelsRemoved
     *     The labels_removed
     */
    public void setLabelsRemoved(Integer labelsRemoved) {
        this.labelsRemoved = labelsRemoved;
    }

    /**
     * 
     * @return
     *     The indexesAdded
     */
    public Integer getIndexesAdded() {
        return indexesAdded;
    }

    /**
     * 
     * @param indexesAdded
     *     The indexes_added
     */
    public void setIndexesAdded(Integer indexesAdded) {
        this.indexesAdded = indexesAdded;
    }

    /**
     * 
     * @return
     *     The indexesRemoved
     */
    public Integer getIndexesRemoved() {
        return indexesRemoved;
    }

    /**
     * 
     * @param indexesRemoved
     *     The indexes_removed
     */
    public void setIndexesRemoved(Integer indexesRemoved) {
        this.indexesRemoved = indexesRemoved;
    }

    /**
     * 
     * @return
     *     The constraintsAdded
     */
    public Integer getConstraintsAdded() {
        return constraintsAdded;
    }

    /**
     * 
     * @param constraintsAdded
     *     The constraints_added
     */
    public void setConstraintsAdded(Integer constraintsAdded) {
        this.constraintsAdded = constraintsAdded;
    }

    /**
     * 
     * @return
     *     The constraintsRemoved
     */
    public Integer getConstraintsRemoved() {
        return constraintsRemoved;
    }

    /**
     * 
     * @param constraintsRemoved
     *     The constraints_removed
     */
    public void setConstraintsRemoved(Integer constraintsRemoved) {
        this.constraintsRemoved = constraintsRemoved;
    }

}
