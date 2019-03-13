/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.layout;

import java.util.ArrayList;

import com.cybozu.kintone.client.model.app.form.LayoutType;

public class GroupLayout extends ItemLayout {
    private String code;
    private ArrayList<RowLayout> layout;

    /**
     * default constructor
     */
    public GroupLayout() {
        this.type = LayoutType.GROUP;
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
     * @return the layout
     */
    public ArrayList<RowLayout> getLayout() {
        return this.layout;
    }

    /**
     * @param layout the layout to set
     */
    public void setLayout(ArrayList<RowLayout> layout) {
        this.layout = layout;
    }
}
