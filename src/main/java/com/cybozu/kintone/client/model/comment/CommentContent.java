/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.comment;

import java.util.List;

public class CommentContent {
    private String text;
    private List<CommentMention> mentions;

    /*
     * Get text
     * @return
     */
    public String getText() {
        return this.text;
    }

    /*
     * Set text
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    /*
     * Get mention
     * @return
     */
    public List<CommentMention> getMentions() {
        return this.mentions;
    }

    /*
     * Set mention
     * @param mention
     */
    public void setMentions(List<CommentMention> mentions) {
        this.mentions = mentions;
    }
}
