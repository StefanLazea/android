package eu.ase.biletjucator;

import android.content.Intent;
import android.media.CamcorderProfile;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ADD_PLAYER = 200;
    private static final int REQUEST_CODE_UPDATE_PLAYER = 201;
    private ArrayList<Jucator> listPlayers = new ArrayList<>();
    private ListView lvPlayers;
    private int selectedPlayerIndex;

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
    }

    private void initComponents() {
        lvPlayers = findViewById(R.id.add_lv_players);
        //seteaza un array adapter pe lista noastra
        ArrayAdapter<Jucator> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1,
                listPlayers
        );
        lvPlayers.setAdapter(adapter);

        lvPlayers.setOnItemClickListener(lvPlayersItemSelected());
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
        if(requestCode == REQUEST_CODE_UPDATE_PLAYER && resultCode == RESULT_OK && data!=null){
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

}
