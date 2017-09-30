package com.example.sharangirdhani.homework04;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QuestionsUtil {
    static public class questionsJSONParser {
        static ArrayList<Questions> parseQuestions(String in) throws JSONException {
            ArrayList<Questions> questionsArrayList = new ArrayList<>();
            ArrayList<String> choices;

            JSONObject root = new JSONObject(in);
            JSONArray qsJSONArray = root.getJSONArray("questions");

            for(int i=0;i<qsJSONArray.length();i++){
                choices = new ArrayList<>();
                JSONObject qsJSONObject = qsJSONArray.getJSONObject(i);
                Questions question = new Questions();
                question.setText(qsJSONObject.getString("text"));
                if(qsJSONObject.has("image")) {
                    question.setImage(qsJSONObject.getString("image"));
                }
                else {
                    question.setImage(null);
                }
                JSONObject choiceJSONObject = qsJSONObject.getJSONObject("choices");
                if(choiceJSONObject != null && choiceJSONObject.getJSONArray("choice") != null) {
                    JSONArray choiceJSONArray = qsJSONObject.getJSONObject("choices").getJSONArray("choice");
                    for(int j = 0;j<choiceJSONArray.length();j++) {
                        choices.add(choiceJSONArray.getString(j));
                    }
                    question.setChoices(choices);
                    question.setAnswer(choiceJSONObject.getString("answer"));
                }
                questionsArrayList.add(question);
            }
            return questionsArrayList;
        }
    }

}