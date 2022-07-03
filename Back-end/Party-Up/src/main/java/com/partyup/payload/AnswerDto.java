package com.partyup.payload;

public class AnswerDto {

    private Long id;

    private int answer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAnswer() {
        if (answer < 1) answer = 1;
        if (answer > 5) answer = 5;
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
