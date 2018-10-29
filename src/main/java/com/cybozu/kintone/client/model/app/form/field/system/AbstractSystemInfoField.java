/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field.system;

public abstract class AbstractSystemInfoField extends AbstractSystemField {
    protected Boolean noLabel;

    /**
     * @return
     */
    public Boolean getNoLabel() {
        return this.noLabel;
    }

    /**
     * @param noLabel
     */
    public void setNoLabel(Boolean noLabel) {
        this.noLabel = noLabel;
    }
}
