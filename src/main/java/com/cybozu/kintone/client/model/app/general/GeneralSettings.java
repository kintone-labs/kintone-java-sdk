package com.cybozu.kintone.client.model.app.general;

public class GeneralSettings {
    private String name;
    private String description;
    private Icon icon;
    private IconTheme theme;
    private Integer revision;

    public enum IconTheme {
        WHITE, RED, BLUE, GREEN, YELLOW, BLACK
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public IconTheme getTheme() {
        return theme;
    }

    public void setTheme(IconTheme theme) {
        this.theme = theme;
    }

    public Integer getRevision() {
        return this.revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }
}
