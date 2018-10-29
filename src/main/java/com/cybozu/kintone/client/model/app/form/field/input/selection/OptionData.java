/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field.input.selection;

public class OptionData {
    protected Integer index;
    protected String label;

    /**
     * default constructor
     */
    public OptionData() {

    }

    /**
     * @param index
     * @param label
     */
    public OptionData(Integer index, String label) {
        this.index = index;
        this.label = label;
    }

    /**
     * @return the index
     */
    public Integer getIndex() {
        return this.index;
    }

    /**
     * @param index
     *            the index to set
     */
    public void setIndex(Integer index) {
        this.index = index;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * @param label
     *            the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + index;
        result = prime * result + ((label == null) ? 0 : label.hashCode());
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
        if (!(obj instanceof OptionData)) {
            return false;
        }
        OptionData other = (OptionData) obj;
        if (index != other.index) {
            return false;
        }
        if (label == null) {
            if (other.label != null) {
                return false;
            }
        } else if (!label.equals(other.label)) {
            return false;
        }
        return true;
    }
}
