package weeklyprojects.pop.dk.week2;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText usedLetters;
    private EditText visibleWord;
    private EditText inputWord;
    private Button btnSelect;
    private ImageView imageView;

    private  HangmanController hangmanController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.usedLetters = (EditText) findViewById(R.id.inputUsedLetter);
        this.visibleWord = (EditText) findViewById(R.id.visibleWord);
        this.inputWord = (EditText) findViewById(R.id.inputSelectLetter);
        this.btnSelect = (Button) findViewById(R.id.btnSelectLetter);
        this.imageView = (ImageView) findViewById(R.id.imageView);

        this.usedLetters.setEnabled(false);
        this.visibleWord.setEnabled(false);

        this.btnSelect.setOnClickListener(this);
        hangmanController = new HangmanController();

        restart();

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnSelectLetter:
                String letter = inputWord.getText().toString();

                guessLetter(letter);

                break;
            case R.id.btnRestart:
                restart();
                break;
        }
    }

    public void restart(){
        hangmanController.reset();
        visibleWord.setText(hangmanController.getVisibleWord());
        imageView.setImageResource(R.drawable.wrong0);
        usedLetters.setText("");
        inputWord.setText("");
    }
    public void guessLetter(String letter){
        if(!isLetterValid(letter)) return;

        hangmanController.guessLetter(letter);

        usedLetters.setText(stringify(hangmanController.getUsedLetters()));
        visibleWord.setText(hangmanController.getVisibleWord());
        imageView.setImageResource(getImageId(hangmanController.getNumberWrongLetters()));
        inputWord.setText("");


    }

    public int getImageId(int wrongLetters){
        switch (wrongLetters){
            case 0:
                return R.drawable.wrong0;
            case 1:
                return R.drawable.wrong1;
            case 2:
                return R.drawable.wrong2;
            case 3:
                return R.drawable.wrong3;
            case 4:
                return R.drawable.wrong4;
            case 5:
                return R.drawable.wrong5;
            default:
                return R.drawable.wrong6;
        }
    }

    public String stringify(List<String> list){
        String newVal = "";
        for(String s : list){
            newVal+= s;
        }
        return newVal;
    }

    public boolean isLetterValid(String letter){
        if(letter.length() != 1) return false;
        if(hangmanController.getUsedLetters().contains(letter)) return false;

        return true;
    }


}

