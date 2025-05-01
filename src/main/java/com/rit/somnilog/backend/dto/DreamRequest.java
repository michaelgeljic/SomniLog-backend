package com.rit.somnilog.backend.dto;

import java.util.List;

public class DreamRequest {
    private String title;
    private String content;
    private String dreamDate;
    private String mood;
    private String typeTag;
    private Boolean isRecurring;
    private List<String> tags;

    // Getters and setters
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getDreamDate() {
        return dreamDate;
    }
    public void setDreamDate(String dreamDate) {
        this.dreamDate = dreamDate;
    }
    public String getMood() {
        return mood;
    }
    public void setMood(String mood) {
        this.mood = mood;
    }
    public String getTypeTag() {
        return typeTag;
    }
    public void setTypeTag(String typeTag) {
        this.typeTag = typeTag;
    }
    public Boolean getIsRecurring() {
        return isRecurring;
    }
    public void setIsRecurring(Boolean isRecurring) {
        this.isRecurring = isRecurring;
    }
    public List<String> getTags() {
        return tags;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}