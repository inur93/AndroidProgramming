package weeklyprojects.pop.dk.week2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        ImageView img = (ImageView) findViewById(R.id.imgGameOver);


        if( getIntent().getExtras().getBoolean("hasWon", false)){
            img.setImageResource(R.drawable.winner);
        }else{
            img.setImageResource(R.drawable.loser);
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
        switch(item.getItemId()){
            case R.id.menuRestart:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.menuHelp:
                Intent help = new Intent(this, HelpActivity.class);
                startActivity(help);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
