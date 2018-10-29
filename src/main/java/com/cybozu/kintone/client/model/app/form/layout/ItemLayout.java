/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.layout;

import com.cybozu.kintone.client.model.app.form.LayoutType;

public abstract class ItemLayout {
    protected LayoutType type;

    /**
     * @return the type
     */
    public LayoutType getType() {
        return this.type;
    }
}
