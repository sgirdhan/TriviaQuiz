/*
 Homwork 4
 Questions
 Sharan Girdhani     - 800960333
 Salman Mujtaba   - 800969897
*/

package com.example.sharangirdhani.homework04;

import java.io.Serializable;
import java.util.ArrayList;

public class Questions implements Serializable {
    String text;
    String image;
    ArrayList<String> choices;
    int answer;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Questions{" +
                "text='" + text + '\'' +
                ", image='" + image + '\'' +
                ", choices=" + choices +
                ", answer='" + answer + '\'' +
                '}';
    }
}