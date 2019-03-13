/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.record.field;

import com.cybozu.kintone.client.model.app.form.FieldType;

public class FieldValue {

	private FieldType type;
	private Object value;

	/**
	 * Get field type
	 * @return type
	 */
	public FieldType getType() {
		return this.type;
	}

	/**
	 * Set field type
	 * @param type the type to set
	 */
	public void setType(FieldType type) {
		this.type = type;
	}

	/**
	 * Get field value
	 * @return value
	 */
	public Object getValue() {
		return this.value;
	}

	/**
	 * Set field value
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        FieldValue other = (FieldValue) obj;
        if (type != other.type)
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }
}
