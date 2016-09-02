package weeklyprojects.pop.dk.week1;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtStatus;
    private Button btnSettings, btnGuide, btnGo;
    private EditText inputAddress;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.txtStatus = (TextView) findViewById(R.id.txtStatus);
        this.btnGuide = (Button) findViewById(R.id.btnGuide);
        this.btnSettings = (Button) findViewById(R.id.btnSettings);
        this.btnGo = (Button) findViewById(R.id.btnGo);
        this.webView = (WebView) findViewById(R.id.webView);
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

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnGuide:
                txtStatus.setText("You pressed guide button");
                break;
            case R.id.btnSettings:
                txtStatus.setText("You pressed settings button");
                break;
            case R.id.btnGo:
                    String address = inputAddress.getText().toString();

                    if(URLUtil.isValidUrl(address)) {
                        webView.loadUrl(address);
                        txtStatus.setText("URL is valid");
                    }else {
                        txtStatus.setText("your url is invalid. remember to add http:// as prefix");
                    }
                break;
            default:
                txtStatus.setText("I Have no idea what you did");
                break;
        }
    }
}
