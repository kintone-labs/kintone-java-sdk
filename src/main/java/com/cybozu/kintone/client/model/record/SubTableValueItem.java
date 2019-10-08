/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.record;

import java.util.HashMap;


public class SubTableValueItem {

	private Integer id;
	private HashMap<String, FieldValue> value;

	/**
	 * Get id
	 * @return id
	 */
	public Integer getID() {
		return this.id;
	}

	/**
	 * Set id
	 * @param id the id to set
	 */
	public void setID(Integer id) {
		this.id = id;
	}

	/**
	 * Get value
	 * @return value
	 */
	public HashMap<String, FieldValue> getValue() {
		return this.value;
	}

	/**
	 * Set value
	 * @param value the value to set
	 */
	public void setValue(HashMap<String, FieldValue> value) {
		this.value = value;
	}
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SubTableValueItem other = (SubTableValueItem) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }
}
