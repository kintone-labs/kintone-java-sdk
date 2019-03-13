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
     * @return comments
     */

    public ArrayList<Comment> getComments() {
        return this.comments;
    }

    /**
     * Set comment
     * @param comments the comments to set
     */
    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    /**
     * Get Older comment
     * @return older
     */
    public Boolean getOlder() {
        return this.older;
    }

    /**
     * Set Older comment
     * @param older the older to set
     */
    public void setOlder(Boolean older) {
        this.older = older;
    }

    /**
     * Get Newer comment
     * @return newer
     */
    public Boolean getNewer() {
        return this.newer;
    }

    /**
     * Set Newer comment
     * @param newer the newer to set
     */
    public void setNewer(Boolean newer) {
        this.newer = newer;
    }
}
