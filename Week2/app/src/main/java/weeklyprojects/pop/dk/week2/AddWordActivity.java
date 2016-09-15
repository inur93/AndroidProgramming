package weeklyprojects.pop.dk.week2;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddWordActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editAddWord;
    private ListView possibleWordsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        this.editAddWord = (EditText) findViewById(R.id.editWordAdd);
        Button addWord = (Button) findViewById(R.id.btnAddWord);
        Button removeWord = (Button) findViewById(R.id.btnRemoveWord);

        possibleWordsList = (ListView) findViewById(R.id.listView);

        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, HangmanController.getPossibleWords());
        possibleWordsList.setAdapter(adapter);
        addWord.setOnClickListener(this);
        removeWord.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddWord:
                String word = editAddWord.getText().toString();
                HangmanController.getPossibleWords().add(word);
                possibleWordsList.refreshDrawableState();
                break;
            case R.id.btnRemoveWord:
                Object selected = possibleWordsList.getSelectedItem();
                if(selected != null) {

                    HangmanController.getPossibleWords().remove(selected.toString());
                    possibleWordsList.refreshDrawableState();
                }
                break;
        }
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
}
