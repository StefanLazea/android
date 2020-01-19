package eu.ase.bilet5examen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.EmptyStackException;

public class AddExamActivity extends AppCompatActivity {
    public static final String ADD_EXAM_KEY = "addExamKey";
    private EditText etId;
    private EditText etMaterie;
    private EditText etSala;
    private EditText etNrStud;
    private EditText etSupraveghetor;
    private Button btnSend;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exam);
        initComponents();
        intent = getIntent();
        if (intent.hasExtra(ADD_EXAM_KEY)) {
            Examen ex = intent.getParcelableExtra(ADD_EXAM_KEY);
            updateUI(ex);
        }
    }

    private void updateUI(Examen ex) {
        etId.setText(String.valueOf(ex.getId()));
        etNrStud.setText(String.valueOf(ex.getNumarStudenti()));
        etSupraveghetor.setText(ex.getSupraveghetor());
        etSala.setText(ex.getSala());
        etMaterie.setText(ex.getDenumireMaterie());
    }

    private void initComponents() {
        etId = findViewById(R.id.etId);
        etMaterie = findViewById(R.id.etDenumire);
        etNrStud = findViewById(R.id.etNrStud);
        etSala = findViewById(R.id.etSala);
        etSupraveghetor = findViewById(R.id.etSupraveghetor);
        btnSend = findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    Examen ex = createExam();
                    Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT).show();
                    intent.putExtra(ADD_EXAM_KEY, ex);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private Examen createExam() {
        long id = Long.parseLong(etId.getText().toString());
        String denumire = etMaterie.getText().toString();
        String sala = etSala.getText().toString();
        int nrStud = Integer.parseInt(etNrStud.getText().toString());
        String prof = etSupraveghetor.getText().toString();

        return new Examen(id, denumire, nrStud, sala, prof);
    }

    private boolean validate() {
        if (etId.getText() == null || etId.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.field_error, Toast.LENGTH_LONG).show();
            return false;
        }
        if (etMaterie.getText() == null || etMaterie.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.field_error, Toast.LENGTH_LONG).show();
            return false;
        }
        if (etNrStud.getText() == null || etNrStud.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.field_error, Toast.LENGTH_LONG).show();
            return false;
        }
        if (etSala.getText() == null || etSala.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.field_error, Toast.LENGTH_LONG).show();
            return false;
        }
        if (etSupraveghetor.getText() == null || etSupraveghetor.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.field_error, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
