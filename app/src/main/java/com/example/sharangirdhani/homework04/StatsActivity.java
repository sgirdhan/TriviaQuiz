/*
 Homwork 4
 StatsActiviy
 Sharan Girdhani     - 800960333
 Salman Mujtaba   - 800969897
*/

package com.example.sharangirdhani.homework04;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.sharangirdhani.homework04.databinding.ActivityStatsBinding;

public class StatsActivity extends AppCompatActivity {

    private ActivityStatsBinding statsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_stats);
        statsBinding = DataBindingUtil.setContentView(this, R.layout.activity_stats);
        int correctAnswers = 0;
        int questionsCount = 0;

        if (getIntent().getIntExtra(TriviaActivity.CORRECT_ANSWERS_KEY, -1) != -1) {
            correctAnswers = getIntent().getIntExtra(TriviaActivity.CORRECT_ANSWERS_KEY,-1);
            questionsCount = getIntent().getIntExtra(TriviaActivity.QUESTIONS_COUNT_KEY,-1);

            int percentage = (100 * correctAnswers) / questionsCount;

            if(percentage == 100) {
                statsBinding.textViewGenericText.setText(getResources().getString(R.string.textAllCorrect));
            }
            else {
                statsBinding.textViewGenericText.setText(getResources().getString(R.string.textTryAgain));
            }
            statsBinding.progressBarResult.setIndeterminate(false);
            statsBinding.progressBarResult.setProgress(percentage);
            statsBinding.textViewResult.setText(Integer.toString(percentage) + "%");
        }


    }

    public void quitStats(View view){
        Intent restartIntent = new Intent(this,MainActivity.class);
        restartIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        restartIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(restartIntent);
    }

    public void tryAgain(View view){
        finish();
    }
}
