/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.comment;

public class CommentMention {
    private String code;
    private String type;

    /*
     * Get app code
     * @return
     */
    public String getCode() {
        return this.code;
    }

    /*
     * Set app code
     * @code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /*
     * Get covertype
     * @return
     */
    public String getType() {
        return this.type;
    }

    /*
     * Set covertype
     * @return
     */
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        CommentMention other = (CommentMention) obj;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }
}
