package weeklyprojects.pop.dk.week2.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import weeklyprojects.pop.dk.week2.AddWordActivity;
import weeklyprojects.pop.dk.week2.HelpActivity;
import weeklyprojects.pop.dk.week2.MainActivity;
import weeklyprojects.pop.dk.week2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Menu extends Fragment implements View.OnClickListener {


    public Menu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_menu, container, false);

        Button startGame = (Button) root.findViewById(R.id.btnStartGame);
        Button help = (Button) root.findViewById(R.id.btnHelp);
        CheckBox useDrWords = (CheckBox) root.findViewById(R.id.checkUseDRWords);
        useDrWords.setChecked(PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean("useDrWords", true));
        useDrWords.setOnClickListener(this);
        startGame.setOnClickListener(this);
        help.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch(v.getId()){
            case R.id.btnStartGame:
                fragment = new GameFragment();
                break;
            case R.id.btnHelp:
                fragment = new HelpFragment();
                break;
            case R.id.useDrWords:
                PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putBoolean("useDrWords",
                        ((CheckBox) v).isChecked()).commit();
        }

        if(fragment != null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

}
