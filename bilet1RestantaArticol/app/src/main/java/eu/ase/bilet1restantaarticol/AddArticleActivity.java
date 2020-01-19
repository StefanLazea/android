package eu.ase.bilet1restantaarticol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddArticleActivity extends AppCompatActivity {
    public final static String ADD_ARTICLE_KEY = "addArticleKey";
    private EditText etTitlu;
    private EditText etPrimaPag;
    private EditText etUltimaPag;
    private EditText etNrAutori;
    private Button btnSend;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);
        initComponents();
        intent = getIntent();
    }

    private void initComponents() {
        etNrAutori = findViewById(R.id.etAutori);
        etPrimaPag = findViewById(R.id.etPrimaPagian);
        etUltimaPag = findViewById(R.id.etUltimaPagina);
        etTitlu = findViewById(R.id.etTitlu);

        btnSend = findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    Articol art = creareArticol();
                    intent.putExtra(ADD_ARTICLE_KEY, art);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

    }

    private Articol creareArticol() {
        String titlu = etTitlu.getText().toString();
        String prima = etPrimaPag.getText().toString();
        String ultima = etUltimaPag.getText().toString();
        int nrAutori = Integer.parseInt(etNrAutori.getText().toString());
        return new Articol(titlu, prima, ultima, nrAutori);
    }

    private boolean validate() {
        if (etTitlu.getText() == null || etTitlu.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.field_empty_error, Toast.LENGTH_LONG).show();
            return false;
        }
        if (etUltimaPag.getText() == null || etUltimaPag.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.field_empty_error, Toast.LENGTH_LONG).show();
            return false;
        }
        if (etPrimaPag.getText() == null || etPrimaPag.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.field_empty_error, Toast.LENGTH_LONG).show();
            return false;
        }
        if (etNrAutori.getText() == null || etNrAutori.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.field_empty_error, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
