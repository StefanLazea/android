package eu.ase.bilet2restautoturism;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import eu.ase.bilet2restautoturism.json.HttpManager;
import eu.ase.bilet2restautoturism.json.HttpResponse;
import eu.ase.bilet2restautoturism.json.JsonParser;

public class ListActivity extends AppCompatActivity {

    private static final String URL = "https://api.myjson.com/bins/tidn6";
    private Intent intent;
    private ArrayList<Autovehicul> autovehicule = new ArrayList<>();
    private static final int REQUEST_CODE_UPDATE_AUTO = 201;
    private ListView lvAuto;
    private int selectedAutoIndex;
    private Button btnJson;
    private HttpResponse httpResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        intent = getIntent();
        initComponents();

        if (intent.hasExtra(MainActivity.ADD_LIST_KEY)) {
            autovehicule.addAll(intent.<Autovehicul>getParcelableArrayListExtra(MainActivity.ADD_LIST_KEY));
            Toast.makeText(getApplicationContext(), autovehicule.toString(), Toast.LENGTH_LONG).show();
            notifyAdapter();
        }
        addListToDB();
    }

    private void addListToDB() {
        if (autovehicule != null) {
            for (int i = 0; i < autovehicule.size(); i++) {
                insertIntoDb(autovehicule.get(i));
            }
        }
    }

    private void notifyAdapter() {
        ArrayAdapter<Autovehicul> adapter = (ArrayAdapter) lvAuto.getAdapter();
        adapter.notifyDataSetChanged();
    }

    private void initComponents() {
        lvAuto = findViewById(R.id.lvAuto);
        btnJson = findViewById(R.id.btnJson);

        ArrayAdapter<Autovehicul> adapter = new ArrayAdapter<>(
                getApplication(),
                android.R.layout.simple_list_item_1,
                autovehicule
        );
        lvAuto.setAdapter(adapter);


        lvAuto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedAutoIndex = position;
                Autovehicul auto = autovehicule.get(position);
                if (auto != null) {
                    Intent intent = new Intent(ListActivity.this, AddAutoActivity.class);
                    intent.putExtra(AddAutoActivity.ADD_AUTO_KEY, auto);
                    startActivityForResult(intent, REQUEST_CODE_UPDATE_AUTO);
                }
            }
        });
        btnJson.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {
                new HttpManager(){
                    @Override
                    protected void onPostExecute(String s) {
                        httpResponse = JsonParser.jsonParse(s);
                        if(httpResponse!=null){
                            autovehicule.addAll(httpResponse.getAutovehicule());
                            notifyAdapter();
                        }
                    }
                }.execute(URL);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_UPDATE_AUTO && resultCode == RESULT_OK && data != null) {
            Autovehicul auto = (Autovehicul) data.getParcelableExtra(AddAutoActivity.ADD_AUTO_KEY);
            Toast.makeText(getApplicationContext(), auto.toString(), Toast.LENGTH_LONG).show();
            updateItem(auto);
            notifyAdapter();
        }
    }

    private void updateItem(Autovehicul auto) {
        autovehicule.get(selectedAutoIndex).setNumarAuto(auto.getNumarAuto());
        autovehicule.get(selectedAutoIndex).setDataInregistrare(auto.getDataInregistrare());
        autovehicule.get(selectedAutoIndex).setIdLocParcare(auto.getIdLocParcare());
        autovehicule.get(selectedAutoIndex).setPlataEfectuata(auto.getPlataEfectuata());

    }

    @SuppressLint("StaticFieldLeak")
    private void insertIntoDb(Autovehicul auto) {
        new AutovehiculService.Insert(getApplicationContext()) {
            @Override
            protected void onPostExecute(Autovehicul autovehicul) {
                if (autovehicul != null) {
                    Log.i("DB INSERT >>>>", autovehicul.toString() + " a fost introdus in db");
                }
            }
        }.execute(auto);
    }

}
