package weeklyprojects.pop.dk.week2.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import weeklyprojects.pop.dk.week2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameOver extends Fragment {


    public GameOver() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_game_over, container, false);

        ImageView img = (ImageView) root.findViewById(R.id.imgGameOver);


        if(getArguments().getBoolean("hasWon", false)){
            img.setImageResource(R.drawable.winner);
        }else{
            img.setImageResource(R.drawable.loser);
        }

        return root;
    }

}
