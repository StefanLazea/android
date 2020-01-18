package eu.ase.bilet6curs;

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
import java.util.List;

import eu.ase.bilet6curs.json.HttpManager;
import eu.ase.bilet6curs.json.HttpResponse;
import eu.ase.bilet6curs.json.JsonParser;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD_CURS = 200;
    private static final int REQUEST_CODE_UPDATE_CURS = 201;
    public static final String URL = "https://api.myjson.com/bins/t8v1u";
    private List<Curs> cursuri = new ArrayList<>();
    private ListView lvCursuri;
    private int selectedCursIndex;
    private Button btnSaveToDb;
    private HttpResponse httpResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents() {
        lvCursuri = findViewById(R.id.lv_cursuri);
        btnSaveToDb = findViewById(R.id.main_save_db);

        ArrayAdapter<Curs> adapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                cursuri);
        lvCursuri.setAdapter(adapter);

        lvCursuri.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), AddCourseActivity.class);
                selectedCursIndex = position;
                intent.putExtra(AddCourseActivity.ADD_CURS_KEY, cursuri.get(position));
                startActivityForResult(intent, REQUEST_CODE_UPDATE_CURS);
            }
        });

        lvCursuri.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                createAlertDialog(position);
                return true;
            }
        });

        btnSaveToDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < cursuri.size(); i++) {
                    insertIntoDb(cursuri.get(i));
                }
            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    private void insertIntoDb(Curs curs) {
        new CursService.Insert(getApplicationContext()) {
            @Override
            protected void onPostExecute(Curs result) {
                if (result != null) {
                    Log.i("Db insert", "S-a inserat cursul cu id "+ result.getIdCurs() + result.toString());
                    Toast.makeText(getApplicationContext(), "Data salvata cu succes",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(curs);
    }

    private void createAlertDialog(final int position) {
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Stergere element")
                .setMessage("Sunteti sigur ca vreti sa stergeti elementul?")
                .setPositiveButton("DA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cursuri.remove(position);
                        notifyAdapter();
                    }
                })
                .setNegativeButton("NU", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "NU s-a sters!", Toast.LENGTH_LONG).show();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_CURS && resultCode == RESULT_OK && data != null) {
            Curs curs = (Curs) data.getParcelableExtra(AddCourseActivity.ADD_CURS_KEY);
            if (curs != null) {
                cursuri.add(curs);
                Toast.makeText(getApplicationContext(), cursuri.toString(), Toast.LENGTH_LONG).show();
                notifyAdapter();
            }
        }

        if (requestCode == REQUEST_CODE_UPDATE_CURS && resultCode == RESULT_OK && data != null) {
            Curs curs = (Curs) data.getParcelableExtra(AddCourseActivity.ADD_CURS_KEY);
            if (curs != null) {
                updateCurs(curs);
                notifyAdapter();
            }
        }
    }

    private void updateCurs(Curs curs) {
        cursuri.get(selectedCursIndex).setIdCurs(curs.getIdCurs());
        cursuri.get(selectedCursIndex).setDenumire(curs.getDenumire());
        cursuri.get(selectedCursIndex).setNumarParticipanti(curs.getNumarParticipanti());
        cursuri.get(selectedCursIndex).setSala(curs.getSala());
        cursuri.get(selectedCursIndex).setProfesorTitular(curs.getProfesorTitular());
    }

    private void notifyAdapter() {
        ArrayAdapter<Curs> adapter = (ArrayAdapter) lvCursuri.getAdapter();
        adapter.notifyDataSetChanged();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_curs) {
            Intent intent = new Intent(getApplicationContext(), AddCourseActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_CURS);
        }
        if (item.getItemId() == R.id.sync) {

            new HttpManager() {
                @Override
                protected void onPostExecute(String s) {
                    httpResponse = JsonParser.parseJson(s);
                    if (httpResponse != null) {
                        addCursuriToListView(httpResponse.getCursuri());
                        Toast.makeText(getApplicationContext(), httpResponse.getCursuri().toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }.execute(URL);
        }
        return true;
    }

    private void addCursuriToListView(List<Curs> result) {
        cursuri.addAll(result);
        notifyAdapter();
    }

}
