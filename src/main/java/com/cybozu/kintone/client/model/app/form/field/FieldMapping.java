/**
 * MIT License
 * <p>
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field;

public class FieldMapping {
    private String field;
    private String relatedField;

    /**
     * @param field         field of the FieldMapping
     * @param relatedFields relatedFields of the FieldMapping
     */
    public FieldMapping(String field, String relatedFields) {
        this.field = field;
        this.relatedField = relatedFields;
    }

    /**
     * @return the field
     */
    public String getField() {
        return this.field;
    }

    /**
     * @param field the field to set
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * @return the relatedFields
     */
    public String getRelatedFields() {
        return this.relatedField;
    }

    /**
     * @param relatedFields the relatedFields to set
     */
    public void setRelatedFields(String relatedFields) {
        this.relatedField = relatedFields;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((field == null) ? 0 : field.hashCode());
        result = prime * result + ((relatedField == null) ? 0 : relatedField.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     *
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
        if (!(obj instanceof FieldMapping)) {
            return false;
        }
        FieldMapping other = (FieldMapping) obj;
        if (field == null) {
            if (other.field != null) {
                return false;
            }
        } else if (!field.equals(other.field)) {
            return false;
        }
        if (relatedField == null) {
            if (other.relatedField != null) {
                return false;
            }
        } else if (!relatedField.equals(other.relatedField)) {
            return false;
        }
        return true;
    }
}
