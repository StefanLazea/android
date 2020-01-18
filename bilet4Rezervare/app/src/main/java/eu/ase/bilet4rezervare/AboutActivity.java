package eu.ase.bilet4rezervare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AboutActivity extends AppCompatActivity {

    private TextView tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        tvDate = findViewById(R.id.about_date);

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US);
        Date data = new Date();
        String dataCurenta = dateFormat.format(data);
        tvDate.setText(dataCurenta);

    }
}
