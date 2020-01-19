package eu.ase.bilet1restantaarticol;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_UPDATE_ARTICLE = 201;
    private ListView lvArticole;
    private Intent intent;
    private ArrayList<Articol> articole = new ArrayList<>();
    public int positionSelectedIndex;

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
            insertIntoDb(lista.get(i));
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

        lvArticole.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                positionSelectedIndex = position;
                Articol articol = articole.get(position);
                if (articol != null) {
                    Intent intent = new Intent(ListActivity.this, AddArticleActivity.class);
                    intent.putExtra(AddArticleActivity.ADD_ARTICLE_KEY, articol);
                    startActivityForResult(intent, REQUEST_CODE_UPDATE_ARTICLE);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_UPDATE_ARTICLE && resultCode == RESULT_OK && data != null) {
            Articol art = (Articol) data.getParcelableExtra(AddArticleActivity.ADD_ARTICLE_KEY);
            setItemFromList(art);
            notifyAdapter();
        }
    }

    private void setItemFromList(Articol art) {
        articole.get(positionSelectedIndex).setTitlu(art.getTitlu());
        articole.get(positionSelectedIndex).setNumarAutori(art.getNumarAutori());
        articole.get(positionSelectedIndex).setPrimaPagina(art.getPrimaPagina());
        articole.get(positionSelectedIndex).setUltimaPagina(art.getPrimaPagina());
    }

    private void notifyAdapter() {
        ArrayAdapter<Articol> adapter = (ArrayAdapter) lvArticole.getAdapter();
        adapter.notifyDataSetChanged();
    }


    @SuppressLint("StaticFieldLeak")
    private void insertIntoDb(Articol articol) {
        new ArticolService.Insert(getApplicationContext()) {
            @Override
            protected void onPostExecute(Articol articol) {
                if (articol != null) {
                    Log.i("INSERT DB >>>>>>>>", "Articolul a fost introdus in baza de date "
                            + articol.toString());
                }
            }
        }.execute(articol);
    }
}
