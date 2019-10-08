package com.cybozu.kintone.client.model.app.general_settings;

import com.cybozu.kintone.client.model.file.FileModel;

public class Icon {
    private FileModel file;
    private String key;
    private IconType type;

    public enum IconType {
        FILE, PRESET
    }

    public Icon(FileModel file, String key, IconType type) {
        this.file = file;
        this.key = key;
        this.type = type;
    }

    public FileModel getFile() {
        return file;
    }

    public void setFile(FileModel file) {
        this.file = file;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public IconType getType() {
        return type;
    }

    public void setType(IconType type) {
        this.type = type;
    };

}
