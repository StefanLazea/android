package eu.ase.bilet5examen;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import eu.ase.bilet5examen.json.HttpManager;
import eu.ase.bilet5examen.json.HttpResponse;
import eu.ase.bilet5examen.json.JsonParser;

public class MainActivity extends AppCompatActivity {

    public static final String URL = "https://api.myjson.com/bins/aueaa";
    public static final int REQUEST_CODE_ADD_EXAM = 200;
    public static final int REQUEST_CODE_UPDATE_EXAM = 201;
    private List<Examen> examene = new ArrayList<>();
    private ListView lvExamene;
    private Button btnSend;
    private int selectePositionIndex;
    private HttpResponse httpResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents() {
        lvExamene = findViewById(R.id.lvExamene);
        btnSend = findViewById(R.id.main_btnSave);

        ArrayAdapter<Examen> adapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                examene);
        lvExamene.setAdapter(adapter);

        lvExamene.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectePositionIndex = position;
                Intent intent = new Intent(getApplicationContext(), AddExamActivity.class);
                intent.putExtra(AddExamActivity.ADD_EXAM_KEY, examene.get(position));
                startActivityForResult(intent, REQUEST_CODE_UPDATE_EXAM);
            }
        });
        lvExamene.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                buildAlertDialog(position);
                return true;
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (examene != null) {
                    addListToDb();
                }
            }
        });
    }

    private void addListToDb() {
        for (int i = 0; i < examene.size(); i++) {
            insertIntoDb(examene.get(i));
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void insertIntoDb(Examen examen) {
        new ExamenService.Insert(getApplicationContext()) {
            @Override
            protected void onPostExecute(Examen examen) {
                if (examen != null) {
                    Log.i("DB insert", "Exam: " + examen.toString() + " was inserted");
                }
            }
        }.execute(examen);
    }

    private void buildAlertDialog(final int position) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Stergere element")
                .setMessage("Doriti sa stergeti elementul de pe pozitia selectata?")
                .setPositiveButton("DA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        examene.remove(position);
                        notifyAdapter();
                    }
                })
                .setNegativeButton("Nu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Nu s-a sters", Toast.LENGTH_SHORT).show();
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
        if (item.getItemId() == R.id.add_exam) {
            Intent intent = new Intent(getApplicationContext(), AddExamActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_EXAM);
        }
        if (item.getItemId() == R.id.sync) {
            addExamListFromJson();
        }
        return true;

    }

    @SuppressLint("StaticFieldLeak")
    private void addExamListFromJson() {
        new HttpManager() {
            @Override
            protected void onPostExecute(String s) {
                httpResponse = JsonParser.parseJson(s);
                if (httpResponse != null) {
                    examene.addAll(httpResponse.getExamen());
                    notifyAdapter();
                }
            }
        }.execute(URL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_EXAM && resultCode == RESULT_OK && data != null) {
            Examen ex = (Examen) data.getParcelableExtra(AddExamActivity.ADD_EXAM_KEY);
            if (ex != null) {
                examene.add(ex);
                Toast.makeText(getApplicationContext(), examene.toString(), Toast.LENGTH_LONG).show();
                notifyAdapter();
            }
        }
        if (requestCode == REQUEST_CODE_UPDATE_EXAM && resultCode == RESULT_OK && data != null) {
            Examen ex = (Examen) data.getParcelableExtra(AddExamActivity.ADD_EXAM_KEY);
            if (ex != null) {
                updateEntry(ex);
                notifyAdapter();
            }
        }
    }

    private void updateEntry(Examen ex) {
        examene.get(selectePositionIndex).setId(ex.getId());
        examene.get(selectePositionIndex).setDenumireMaterie(ex.getDenumireMaterie());
        examene.get(selectePositionIndex).setNumarStudenti(ex.getNumarStudenti());
        examene.get(selectePositionIndex).setSala(ex.getSala());
        examene.get(selectePositionIndex).setSupraveghetor(ex.getSupraveghetor());
    }

    public void notifyAdapter() {
        ArrayAdapter<Examen> adapter = (ArrayAdapter) lvExamene.getAdapter();
        adapter.notifyDataSetChanged();
    }
}
