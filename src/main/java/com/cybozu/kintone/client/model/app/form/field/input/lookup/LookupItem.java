/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field.input.lookup;

import java.util.ArrayList;
import java.util.List;

import com.cybozu.kintone.client.model.app.form.field.FieldMapping;
import com.cybozu.kintone.client.model.app.form.field.related_record.RelatedApp;

public class LookupItem {
    private List<FieldMapping> fieldMappings = new ArrayList<FieldMapping>();
    private String filterCond;
    private List<String> lookupPickerFields = new ArrayList<String>();
    private RelatedApp relatedApp;
    private String relatedKeyField;
    private String sort;

    /**
     * default constructor
     */
    public LookupItem() {

    }

    /**
     * @return the fieldMapping
     */
    public List<FieldMapping> getFieldMapping() {
        return this.fieldMappings;
    }

    /**
     * @param fieldMapping the fieldMapping to set
     */
    public void setFieldMapping(List<FieldMapping> fieldMapping) {
        this.fieldMappings = fieldMapping;
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
     * @return the lookupPickerFields
     */
    public List<String> getLookupPickerFields() {
        return this.lookupPickerFields;
    }

    /**
     * @param lookupPickerFields the lookupPickerFields to set
     */
    public void setLookupPickerFields(List<String> lookupPickerFields) {
        this.lookupPickerFields = lookupPickerFields;
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
     * @return the relatedKeyField
     */
    public String getRelatedKeyField() {
        return this.relatedKeyField;
    }

    /**
     * @param relatedKeyField the relatedKeyField to set
     */
    public void setRelatedKeyField(String relatedKeyField) {
        this.relatedKeyField = relatedKeyField;
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
        result = prime * result + ((fieldMappings == null) ? 0 : fieldMappings.hashCode());
        result = prime * result + ((filterCond == null) ? 0 : filterCond.hashCode());
        result = prime * result + ((lookupPickerFields == null) ? 0 : lookupPickerFields.hashCode());
        result = prime * result + ((relatedApp == null) ? 0 : relatedApp.hashCode());
        result = prime * result + ((relatedKeyField == null) ? 0 : relatedKeyField.hashCode());
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
        if (!(obj instanceof LookupItem)) {
            return false;
        }
        LookupItem other = (LookupItem) obj;
        if (fieldMappings == null) {
            if (other.fieldMappings != null) {
                return false;
            }
        } else if (!fieldMappings.equals(other.fieldMappings)) {
            return false;
        }
        if (filterCond == null) {
            if (other.filterCond != null) {
                return false;
            }
        } else if (!filterCond.equals(other.filterCond)) {
            return false;
        }
        if (lookupPickerFields == null) {
            if (other.lookupPickerFields != null) {
                return false;
            }
        } else if (!lookupPickerFields.equals(other.lookupPickerFields)) {
            return false;
        }
        if (relatedApp == null) {
            if (other.relatedApp != null) {
                return false;
            }
        } else if (!relatedApp.equals(other.relatedApp)) {
            return false;
        }
        if (relatedKeyField == null) {
            if (other.relatedKeyField != null) {
                return false;
            }
        } else if (!relatedKeyField.equals(other.relatedKeyField)) {
            return false;
        }
        return true;
    }
}
