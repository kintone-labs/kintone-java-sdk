/**
 * MIT License
 * <p>
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.layout;

public class FieldSize {
    private String width;
    private String height;
    private String innerHeight;

    /**
     * @return the width
     */
    public String getWidth() {
        return this.width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(String width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public String getHeight() {
        return this.height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(String height) {
        this.height = height;
    }

    /**
     * @return the innerHeight
     */
    public String getInnerHeight() {
        return this.innerHeight;
    }

    /**
     * @param innerHeight the innerHeight to set
     */
    public void setInnerHeight(String innerHeight) {
        this.innerHeight = innerHeight;
    }
}
