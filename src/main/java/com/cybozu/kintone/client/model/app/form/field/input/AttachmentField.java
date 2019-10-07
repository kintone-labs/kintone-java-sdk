/**
 * MIT License
 * <p>
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field.input;

import com.cybozu.kintone.client.model.app.form.FieldType;

public class AttachmentField extends AbstractInputField {
    protected String thumbnailSize;

    /**
     * @param code code of the AttachmentField
     */
    public AttachmentField(String code) {
        this.code = code;
        this.type = FieldType.FILE;
    }

    /**
     * @return the thumbnailSize
     */
    public String getThumbnailSize() {
        return this.thumbnailSize;
    }

    /**
     * @param thumbnailSize the thumbnailSize to set
     */
    public void setThumbnailSize(String thumbnailSize) {
        this.thumbnailSize = thumbnailSize;
    }
}
