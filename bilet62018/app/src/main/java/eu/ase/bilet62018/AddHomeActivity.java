package eu.ase.bilet62018;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddHomeActivity extends AppCompatActivity {

    public static final String ADD_HOME_KEY = "addHomeKey";
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    private EditText etAdresa;
    private EditText etSuprafata;
    private EditText etData;
    private EditText etNrCamere;
    private Spinner spinner;
    private Button send;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_home);
        intent = getIntent();
        initComponents();
    }

    private void initComponents() {
        etAdresa = findViewById(R.id.etAdresa);
        etData = findViewById(R.id.etData);
        etNrCamere = findViewById(R.id.etNumarCamere);
        etSuprafata = findViewById(R.id.etSuprafata);
        spinner = findViewById(R.id.spnLocuinta);
        send = findViewById(R.id.btnSend);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getApplicationContext(),
                R.array.spnLocuinta,
                R.layout.support_simple_spinner_dropdown_item
        );
        spinner.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    HomeExchange homeEx = createHome();
                    intent.putExtra(ADD_HOME_KEY, homeEx);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

    }

    private HomeExchange createHome() {
        String adresa = etAdresa.getText().toString();
        int nr = Integer.parseInt(etNrCamere.getText().toString());
        float suprafata = Float.parseFloat(etSuprafata.getText().toString());
        Date data = null;
        try {
            data = new SimpleDateFormat(AddHomeActivity.DATE_FORMAT, Locale.US)
                    .parse(etData.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String tip = spinner.getSelectedItem().toString();
        return new HomeExchange(adresa, nr, suprafata, data, tip);
    }

    private boolean validate() {
        if (etAdresa.getText() == null || etAdresa.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Campul nu trebuie sa fie gol", Toast.LENGTH_LONG).show();
            return false;
        }
        if (etSuprafata.getText() == null || etSuprafata.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Campul nu trebuie sa fie gol", Toast.LENGTH_LONG).show();
            return false;
        }
        if (etNrCamere.getText() == null || etNrCamere.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Campul nu trebuie sa fie gol", Toast.LENGTH_LONG).show();
            return false;
        }
        if (etData.getText() == null || etData.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Campul nu trebuie sa fie gol", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
