package com.cybozu.kintone.client.model.app.view;

import java.util.ArrayList;

public class ViewModel {
    private BuiltinType builtinType;
    private String date;
    private ArrayList<String> fields;
    private String filterCond;
    private String html;
    private Integer id;
    private Integer index;
    private String name;
    private Boolean pager;
    private String sort;
    private String title;
    private ViewType type;

    public enum ViewType {
        LIST, CALENDAR, CUSTOM
    }

    public enum BuiltinType {
        ASSIGNEE
    }

    public BuiltinType getBuiltinType() {
        return builtinType;
    }

    public void setBuiltinType(BuiltinType builtinType) {
        this.builtinType = builtinType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<String> getFields() {
        return fields;
    }

    public void setFields(ArrayList<String> fields) {
        this.fields = fields;
    }

    public String getFilterCond() {
        return filterCond;
    }

    public void setFilterCond(String filterCond) {
        this.filterCond = filterCond;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getPager() {
        return pager;
    }

    public void setPager(Boolean pager) {
        this.pager = pager;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ViewType getType() {
        return type;
    }

    public void setType(ViewType type) {
        this.type = type;
    }
}
