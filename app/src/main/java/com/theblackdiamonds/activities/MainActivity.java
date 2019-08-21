package com.theblackdiamonds.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.theblackdiamonds.R;
import com.theblackdiamonds.adapters.GridViewAnswerAdapter;
import com.theblackdiamonds.adapters.GridViewSuggestAdapter;
import com.theblackdiamonds.models.Common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public List<String> suggestSource = new ArrayList<>();
    public GridViewAnswerAdapter answerAdapter;
    public GridViewSuggestAdapter suggestAdapter;
    public Button btnSubmit;
    public GridView gridViewAnswer, gridViewSuggest;
    public ImageView imageView;
    public char[] answer;
    int[] img_list = {R.drawable.apple, R.drawable.dropbox,
            R.drawable.pinterest, R.drawable.youtube};
    String correct_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        gridViewAnswer = findViewById(R.id.gridViewAnswer);
        gridViewSuggest = findViewById(R.id.gridViewSuggest);
        imageView = findViewById(R.id.imageView);
        btnSubmit = findViewById(R.id.btnSubmit);

        setUpList();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = "";
                for (int i = 0; i < Common.user_submit_answer.length; i++) {
                    result += String.valueOf(Common.user_submit_answer[i]);
                }

                if (result.equals(correct_answer)) {
                    Toast.makeText(MainActivity.this, "Finish! This is " + result, Toast.LENGTH_SHORT).show();

                    GridViewAnswerAdapter answerAdapter = new GridViewAnswerAdapter(setUpNullList(), MainActivity.this);
                    gridViewAnswer.setAdapter(answerAdapter);
                    answerAdapter.notifyDataSetChanged();

                    GridViewSuggestAdapter suggestAdapter = new GridViewSuggestAdapter(suggestSource, MainActivity.this, MainActivity.this);
                    gridViewAnswer.setAdapter(suggestAdapter);
                    suggestAdapter.notifyDataSetChanged();

                    setUpList();
                } else {
                    Toast.makeText(MainActivity.this, "Incorrect!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setUpList() {
        Random random = new Random();
        int imageSelected = img_list[random.nextInt(img_list.length)];
        imageView.setImageResource(imageSelected);

        correct_answer = getResources().getResourceName(imageSelected);
        correct_answer = correct_answer.substring(correct_answer.lastIndexOf("/") + 1);

        answer = correct_answer.toCharArray();
        Common.user_submit_answer = new char[answer.length];

        suggestSource.clear();

        for (char item : answer) {
            suggestSource.add(String.valueOf(item));
        }

        for (int i = answer.length; i < answer.length * 2; i++)
            suggestSource.add(Common.alphabet_character[random.nextInt(Common.alphabet_character.length)]);

        Collections.shuffle(suggestSource);

        answerAdapter = new GridViewAnswerAdapter(setUpNullList(), MainActivity.this);
        suggestAdapter = new GridViewSuggestAdapter(suggestSource, MainActivity.this, MainActivity.this);

        answerAdapter.notifyDataSetChanged();
        suggestAdapter.notifyDataSetChanged();

        gridViewAnswer.setAdapter(answerAdapter);
        gridViewSuggest.setAdapter(suggestAdapter);
    }

    private char[] setUpNullList() {
        char[] result = new char[answer.length];

        for (int i = 0; i < answer.length; i++)
            result[i] = ' ';
        return result;
    }
}