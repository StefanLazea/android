package eu.ase.bilet1restantaarticol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private ListView lvArticole;
    private Intent intent;
    private ArrayList<Articol> articole = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initComponents();
        intent = getIntent();
        if (intent.hasExtra(MainActivity.ADD_ARTICLE_TO_LIST)) {
            ArrayList<Articol> lista = intent.getParcelableArrayListExtra(MainActivity.ADD_ARTICLE_TO_LIST);
            addArticleToList(lista);
            Toast.makeText(getApplicationContext(), articole.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void addArticleToList(ArrayList<Articol> lista) {
        for (int i = 0; i < lista.size(); i++) {
            articole.add(lista.get(i));
        }
        notifyAdapter();
    }

    private void initComponents() {
        lvArticole = findViewById(R.id.lvArticole);

        ArrayAdapter<Articol> adapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                articole
        );
        lvArticole.setAdapter(adapter);

    }

    private void notifyAdapter() {
        ArrayAdapter<Articol> adapter = (ArrayAdapter) lvArticole.getAdapter();
        adapter.notifyDataSetChanged();
    }
}
