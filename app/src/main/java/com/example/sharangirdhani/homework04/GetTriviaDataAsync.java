package com.example.sharangirdhani.homework04;


import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GetTriviaDataAsync extends AsyncTask<Void, Void, ArrayList<Questions>>{

    BufferedReader reader = null;
    final static String baseURL="http://dev.theappsdr.com/apis/trivia_json/index.php";
    GetTriviaData activity;

    public GetTriviaDataAsync(GetTriviaData activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<Questions> doInBackground(Void... params) {
        try {
            URL url = new URL(baseURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return QuestionsUtil.questionsJSONParser.parseQuestions(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Questions> questions) {
        super.onPostExecute(questions);
        activity.setTriviaData(questions);
    }

    interface GetTriviaData {
        void setTriviaData(ArrayList<Questions> questions);
    }
}