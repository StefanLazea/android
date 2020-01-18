package eu.ase.bilet6curs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

            }
        });
    }
}
