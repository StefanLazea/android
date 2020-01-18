package eu.ase.bilet6curs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddCourseActivity extends AppCompatActivity {
    public static final String ADD_CURS_KEY = "addCursKey";
    private EditText etId;
    private EditText etDenumire;
    private EditText etNrParticipanti;
    private EditText etSala;
    private EditText etProf;
    private Button btnSend;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        initComponents();
        intent = getIntent();
        if (intent.hasExtra(ADD_CURS_KEY)) {
            Curs curs = intent.getParcelableExtra(ADD_CURS_KEY);
            updateUI(curs);
        }
    }

    private void updateUI(Curs curs) {
        etId.setText(String.valueOf(curs.getIdCurs()));
        etDenumire.setText(curs.getDenumire());
        etNrParticipanti.setText(String.valueOf(curs.getNumarParticipanti()));
        etSala.setText(curs.getSala());
        etProf.setText(curs.getProfesorTitular());
    }


    private void initComponents() {
        etId = findViewById(R.id.add_id);
        etDenumire = findViewById(R.id.add_denumire);
        etNrParticipanti = findViewById(R.id.add_participanti);
        etProf = findViewById(R.id.add_prof);
        etSala = findViewById(R.id.add_sala);

        btnSend = findViewById(R.id.add_send);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    Curs curs = creareCurs();
                    Toast.makeText(getApplicationContext(), curs.toString(), Toast.LENGTH_LONG).show();
                    intent.putExtra(ADD_CURS_KEY, curs);
                    setResult(RESULT_OK, intent);
                    finish();

                }
            }
        });
    }

    private Curs creareCurs() {
        long id = Long.parseLong(etId.getText().toString());
        String denumire = etDenumire.getText().toString();
        String prof = etProf.getText().toString();
        int nr = Integer.parseInt(etNrParticipanti.getText().toString());
        String sala = etSala.getText().toString();

        return new Curs(id, denumire, nr, sala, prof);
    }

    private boolean validate() {
        if (etDenumire.getText() == null || etDenumire.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.add_denumire_error, Toast.LENGTH_LONG).show();
            return false;
        }
        if (etProf.getText() == null || etProf.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.add_prof_error, Toast.LENGTH_LONG).show();
            return false;
        }
        if (etSala.getText() == null || etSala.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.add_sala_error, Toast.LENGTH_LONG).show();
            return false;
        }
        if (etNrParticipanti.getText() == null || etNrParticipanti.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.add_nr_part_error, Toast.LENGTH_LONG).show();
            return false;
        }
        if (etId.getText() == null || etId.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.add_id_error, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
