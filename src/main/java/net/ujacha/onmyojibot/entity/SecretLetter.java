package net.ujacha.onmyojibot.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SecretLetter {

    private Long id;

    private String question;
    private String modifiedQuestion;
    private String Answer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getModifiedQuestion() {
        return modifiedQuestion;
    }

    public void setModifiedQuestion(String modifiedQuestion) {
        this.modifiedQuestion = modifiedQuestion;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
