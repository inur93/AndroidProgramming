package weeklyprojects.pop.dk.week2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.androidquery.AQuery;

public class HelpActivity extends AppCompatActivity {

    AQuery aq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        this.aq = new AQuery(this);
        aq.find(R.id.webView).getWebView().loadUrl("file:///android_asset/help.html");
    }
}
