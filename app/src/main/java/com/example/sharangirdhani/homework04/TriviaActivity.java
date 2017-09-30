package com.example.sharangirdhani.homework04;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.sharangirdhani.homework04.databinding.ActivityTriviaBinding;

import java.util.ArrayList;

public class TriviaActivity extends AppCompatActivity implements GetRelevantImageAsync.DisplayImage{
    final static int TOTAL_TIME = 120;
    private ActivityTriviaBinding triviaBinding;

    public static final String CORRECT_ANSWERS_KEY = "CORRECT";
    public static final String QUESTIONS_COUNT_KEY = "COUNT_OF_QUESTIONS";

    GetRelevantImageAsync newImage;

    private int currentQuestion;
    private int correctQuestions;

    private ArrayList<Questions> questionsList;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_trivia);
        triviaBinding = DataBindingUtil.setContentView(this, R.layout.activity_trivia);
        newImage = new GetRelevantImageAsync(this);

        currentQuestion = 0;
        correctQuestions = 0;

        if(getIntent().getSerializableExtra(MainActivity.QUESTION_ARRAY_KEY) != null){
            questionsList = (ArrayList<Questions>) getIntent().getSerializableExtra(MainActivity.QUESTION_ARRAY_KEY);
            showQuestion(currentQuestion);
            startCountdown();
        }
        else
        {
            Toast.makeText(this,getResources().getString(R.string.toastNoDatabase),Toast.LENGTH_SHORT);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(timer != null){
            timer.cancel();
        }
    }

    private void startCountdown(){
        timer = new CountDownTimer(TOTAL_TIME * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                triviaBinding.textViewTime.setText(getString(R.string.textTimeLeft) + " "
                        + Long.toString(millisUntilFinished/1000) + " "
                        + getString(R.string.textSeconds));
            }

            @Override
            public void onFinish() {
                showResults();
            }
        }.start();
    }

    private void showQuestion(int i) {

        triviaBinding.imageQuestion.setImageBitmap(null);
        triviaBinding.progressBarImage.setVisibility(View.VISIBLE);
        triviaBinding.textImageLoadingLabel.setVisibility(View.VISIBLE);


        Questions question = questionsList.get(i);
        triviaBinding.textViewQuestionLabel.setText("Q" + Integer.toString(i + 1));
        triviaBinding.textViewQuestion.setText(question.getText());

        triviaBinding.radioGroupOptions.removeAllViews();
        for (String choice: question.getChoices()) {
            addRadioButton(choice);
        }

        if (question.getImage() != null){
            newImage = new GetRelevantImageAsync(this);
            newImage.execute(question.getImage());
        } else {
            triviaBinding.imageQuestion.setImageResource(R.drawable.noimage);
            triviaBinding.progressBarImage.setVisibility(View.INVISIBLE);
            triviaBinding.textImageLoadingLabel.setVisibility(View.INVISIBLE);
        }
    }

    private void addRadioButton(String radioButtonText){
        RadioButton radioButton = new RadioButton(this);
        radioButton.setText(radioButtonText);
        triviaBinding.radioGroupOptions.addView(radioButton,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void setImage(Bitmap image) {
        triviaBinding.imageQuestion.setImageBitmap(image);
        triviaBinding.progressBarImage.setVisibility(View.INVISIBLE);
        triviaBinding.textImageLoadingLabel.setVisibility(View.INVISIBLE);
    }

    public void nextQuestion(View view) {
        newImage.cancel(false);

        if(checkAnswer()) {
            correctQuestions++;
        }

        if (currentQuestion < questionsList.size() - 1) {
            currentQuestion++;
            showQuestion(currentQuestion);
        } else {
            showResults();
        }
    }

    private void showResults() {
        Intent intent = new Intent(this, StatsActivity.class);
        intent.putExtra(CORRECT_ANSWERS_KEY,correctQuestions);
        intent.putExtra(QUESTIONS_COUNT_KEY,questionsList.size());
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        currentQuestion = 0;
        correctQuestions = 0;
        showQuestion(currentQuestion);
        startCountdown();
    }

    public boolean checkAnswer(){
        int givenAnswer = triviaBinding.radioGroupOptions.indexOfChild(triviaBinding.radioGroupOptions.findViewById(triviaBinding.radioGroupOptions.getCheckedRadioButtonId())) + 1;

        if (questionsList.get(currentQuestion).getAnswer() == givenAnswer) {
            return true;
        } else {
            return false;
        }
    }

    public void quit(View view){
        finish();
    }
}
