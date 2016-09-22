package weeklyprojects.pop.dk.week2.fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import weeklyprojects.pop.dk.week2.GameOverActivity;
import weeklyprojects.pop.dk.week2.HangmanController;
import weeklyprojects.pop.dk.week2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment implements View.OnClickListener{

    private EditText usedLetters;
    private EditText visibleWord;
    private EditText inputWord;
    private Button btnSelect;
    private ImageView imageView;

    private HangmanController hangmanController;

    public GameFragment() {
        super();
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_game, container, false);
        usedLetters = (EditText) root.findViewById(R.id.txtGuessedLetters);
        visibleWord = (EditText) root.findViewById(R.id.txtWordToGuess);
        inputWord = (EditText) root.findViewById(R.id.inputGuess);
        btnSelect = (Button) root.findViewById(R.id.btnGuess);
        imageView = (ImageView) root.findViewById(R.id.imageView);

        usedLetters.setEnabled(false);
        visibleWord.setEnabled(false);

        btnSelect.setOnClickListener(this);

        hangmanController = new HangmanController();

        restart();
        return root;
    }

    @Override
    public void onClick(View v) {
        //System.out.println("button: " + v.getId() + " restart: " + R.id.btnRestart);
        switch (v.getId()) {
            case R.id.btnGuess:
                String letter = inputWord.getText().toString();
                guessLetter(letter);
                break;
        }

        if (hangmanController.isGameOver()) {
            Bundle b = new Bundle();
            Fragment gameOver = new GameOver();
            if (hangmanController.hasLost()) {
                b.putBoolean("hasWon", false);
            } else if (hangmanController.hasWon()) {
                b.putBoolean("hasWon", true);
            }
            gameOver.setArguments(b);
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, gameOver)
                    .commit();
        }

    }

    public void restart() {
        System.out.println("resetting game...");
        if(PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean("useDrWords", true)){
            getExternalWords();
        }else {
            hangmanController.getPossibleWords().clear();
            hangmanController.getPossibleWords();
            hangmanController.reset();
        }
        visibleWord.setText(hangmanController.getVisibleWord());
        imageView.setImageResource(R.drawable.wrong0);
        usedLetters.setText("");
        inputWord.setText("");
    }

    public void guessLetter(String letter) {
        if (!isLetterValid(letter)) return;

        hangmanController.guessLetter(letter);

        usedLetters.setText(stringify(hangmanController.getUsedLetters()));
        visibleWord.setText(hangmanController.getVisibleWord());
        imageView.setImageResource(getImageId(hangmanController.getNumberWrongLetters()));
        inputWord.setText("");


    }

    public int getImageId(int wrongLetters) {
        switch (wrongLetters) {
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

    public String stringify(List<String> list) {
        String newVal = "";
        for (String s : list) {
            newVal += s;
        }
        return newVal;
    }

    public boolean isLetterValid(String letter) {

        if (letter.length() != 1) {
            Toast.makeText(getActivity(), "Come on.. don't be stupid. You need to type a letter.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (hangmanController.getUsedLetters().contains(letter)) {
            Toast.makeText(getActivity(), "You already tried this letter. Try again?", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void getExternalWords(){
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object... arg0) {
                try {
                    hangmanController.getWordFromDR();
                    return "Ordene blev korrekt hentet fra DR's server";
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Ordene blev ikke hentet korrekt: "+e;
                }
            }

            @Override
            protected void onPostExecute(Object resultat) {
                System.out.println("resultat: \n" + resultat);
            }
        }.execute();



    }
    /*@Override
    protected void onResume() {
        super.onResume();
        if (hangmanController.isGameOver()) {
            restart();
        }
    }*/

}
