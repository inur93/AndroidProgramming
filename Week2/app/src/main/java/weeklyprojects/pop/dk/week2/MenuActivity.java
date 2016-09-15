package weeklyprojects.pop.dk.week2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Button startGame = (Button) findViewById(R.id.btnStart);
        Button help = (Button) findViewById(R.id.btnHelp);
        Button addWords = (Button) findViewById(R.id.btnAddWords);

        startGame.setOnClickListener(this);
        help.setOnClickListener(this);
        addWords.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnStart:
                Intent start = new Intent(this, MainActivity.class);
                startActivity(start);
                break;
            case R.id.btnAddWords:
                Intent add = new Intent(this, AddWordActivity.class);
                startActivity(add);
                break;
            case R.id.btnHelp:
                Intent help = new Intent(this, HelpActivity.class);
                startActivity(help);
                break;
        }
    }
}
