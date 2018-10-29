/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field.input;

import com.cybozu.kintone.client.model.app.form.FieldType;

public class AttachmentField extends AbstractInputField {
    protected Integer thumbnailSize;

    /**
     * @param code
     */
    public AttachmentField(String code) {
        this.code = code;
        this.type = FieldType.FILE;
    }

    /**
     * @return the thumbnailSize
     */
    public Integer getThumbnailSize() {
        return this.thumbnailSize;
    }

    /**
     * @param thumbnailSize
     *            the thumbnailSize to set
     */
    public void setThumbnailSize(Integer thumbnailSize) {
        this.thumbnailSize = thumbnailSize;
    }
}
