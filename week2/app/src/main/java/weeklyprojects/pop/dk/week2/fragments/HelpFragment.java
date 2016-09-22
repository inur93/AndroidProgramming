package weeklyprojects.pop.dk.week2.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.androidquery.AQuery;

import weeklyprojects.pop.dk.week2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends Fragment {

    private AQuery aq;

    public HelpFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        this.aq = new AQuery(view);
        WebView webView = aq.find(R.id.helpView).getWebView();
        webView.loadUrl("file:///android_asset/help.html");

        return view;
    }

}
