package weeklyprojects.pop.dk.week1;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtStatus;
    private Button btnSettings, btnGuide, btnGo;
    private EditText inputAddress;
    private WebView webView;
    private String defaultStatus = "try ignore http prefix";

    private Timer timer;
    private TimerTask task;
    final Handler handler = new Handler();
    final Runnable runnable = new Runnable() {
        public void run() {
            txtStatus.setText(defaultStatus);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.txtStatus = (TextView) findViewById(R.id.txtStatus);
        this.txtStatus.setText(defaultStatus);
        this.timer = new Timer();


        this.btnGuide = (Button) findViewById(R.id.btnGuide);
        this.btnSettings = (Button) findViewById(R.id.btnSettings);
        this.btnGo = (Button) findViewById(R.id.btnGo);
        this.webView = (WebView) findViewById(R.id.webView);
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.inputAddress = (EditText) findViewById(R.id.inputAddress);

        this.txtStatus.setOnClickListener(this);
        this.btnGuide.setOnClickListener(this);
        this.btnSettings.setOnClickListener(this);
        this.btnGo.setOnClickListener(this);

        this.webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
                view.loadUrl(request.getUrl().toString());
                return false;
            }
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                view.loadUrl(url);
                return false; // then it is not handled by default action
            }
        });
    }

    public void setStatusText(String text){
        txtStatus.setText(text);
        handler.postDelayed(runnable,2000);

/*        if(task != null) task.cancel();

        task = new TimerTask() {
            @Override
            public void run() {
               handler.handleMessage(null);
            }
        };
        timer.schedule(task, 2000);*/
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnGuide:
                setStatusText("You pressed guide button");
                break;
            case R.id.btnSettings:
                setStatusText("You pressed settings button");
                break;
            case R.id.btnGo:
                    String address = inputAddress.getText().toString();
                    String statusMsg = "URL is valid";
                    if(!address.startsWith("http://") && !address.startsWith("https://")){
                        address = "http://lmgtfy.com/?q=" + address.replace(" ", "+");

                        statusMsg = "let me google that for you";
                    }

                    if(URLUtil.isValidUrl(address)) {
                        webView.loadUrl(address);
                        setStatusText(statusMsg);
                    }else {
                        setStatusText("your url is invalid. remember to add http:// as prefix");
                    }
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(inputAddress.getWindowToken(), 0);
                break;
            default:
                setStatusText("I Have no idea what you did");
                break;
        }
    }
}
