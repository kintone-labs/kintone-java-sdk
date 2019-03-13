/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app;

import java.util.Date;

import com.cybozu.kintone.client.model.member.Member;

/**
 * Data class model for Kintone App
 */
public class AppModel {
    private Integer appId;
    private String code;
    private String name;
    private String description;
    private Integer spaceId;
    private Integer threadId;
    private Date createdAt;
    private Member creator;
    private Date modifiedAt;
    private Member modifier;

    /**
     * @return the appId
     */
    public Integer getAppId() {
        return appId;
    }

    /**
     * @param appId the appId to set
     */
    public void setAppId(int appId) {
        this.appId = appId;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the spaceId
     */
    public Integer getSpaceId() {
        return spaceId;
    }

    /**
     * @param spaceId the spaceId to set
     */
    public void setSpaceId(int spaceId) {
        this.spaceId = spaceId;
    }

    /**
     * @return the threadId
     */
    public Integer getThreadId() {
        return threadId;
    }

    /**
     * @param threadId the threadId to set
     */
    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    /**
     * @return the creator
     */
    public Member getCreator() {
        return creator;
    }

    /**
     * @param creator the creator to set
     */
    public void setCreator(Member creator) {
        this.creator = creator;
    }

    /**
     * @return the modifier
     */
    public Member getModifier() {
        return modifier;
    }
    /**
     * @param modifier the modifier to set
     */
    public void setModifier(Member modifier) {
        this.modifier = modifier;
    }

    /**
     * @return the createdAt
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return the modifiedAt
     */
    public Date getModifiedAt() {
        return modifiedAt;
    }

    /**
     * @param modifiedAt the modifiedAt to set
     */
    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((appId == null) ? 0 : appId.hashCode());
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
        AppModel other = (AppModel) obj;
        if (appId == null) {
            if (other.appId != null)
                return false;
        } else if (!appId.equals(other.appId))
            return false;
        return true;
    }
}
