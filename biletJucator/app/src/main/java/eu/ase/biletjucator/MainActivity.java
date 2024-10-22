package eu.ase.biletjucator;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.CamcorderProfile;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import eu.ase.biletjucator.json.HttpManager;
import eu.ase.biletjucator.json.HttpResponse;
import eu.ase.biletjucator.json.JsonParser;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ADD_PLAYER = 200;
    private static final int REQUEST_CODE_UPDATE_PLAYER = 201;
    private ArrayList<Jucator> listPlayers = new ArrayList<>();
    private ListView lvPlayers;
    private int selectedPlayerIndex;
    private Button saveToDb;

    private HttpResponse httpResponse;
    private static final String URL = "test";

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey("PLAYERS")) {
            listPlayers = savedInstanceState.getParcelableArrayList("PLAYERS");
        }
        setContentView(R.layout.activity_main);
        initComponents();
        if (savedInstanceState != null && savedInstanceState.containsKey("PLAYERS")) {
            notifyAdapter();
        }
//
//        new HttpManager(){
//            @Override
//            protected void onPostExecute(String s) {
//                httpResponse = JsonParser.parseJson(s);
//                if(httpResponse!=null){
//                    Toast.makeText(getApplicationContext(), "Ura", Toast.LENGTH_LONG).show();
//                }
//            }
//        }.execute(URL);
    }


    private void initComponents() {
        lvPlayers = findViewById(R.id.add_lv_players);
        saveToDb = findViewById(R.id.main_save_db);
        //seteaza un array adapter pe lista noastra
        ArrayAdapter<Jucator> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1,
                listPlayers
        );
        lvPlayers.setAdapter(adapter);

        lvPlayers.setOnItemClickListener(lvPlayersItemSelected());
        lvPlayers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                buildAlertDialog(position);
                return true;
            }
        });

        saveToDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addListToDb();
            }
        });

    }

    private void addListToDb() {
        if (listPlayers != null) {
            for (int i = 0; i < listPlayers.size(); i++) {
                insertIntoDb(listPlayers.get(i));
            }
        }
        getAllFromDb();
    }

    private void buildAlertDialog(final int position) {
        AlertDialog alert = new AlertDialog.Builder(this)
                .setTitle("Stergere jucator")
                .setMessage(getString(R.string.main_delete_alert,
                        listPlayers.get(position).getNume()))
                .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listPlayers.remove(position);
                        notifyAdapter();

                    }
                }).setNegativeButton("Nu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Ok", Toast.LENGTH_LONG).show();
                    }
                }).create();

        alert.show();

    }

    private AdapterView.OnItemClickListener lvPlayersItemSelected() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), AddPlayerActivity.class);
                selectedPlayerIndex = position;
                //Trimitem obiectul selectat pentru a fi modificat
                intent.putExtra(AddPlayerActivity.ADD_PLAYER_KEY, listPlayers.get(position));
                startActivityForResult(intent, REQUEST_CODE_UPDATE_PLAYER);
            }
        };
    }

    //in metoda revine intent-ul trimis din AddPlayerActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_PLAYER && resultCode == RESULT_OK && data != null) {
            Jucator jucator = (Jucator) data.getParcelableExtra(AddPlayerActivity.ADD_PLAYER_KEY);
            if (jucator != null) {
                listPlayers.add(jucator);
                Toast.makeText(getApplicationContext(), listPlayers.toString(), Toast.LENGTH_LONG).show();
                //notifica adapterul setat pe listview ca a fost adus un nou jucator
                notifyAdapter();
            }
        }
        //daca se duce cu intent ul cu UPDATE, se va intoarce si trebuie updatat player ul de la pozitia data
        if (requestCode == REQUEST_CODE_UPDATE_PLAYER && resultCode == RESULT_OK && data != null) {
            Jucator jucator = (Jucator) data.getParcelableExtra(AddPlayerActivity.ADD_PLAYER_KEY);
            if (jucator != null) {
                updatePlayer(jucator);
                Toast.makeText(getApplicationContext(), listPlayers.toString(), Toast.LENGTH_LONG).show();
                notifyAdapter();
            }
        }
    }

    private void updatePlayer(Jucator jucator) {
        listPlayers.get(selectedPlayerIndex).setNume(jucator.getNume());
        listPlayers.get(selectedPlayerIndex).setNumar(jucator.getNumar());
        listPlayers.get(selectedPlayerIndex).setDataNastere(jucator.getDataNastere());
        listPlayers.get(selectedPlayerIndex).setPozitie(jucator.getPozitie());
    }

    private void notifyAdapter() {
        ArrayAdapter<Jucator> adapter = (ArrayAdapter) lvPlayers.getAdapter();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //tratez click urile pe meniu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.adaugaJucator) {
            Intent intent = new Intent(getApplicationContext(),
                    AddPlayerActivity.class);
            //lansez cu forResult pentru a imi intoarce
            //fara a pune extras; deci implicit vrem sa ne aduca un jucator nou
            startActivityForResult(intent, REQUEST_CODE_ADD_PLAYER);
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("PLAYERS", listPlayers);
    }

    @SuppressLint("StaticFieldLeak")
    private void insertIntoDb(Jucator jucator) {
        new JucatorService.Insert(getApplicationContext()) {
            @Override
            protected void onPostExecute(Jucator result) {
                if (result != null) {
//                    listPlayers.add(result);
//                    notifyAdapter();
                    Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();

                }
            }
        }.execute(jucator);
    }

    @SuppressLint("StaticFieldLeak")
    private void getAllFromDb() {
        new JucatorService.GetAll(getApplicationContext()) {
            @Override
            protected void onPostExecute(List<Jucator> result) {
                if (result != null) {
//                    listPlayers.clear();
//                    listPlayers.addAll(result);
//                    notifyAdapter();
                    Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }
}
