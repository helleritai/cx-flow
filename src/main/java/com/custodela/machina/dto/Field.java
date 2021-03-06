package com.custodela.machina.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Field {
    @JsonProperty("type")
    private String type = "other";
    @JsonProperty("name")
    private String name;
    @JsonProperty("jira_field_name")
    private String jiraFieldName;
    @JsonProperty("jira_field_type")
    private String jiraFieldType;
    @JsonProperty("jira_default_value")
    private String jiraDefaultValue;

    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public String getJiraFieldName() {
        return this.jiraFieldName;
    }

    public String getJiraFieldType() {
        return this.jiraFieldType;
    }

    public String getJiraDefaultValue() {
        return this.jiraDefaultValue;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJiraFieldName(String jiraFieldName) {
        this.jiraFieldName = jiraFieldName;
    }

    public void setJiraFieldType(String jiraFieldType) {
        this.jiraFieldType = jiraFieldType;
    }

    public void setJiraDefaultValue(String jiraDefaultValue) {
        this.jiraDefaultValue = jiraDefaultValue;
    }
}