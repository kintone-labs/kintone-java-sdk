/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.comment;

import java.util.ArrayList;

public class GetCommentsResponse {

    private ArrayList<Comment> comments;
    private Boolean older;
    private Boolean newer;

    /**
     * Get comment
     * @return
     */

    public ArrayList<Comment> getComments() {
        return this.comments;
    }

    /**
     * Set comment
     * @param comments
     */
    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    /**
     * Get Older comment
     * @return
     */
    public Boolean getOlder() {
        return this.older;
    }

    /**
     * Set Older comment
     * @param older
     */
    public void setOlder(Boolean older) {
        this.older = older;
    }

    /**
     * Get Newer comment
     * @return
     */
    public Boolean getNewer() {
        return this.newer;
    }

    /**
     * Set Newer comment
     * @param newer
     */
    public void setNewer(Boolean newer) {
        this.newer = newer;
    }
}
