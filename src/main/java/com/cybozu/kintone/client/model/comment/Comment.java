/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.comment;

import java.util.Date;
import java.util.List;

import com.cybozu.kintone.client.model.member.Member;

public class Comment {

    private Integer id;
    private String text;
    private Date createdAt;
    private Member creator;
    private List<CommentMention> mentions;

    /*
     * Get commnet id
     * @return
     */
    public Integer getId() {
        return this.id;
    }

    /*
     * Set comment id
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

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
     * Get createat
     * @return
     */
    public Date getCreatedAt() {
        return this.createdAt;
    }

    /*
     * Set createat
     * @param createAt
     */
    public void setCreatedAt(Date createAt) {
        this.createdAt = createAt;
    }

    /*
     * Get creater
     * @return
     */
    public Member getCreator() {
        return this.creator;
    }

    /*
     * Set creater
     * @param creater
     */
    public void setCreator(Member creator) {
        this.creator = creator;
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
     * @mentions
     */
    public void setMentions(List<CommentMention> mentions) {
        this.mentions = mentions;
    }
}
