/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field.input.member;

import com.cybozu.kintone.client.model.member.MemberSelectEntityType;

public class MemberSelectEntity {
    private String code;
    private MemberSelectEntityType type;

    /**
     * default constructor
     */
    public MemberSelectEntity() {

    }

    /**
     * @param code code of the MemberSelectEntity
     * @param type type of the MemberSelectEntity
     */
    public MemberSelectEntity(String code, MemberSelectEntityType type) {
        this.code = code;
        this.type = type;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return this.code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the type
     */
    public MemberSelectEntityType getType() {
        return this.type;
    }

    /**
     * @param type the type to set
     */
    public void setType(MemberSelectEntityType type) {
        this.type = type;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MemberSelectEntity other = (MemberSelectEntity) obj;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        if (type != other.type)
            return false;
        return true;
    }
}
