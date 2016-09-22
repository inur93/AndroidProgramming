package weeklyprojects.pop.dk.week2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import weeklyprojects.pop.dk.week2.fragments.GameFragment;
import weeklyprojects.pop.dk.week2.fragments.HelpFragment;

public class MainActivity extends AppCompatActivity {


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);

        Fragment menu = new weeklyprojects.pop.dk.week2.fragments.Menu();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, menu)
                .commit();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
                Fragment game = new GameFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, game)
                        .addToBackStack(null)
                        .commit();
                return true;
            case R.id.menuHelp:
                Fragment help = new HelpFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, help)
                        .addToBackStack(null)
                        .commit();
                return true;
        }

        return retVal;
    }
}

