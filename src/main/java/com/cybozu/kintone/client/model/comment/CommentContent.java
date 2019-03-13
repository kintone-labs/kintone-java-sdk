/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.comment;

import java.util.ArrayList;

public class CommentContent {
    private String text;
    private ArrayList<CommentMention> mentions;

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
    public ArrayList<CommentMention> getMentions() {
        return this.mentions;
    }

    /*
     * Set mention
     * @param mention
     */
    public void setMentions(ArrayList<CommentMention> mentions) {
        this.mentions = mentions;
    }
}
