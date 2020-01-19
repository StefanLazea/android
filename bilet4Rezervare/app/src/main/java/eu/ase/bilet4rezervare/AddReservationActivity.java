package eu.ase.bilet4rezervare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddReservationActivity extends AppCompatActivity {
    public final static String ADD_RES_KEY = "addResKey";
    public final static String DATE_FORMAT = "dd-MM-yyyy";
    private EditText etId;
    private EditText etNume;
    private Spinner spinnerTipCamera;
    private EditText etDurata;
    private EditText etSuma;
    private EditText etdataCazare;
    private Button btnSend;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reservation);
        initComponents();
        intent = getIntent();
        if (intent.hasExtra(ADD_RES_KEY)) {
            Rezervare rez = intent.getParcelableExtra(ADD_RES_KEY);
            updateUI(rez);
        }
    }

    private void updateUI(Rezervare rez) {
        etId.setText(String.valueOf(rez.getIdRezervare()));
        etNume.setText(rez.getNumeClient());
        if (rez.getTipCamera() != null) {
            setTipCamera(rez);
        }
        etDurata.setText(String.valueOf(rez.getDurataSejur()));
        etSuma.setText(String.valueOf(rez.getSumaPlata()));
        if (rez.getDataCazare() != null) {
            String dataCazare = new SimpleDateFormat(DATE_FORMAT, Locale.US).format(rez.getDataCazare());
            etdataCazare.setText(dataCazare);
        }
    }

    private void setTipCamera(Rezervare rez) {
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spinnerTipCamera.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).equals(rez.getTipCamera())) {
                spinnerTipCamera.setSelection(i);
                break;
            }
        }
    }

    private void initComponents() {
        etId = findViewById(R.id.add_id);
        etNume = findViewById(R.id.add_name);
        spinnerTipCamera = findViewById(R.id.spinner);
        etDurata = findViewById(R.id.add_durata);
        etSuma = findViewById(R.id.add_suma);
        etdataCazare = findViewById(R.id.add_data_cazare);
        btnSend = findViewById(R.id.add_btn);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.spinner_camera,
                R.layout.support_simple_spinner_dropdown_item);
        spinnerTipCamera.setAdapter(adapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    Rezervare rez = createReservation();
                    intent.putExtra(ADD_RES_KEY, rez);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private Rezervare createReservation() {
        long id = Long.parseLong(etId.getText().toString());
        String nume = etNume.getText().toString();
        String tipCamera = spinnerTipCamera.getSelectedItem().toString();
        int durata = Integer.parseInt(etDurata.getText().toString());
        float suma = Float.parseFloat(etSuma.getText().toString());
        Date birthday = null;

        try {
            birthday = new SimpleDateFormat(AddReservationActivity.DATE_FORMAT,
                    Locale.US).parse(etdataCazare.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Rezervare(id, nume, tipCamera, durata, suma, birthday);
    }

    private boolean validate() {
        if (etId.getText() == null || etId.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.add_id_error, Toast.LENGTH_LONG).show();
            return false;
        }
        if (etNume.getText() == null || etNume.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.add_name_error, Toast.LENGTH_LONG).show();
            return false;
        }
        if (etSuma.getText() == null || etSuma.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.add_error, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}
