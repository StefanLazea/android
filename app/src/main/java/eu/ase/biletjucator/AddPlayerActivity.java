package eu.ase.biletjucator;

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

public class AddPlayerActivity extends AppCompatActivity {

    public static final String DATE_FORMAT="dd-MM-yyyy";
    public static final String ADD_PLAYER_KEY = "addPLayerKey";
    private EditText nume;
    private EditText numar;
    private Spinner pozitie;
    private EditText birthday;
    private Button send;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);
        intent = getIntent();
        initComponents();
    }

    private void initComponents() {
        pozitie = findViewById(R.id.add_spinner);
        nume = findViewById(R.id.add_player_name);
        numar = findViewById(R.id.add_number);
        birthday = findViewById(R.id.add_birthday);
        send = findViewById(R.id.add_send);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(getApplicationContext(),
                        R.array.spinner_items, R.layout.support_simple_spinner_dropdown_item);
        pozitie.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    Toast.makeText(getApplicationContext(), "Jucator adaugat", Toast.LENGTH_SHORT).show();
                    Jucator jucatorNou = createPlayer();
                    intent.putExtra(ADD_PLAYER_KEY, jucatorNou);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private boolean validate() {
        Toast.makeText(getApplicationContext(), "wtf", Toast.LENGTH_SHORT).show();

        if (nume.getText() == null || nume.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.add_name_error, Toast.LENGTH_SHORT).show();
            return false;
        }

        if(numar.getText() == null || numar.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), R.string.add_number_error, Toast.LENGTH_LONG).show();
            return false;
        }

        if(birthday.getText() == null || birthday.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), R.string.add_birtday_error, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private Jucator createPlayer(){

        String numeJucator = nume.getText().toString();
        String pozitieJucator = pozitie.getSelectedItem().toString();
        int numarJucator = Integer.parseInt(numar.getText().toString());

        Date date = null;
        try {
            date = new SimpleDateFormat(DATE_FORMAT, Locale.US).parse(birthday.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return new Jucator(numeJucator, numarJucator, date, pozitieJucator );

    }
}
