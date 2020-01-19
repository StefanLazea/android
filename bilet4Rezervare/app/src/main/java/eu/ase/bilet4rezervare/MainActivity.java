package eu.ase.bilet4rezervare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import eu.ase.bilet4rezervare.json.HttpManager;
import eu.ase.bilet4rezervare.json.HttpResponse;
import eu.ase.bilet4rezervare.json.JsonParser;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ADD_RES = 200;
    public static final String URL = "https://api.myjson.com/bins/cyw56";
    private static final int REQUEST_CODE_UPDATE_RES = 201;
    private ArrayList<Rezervare> rezervari = new ArrayList<>();
    private ListView lvRezervari;
    private int selectedResIndex;
    private HttpResponse httpResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents() {
        lvRezervari = findViewById(R.id.lvRezervari);

        ArrayAdapter<Rezervare> adapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.support_simple_spinner_dropdown_item,
                rezervari);
        lvRezervari.setAdapter(adapter);

        lvRezervari.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedResIndex = position;
                Intent intent = new Intent(getApplicationContext(), AddReservationActivity.class);
                intent.putExtra(AddReservationActivity.ADD_RES_KEY, rezervari.get(position));
                startActivityForResult(intent, REQUEST_CODE_UPDATE_RES);
            }
        });

        lvRezervari.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                createAlertDialog(position);
                return true;
            }
        });


    }

    private void createAlertDialog(final int position) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Stergere element")
                .setMessage("Doriti sa stergeti elementul selectat?")
                .setPositiveButton("DA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rezervari.remove(position);
                        notifyAdapter();
                    }
                })
                .setNegativeButton("NU", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Nu s-a realizat stergerea", Toast.LENGTH_LONG).show();
                    }
                }).create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.about) {
            Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.add_reservation) {
            Intent intent = new Intent(getApplicationContext(), AddReservationActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_RES);
        }
        if (item.getItemId() == R.id.save_db) {
            adaugareInDb();
        }
        if (item.getItemId() == R.id.get_reservation) {
            getDataFromUrl();

        }
        return true;
    }

    @SuppressLint("StaticFieldLeak")
    private void getDataFromUrl() {
        new HttpManager() {
            @Override
            protected void onPostExecute(String s) {
                httpResponse = JsonParser.parseJson(s);
                if (httpResponse != null) {
                    rezervari.addAll(httpResponse.getRezervari());
                    notifyAdapter();
                }
            }
        }.execute(URL);
    }

    private void adaugareInDb() {
        if (rezervari != null) {
            for (int i = 0; i < rezervari.size(); i++) {
                insertIntoDb(rezervari.get(i));
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void insertIntoDb(Rezervare rezervare) {
        new RezervareService.Insert(getApplicationContext()) {
            @Override
            protected void onPostExecute(Rezervare result) {
                if (result != null) {
                    Log.i("DB insert", result.toString() + "was inserted");
                }
            }
        }.execute(rezervare);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_RES && resultCode == RESULT_OK && data != null) {
            Rezervare rez = (Rezervare) data.getParcelableExtra(AddReservationActivity.ADD_RES_KEY);
            if (rez != null) {
                rezervari.add(rez);
                notifyAdapter();
            }
        }
        if (requestCode == REQUEST_CODE_UPDATE_RES && resultCode == RESULT_OK && data != null) {
            Rezervare rez = (Rezervare) data.getParcelableExtra(AddReservationActivity.ADD_RES_KEY);
            if (rez != null) {
                updateEntry(rez);
                notifyAdapter();
            }
        }
    }

    private void updateEntry(Rezervare rez) {
        rezervari.get(selectedResIndex).setIdRezervare(rez.getIdRezervare());
        rezervari.get(selectedResIndex).setNumeClient(rez.getNumeClient());
        rezervari.get(selectedResIndex).setDurataSejur(rez.getDurataSejur());
        rezervari.get(selectedResIndex).setTipCamera(rez.getTipCamera());
        rezervari.get(selectedResIndex).setDataCazare(rez.getDataCazare());
    }

    private void notifyAdapter() {
        ArrayAdapter<Rezervare> adapter = (ArrayAdapter) lvRezervari.getAdapter();
        adapter.notifyDataSetChanged();
    }
}
