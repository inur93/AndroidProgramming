package weeklyprojects.pop.dk.week2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText usedLetters;
    private EditText visibleWord;
    private EditText inputWord;
    private Button btnSelect;
    private ImageView imageView;

    private HangmanController hangmanController;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.usedLetters = (EditText) findViewById(R.id.inputUsedLetter);
        this.visibleWord = (EditText) findViewById(R.id.visibleWord);
        this.inputWord = (EditText) findViewById(R.id.inputSelectLetter);
        this.btnSelect = (Button) findViewById(R.id.btnSelectLetter);
        this.imageView = (ImageView) findViewById(R.id.imageView);
        Button restartButton = (Button) findViewById(R.id.btnRestart);

        this.usedLetters.setEnabled(false);
        this.visibleWord.setEnabled(false);

        this.btnSelect.setOnClickListener(this);
        restartButton.setOnClickListener(this);
        hangmanController = new HangmanController();

        restart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onClick(View v) {
        System.out.println("button: " + v.getId() + " restart: " + R.id.btnRestart);
        switch (v.getId()) {
            case R.id.btnSelectLetter:
                String letter = inputWord.getText().toString();

                guessLetter(letter);

                break;
            case R.id.btnRestart:
                System.out.println("button restart");
                restart();
                break;
        }
        if (hangmanController.isGameOver()) {
            if (hangmanController.hasLost()) {
                Intent lost = new Intent(this, GameOverActivity.class);
                lost.putExtra("hasWon", false);
                startActivity(lost);
            } else if (hangmanController.hasWon()) {
                Intent won = new Intent(this, GameOverActivity.class);
                won.putExtra("hasWon", true);
                startActivity(won);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean retVal = super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.menuRestart:
                restart();
                return true;
            case R.id.menuHelp:
                Intent intent = new Intent(this, HelpActivity.class);
                startActivity(intent);
                return true;
        }

        return retVal;
    }

    public void restart() {
        System.out.println("resetting game...");
        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("useDrWords", true)){
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
            Toast.makeText(this, "Come on.. don't be stupid. You need to type a letter.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (hangmanController.getUsedLetters().contains(letter)) {
            Toast.makeText(this, "You already tried this letter. Try again?", Toast.LENGTH_SHORT).show();
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
    @Override
    protected void onResume() {
        super.onResume();
        if (hangmanController.isGameOver()) {
            restart();
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://weeklyprojects.pop.dk.week2/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://weeklyprojects.pop.dk.week2/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}

