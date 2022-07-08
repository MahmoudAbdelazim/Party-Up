package com.partyup.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Question implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String questionString;

    private String reviewQuestionString;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionString() {
        return questionString;
    }

    public void setQuestionString(String questionString) {
        this.questionString = questionString;
    }

    public String getReviewQuestionString() {
        return reviewQuestionString;
    }

    public void setReviewQuestionString(String reviewQuestionString) {
        this.reviewQuestionString = reviewQuestionString;
    }
}
