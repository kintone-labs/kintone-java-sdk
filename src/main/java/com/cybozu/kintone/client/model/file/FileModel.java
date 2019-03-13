/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.file;

public class FileModel {

	private String contentType;
	private String fileKey;
	private String name;
	private String size;

	/**
	 * Get the type of the uploaded file.
	 * @return contentType
	 */
	public String getContentType() {
		return this.contentType;
	}

	/**
	 * Set the type of the uploaded file.
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * Get the file key of the uploaded file.
	 * @return fileKey
	 */
	public String getFileKey() {
		return this.fileKey;
	}

	/**
	 * Set the file key of the uploaded file.
	 * @param fileKey the fileKey to set
	 */
	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}

	/**
	 * Get the name of the uploaded file.
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Set the name of the uploaded file.
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the size of the uploaded file.
	 * @return size
	 */
	public String getSize() {
		return this.size;
	}

	/**
	 * Set the size of the uploaded file.
	 * @param size the size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((contentType == null) ? 0 : contentType.hashCode());
        result = prime * result + ((fileKey == null) ? 0 : fileKey.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((size == null) ? 0 : size.hashCode());
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
        FileModel other = (FileModel) obj;
        if (contentType == null) {
            if (other.contentType != null)
                return false;
        } else if (!contentType.equals(other.contentType))
            return false;
        if (fileKey == null) {
            if (other.fileKey != null)
                return false;
        } else if (!fileKey.equals(other.fileKey))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (size == null) {
            if (other.size != null)
                return false;
        } else if (!size.equals(other.size))
            return false;
        return true;
    }

}
