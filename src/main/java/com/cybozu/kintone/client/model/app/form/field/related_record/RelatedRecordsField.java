/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field.related_record;

import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.model.app.form.field.Field;

public class RelatedRecordsField extends Field {
    protected String label;
    protected Boolean noLabel;
    protected ReferenceTable referenceTable;

    /**
     * @param code code of the RelatedRecordsField
     */
    public RelatedRecordsField(String code) {
        this.code = code;
        this.type = FieldType.REFERENCE_TABLE;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return this.label;
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
     * @return the referenceTable
     */
    public ReferenceTable getReferenceTable() {
        return this.referenceTable;
    }

    /**
     * @param referenceTable the referenceTable to set
     */
    public void setReferenceTable(ReferenceTable referenceTable) {
        this.referenceTable = referenceTable;
    }
}
