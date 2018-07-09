package com.tiny.web.controller.integration.entity;

public class Author {
    private Double probability;
    private Long writer_id;

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    public Long getWriter_id() {
        return writer_id;
    }

    public void setWriter_id(Long writer_id) {
        this.writer_id = writer_id;
    }

    @Override
    public String toString() {
        return "Author{" +
                "probability=" + probability +
                ", writer_id=" + writer_id +
                '}';
    }
}
