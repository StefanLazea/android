package eu.ase.bilet62018;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String SHARED_PREF_NAME = "sharedpref";
    public static final String DATE_FORMAT = "dd-MM-yyyy HH:mm";
    private SharedPreferences preferences;
    private Button btnDespre;
    private Button btnAddOffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveDateAndTime();

        initComonents();

    }

    private void initComonents() {
        btnDespre = findViewById(R.id.btnAbout);
        btnAddOffer = findViewById(R.id.btnAddOffer);

        btnDespre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateAndTime = preferences.getString(getString(R.string.date_time_current)
                        , "");
                Toast.makeText(getApplicationContext(),
                        getString(R.string.author_name) + " "
                                + dateAndTime, Toast.LENGTH_LONG).show();

            }
        });
    }

    private void saveDateAndTime() {
        if (getApplicationContext() != null) {
            preferences = getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(getString(R.string.date_time_current), getDateAndTime());
            editor.apply();
        }
    }

    private String getDateAndTime() {
        Date data = new Date();
        return new SimpleDateFormat(DATE_FORMAT, Locale.US).format(data);
    }


}
