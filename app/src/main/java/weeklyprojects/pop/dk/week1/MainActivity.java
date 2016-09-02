package weeklyprojects.pop.dk.week1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button myveryown;
    private TextView mystory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // set fields
        this.myveryown = (Button)findViewById(R.id.myveryown);
        this.mystory = (TextView) findViewById(R.id.story);

        // add listeners
        this.myveryown.setOnClickListener(this);
        this.mystory.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.myveryown:
                System.out.println("my veryown id");
                break;
            case R.id.story:
                System.out.println("my story");
                break;
            default:
                System.out.println("I have no idea what I am doing");
                break;
        }
    }
}
