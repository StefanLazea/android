package eu.ase.bilet6curs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddCourseActivity extends AppCompatActivity {

    private EditText etId;
    private EditText etDenumire;
    private EditText etNrParticipanti;
    private EditText etSala;
    private EditText etProf;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        initComponents();
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
                if(validate()){

                }
            }
        });
    }

    private boolean validate() {
        if(etDenumire.getText().toString() == null || etDenumire.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), "Denumirea nu trebuie sa fie goala", Toast.LENGTH_LONG).show();
            return false;
        }
        if(etProf.getText().toString() == null || etProf.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), "Campul profesor nu trebuie sa fie goal", Toast.LENGTH_LONG).show();
            return false;
        }if(etDenumire.getText().toString() == null || etSala.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), "Denumirea nu trebuie sa fie goala", Toast.LENGTH_LONG).show();
            return false;
        }if(etDenumire.getText().toString() == null || etDenumire.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), "Denumirea nu trebuie sa fie goala", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
