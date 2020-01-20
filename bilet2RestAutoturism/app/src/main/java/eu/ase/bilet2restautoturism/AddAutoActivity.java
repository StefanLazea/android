package eu.ase.bilet2restautoturism;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddAutoActivity extends AppCompatActivity {

    public static final String DATE_FORMAT = "dd-MM-yyyy HH:mm";
    public static final String ADD_AUTO_KEY = "addAutoKey";
    private EditText etNumar;
    private EditText etData;
    private EditText etIdLoc;
    private CheckBox cbAPlatit;
    private Button btnSend;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_auto);
        intent = getIntent();
        initComponents();
        if(intent.hasExtra(ADD_AUTO_KEY)){
            Autovehicul auto = intent.getParcelableExtra(ADD_AUTO_KEY);
            if(auto!=null){
                updateUI(auto);
            }
        }
    }

    private void updateUI(Autovehicul auto) {
        etNumar.setText(auto.getNumarAuto());
        etIdLoc.setText(String.valueOf(auto.getIdLocParcare()));
        if(auto.getPlataEfectuata()){
            cbAPlatit.setChecked(true);
        }else{
            cbAPlatit.setChecked(false);
        }
        String date = new SimpleDateFormat(DATE_FORMAT, Locale.US).format(auto.getDataInregistrare());
        etData.setText(date);
    }

    private void initComponents() {
        etNumar = findViewById(R.id.etNrAuto);
        etIdLoc = findViewById(R.id.etLoc);
        etData = findViewById(R.id.etData);
        cbAPlatit = findViewById(R.id.checkBox);
        btnSend = findViewById(R.id.button);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    Autovehicul auto = creareAuto();
                    intent.putExtra(ADD_AUTO_KEY, auto);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private Autovehicul creareAuto() {
        String numar = etNumar.getText().toString();
        Date date = null;
        try {
            date = new SimpleDateFormat(DATE_FORMAT, Locale.US).parse(etData.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int idLoc = Integer.parseInt(etIdLoc.getText().toString());
        boolean aPlatit = false;
        if (cbAPlatit.isChecked()) {
            aPlatit = true;
        }
        return new Autovehicul(numar, date, idLoc, aPlatit);
    }

    private boolean validate() {
        if (etNumar.getText() == null || etNumar.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.field_error, Toast.LENGTH_LONG).show();
            return false;
        }
        if (etData.getText() == null || etData.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.field_error, Toast.LENGTH_LONG).show();
            return false;
        }
        if (etIdLoc.getText() == null || etIdLoc.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.field_error, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
