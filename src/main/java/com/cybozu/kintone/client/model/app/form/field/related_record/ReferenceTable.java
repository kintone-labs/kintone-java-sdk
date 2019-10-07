/**
 * MIT License
 * <p>
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field.related_record;

import java.util.ArrayList;

import com.cybozu.kintone.client.model.app.form.field.FieldMapping;

public class ReferenceTable {
    private FieldMapping condition;
    private String filterCond;
    private RelatedApp relatedApp;
    private Integer size;
    private ArrayList<String> displayFields;
    private String sort;

    /**
     * @param condition     condition of the ReferenceTable
     * @param filterCond    filterCond of the ReferenceTable
     * @param relatedApp    relatedApp of the ReferenceTable
     * @param size          size of the ReferenceTable
     * @param displayFields displayFields of the ReferenceTable
     */
    public ReferenceTable(FieldMapping condition, String filterCond, RelatedApp relatedApp, Integer size,
                          ArrayList<String> displayFields) {
        this.condition = condition;
        this.filterCond = filterCond;
        this.relatedApp = relatedApp;
        this.size = size;
        this.displayFields = displayFields;
    }

    /**
     * @return the condition
     */
    public FieldMapping getCondition() {
        return this.condition;
    }

    /**
     * @param condition the condition to set
     */
    public void setCondition(FieldMapping condition) {
        this.condition = condition;
    }

    /**
     * @return the filterCond
     */
    public String getFilterCond() {
        return this.filterCond;
    }

    /**
     * @param filterCond the filterCond to set
     */
    public void setFilterCond(String filterCond) {
        this.filterCond = filterCond;
    }

    /**
     * @return the relatedApp
     */
    public RelatedApp getRelatedApp() {
        return this.relatedApp;
    }

    /**
     * @param relatedApp the relatedApp to set
     */
    public void setRelatedApp(RelatedApp relatedApp) {
        this.relatedApp = relatedApp;
    }

    /**
     * @return the size
     */
    public Integer getSize() {
        return this.size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * @return the displayFields
     */
    public ArrayList<String> getDisplayFields() {
        return this.displayFields;
    }

    /**
     * @param displayFields the displayFields to set
     */
    public void setDisplayFields(ArrayList<String> displayFields) {
        this.displayFields = displayFields;
    }

    /**
     * @return the sort
     */
    public String getSort() {
        return this.sort;
    }

    /**
     * @param sort the sort to set
     */
    public void setSort(String sort) {
        this.sort = sort;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((condition == null) ? 0 : condition.hashCode());
        result = prime * result + ((filterCond == null) ? 0 : filterCond.hashCode());
        result = prime * result + ((relatedApp == null) ? 0 : relatedApp.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ReferenceTable)) {
            return false;
        }
        ReferenceTable other = (ReferenceTable) obj;
        if (condition == null) {
            if (other.condition != null) {
                return false;
            }
        } else if (!condition.equals(other.condition)) {
            return false;
        }
        if (filterCond == null) {
            if (other.filterCond != null) {
                return false;
            }
        } else if (!filterCond.equals(other.filterCond)) {
            return false;
        }
        if (relatedApp == null) {
            if (other.relatedApp != null) {
                return false;
            }
        } else if (!relatedApp.equals(other.relatedApp)) {
            return false;
        }
        return true;
    }
}
