/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field;

import com.cybozu.kintone.client.model.app.form.FieldType;

public class FieldGroup extends Field {
    protected String label;
    protected Boolean noLabel;
    protected Boolean openGroup;

    /**
     * @param code code of the FieldGroup
     */
    public FieldGroup(String code) {
        this.code = code;
        this.type = FieldType.GROUP;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the noLabel
     */
    public Boolean getNoLabel() {
        return this.noLabel;
    }

    /**
     * @param noLabel the noLabel to set
     */
    public void setNoLabel(Boolean noLabel) {
        this.noLabel = noLabel;
    }

    /**
     * @return the openGroup
     */
    public Boolean getOpenGroup() {
        return this.openGroup;
    }

    /**
     * @param openGroup the openGroup to set
     */
    public void setOpenGroup(Boolean openGroup) {
        this.openGroup = openGroup;
    }
}
