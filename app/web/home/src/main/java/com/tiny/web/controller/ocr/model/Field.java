package com.tiny.web.controller.ocr.model;

public class Field extends Region implements Cloneable{

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * tiff source
     */
    private String label;

    private String value;

    /**
     * for input's id
     */
    private String id;

    /**
     * for input's name
     */
    private String name;

    /**
     * show in html, for input's label
     */
    private String htmlLabel;

    /**
     * for input's
     */
    private String comments;

    /**
     * value type
     */
    private String type;

    /**
     * value must fulfill with rule(regex)
     */
    private String rule;

    private String maxLength;

    private String minLength;

    private String required;

    public Field() {
        super();
    }

    public Field(String label, String value) {
        super();
        this.label = label;
        this.value = value;
    }

    public Field(String htmlLabel, String id, String name) {
        super();
        this.htmlLabel = htmlLabel;
        this.id = id;
        this.name = name;
        this.type = "";
        this.rule = "";
        this.maxLength = "";
        this.minLength = "";
        this.comments = "";
        this.required = "";
    }


    public String getLabel() {
        return label;
    }

    public Field setLabel(String label) {
        this.label = label;
        return this;
    }

    public String getValue() {
        return value;
    }

    public Field setValue(String value) {
        this.value = value;
        return this;
    }

    public String getId() {
        return id;
    }

    public Field setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Field setName(String name) {
        this.name = name;
        return this;
    }

    public String getHtmlLabel() {
        return htmlLabel;
    }

    public Field setHtmlLabel(String htmlLabel) {
        this.htmlLabel = htmlLabel;
        return this;
    }

    public String getComments() {
        return comments;
    }

    public Field setComments(String comments) {
        this.comments = comments;
        return this;
    }

    public String getType() {
        return type;
    }

    public Field setType(String type) {
        this.type = type;
        return this;
    }

    public String getRule() {
        return rule;
    }

    public Field setRule(String rule) {
        this.rule = rule;
        return this;
    }

    public String getMaxLength() {
        return maxLength;
    }

    public Field setMaxLength(String maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    public String getMinLength() {
        return minLength;
    }

    public Field setMinLength(String minLength) {
        this.minLength = minLength;
        return this;
    }

    public String getRequired() {
        return required;
    }

    public Field setRequired(String required) {
        // this.required = required;
        return this;
    }

}
