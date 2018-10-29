/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.layout;

import java.util.ArrayList;
import java.util.List;

import com.cybozu.kintone.client.model.app.form.LayoutType;

public class GroupLayout extends ItemLayout {
    private String code;
    private List<RowLayout> layout = new ArrayList<RowLayout>();

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
    public List<RowLayout> getLayout() {
        return this.layout;
    }

    /**
     * @param layout the layout to set
     */
    public void setLayout(List<RowLayout> layout) {
        this.layout = layout;
    }
}
