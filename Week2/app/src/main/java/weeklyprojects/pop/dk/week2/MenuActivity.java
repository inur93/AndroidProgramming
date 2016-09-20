package weeklyprojects.pop.dk.week2;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.GoogleAuthUtil;

import org.w3c.dom.Text;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{


    private TextView googleName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Button startGame = (Button) findViewById(R.id.btnStart);
        Button help = (Button) findViewById(R.id.btnHelp);
        Button addWords = (Button) findViewById(R.id.btnAddWords);
        Button googleLogin = (Button) findViewById(R.id.btnGoogleLogin);
        googleName = (TextView) findViewById(R.id.txtGoogleName);
        CheckBox useDrWords = (CheckBox) findViewById(R.id.useDrWords);
        useDrWords.setChecked(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("useDrWords", true));
        useDrWords.setOnClickListener(this);
        startGame.setOnClickListener(this);
        help.setOnClickListener(this);
        addWords.setOnClickListener(this);
        googleLogin.setOnClickListener(this);
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
            case R.id.btnGoogleLogin:
                loginWithGoogle();
                break;
            case R.id.useDrWords:
                PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("useDrWords",
                        ((CheckBox) v).isChecked()).commit();
        }
    }

    public void loginWithGoogle(){
        /*AccountManager manager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
        Account[] list = manager.getAccounts();
        for(Account a : list) {
            System.out.println(a.name);
        }*/

        AccountManager accountManager = AccountManager.get(this);
        Account[] accounts = accountManager.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
       //½ Log.e("", "Size: " + accounts.length);
        System.out.println("trying google login. accounts found: " + accounts.length+"\n"
        + "test: " + AccountManager.get(this).getAccountsByType("com.google").length+"\n"
        + "test1: " + AccountManager.get(this).getAccounts().length);
        for (Account account : accounts) {

            String possibleEmail = account.name;
            String type = account.type;

            if (type.equals("com.google")) {

                googleName.setText(possibleEmail);
                // Log.e("", "Emails: " + strGmail);
                break;
            }
        }
    }
}
