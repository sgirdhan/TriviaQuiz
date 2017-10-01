/*
 Homwork 4
 MainActiviy
 Sharan Girdhani     - 800960333
 Salman Mujtaba   - 800969897
*/

package com.example.sharangirdhani.homework04;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.sharangirdhani.homework04.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetTriviaDataAsync.GetTriviaData {
    private ActivityMainBinding binding;
    public static final String QUESTION_ARRAY_KEY = "QUESTIONS";
    private ArrayList<Questions> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        if(isOnline()) {
            new GetTriviaDataAsync(this).execute();
        } else {
            Toast.makeText(this,getString(R.string.toastNoConnection),Toast.LENGTH_LONG).show();
        }


    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setTriviaData(ArrayList<Questions> questions) {
        // TODO Better handling of no questions case
        if (questions.size() == 0){
            Toast.makeText(this, "No Questions Found on Server", Toast.LENGTH_SHORT).show();
        }
        else {
            binding.buttonStart.setEnabled(true);
            binding.progressTriviaLoading.setVisibility(View.INVISIBLE);
            binding.imageTriviaLogo.setVisibility(View.VISIBLE);
            binding.textTriviaStatus.setText(getString(R.string.textReady));
            this.questions = questions;
            Log.d("demo", questions.toString());
        }
    }

    public void endActivity(View view){
        finish();
    }

    public void startTrivia(View view){
        Intent triviaIntent = new Intent(this,TriviaActivity.class);
        triviaIntent.putExtra(QUESTION_ARRAY_KEY,questions);
        startActivity(triviaIntent);
    }
}
