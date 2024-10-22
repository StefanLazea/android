package eu.ase.bilet62018;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import eu.ase.bilet62018.json.HttpManager;
import eu.ase.bilet62018.json.HttpResponse;
import eu.ase.bilet62018.json.JsonParser;

public class MainActivity extends AppCompatActivity {

    private static final String URL = "https://api.myjson.com/bins/cdztu";
    private static final String SHARED_PREF_NAME = "sharedpref";
    public static final String SEND_OFFERS_LIST = "sendOffersList";
    public static final String DATE_FORMAT = "dd-MM-yyyy HH:mm";
    private static final int REQUEST_CODE_ADD_OFFER = 200;
    private SharedPreferences preferences;
    private Button btnDespre;
    private Button btnAddOffer;
    private Button btnListOffers;
    private Button btnGetData;
    private HttpResponse httpResponse;
    private ArrayList<HomeExchange> homes = new ArrayList<>();

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
        btnListOffers = findViewById(R.id.btnListaOferte);
        btnGetData = findViewById(R.id.btnPreluareDate);

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

        btnAddOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddHomeActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_OFFER);
            }
        });

        btnListOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (homes != null) {
                    Intent intent = new Intent(MainActivity.this, ListOffersActivity.class);
                    intent.putParcelableArrayListExtra(SEND_OFFERS_LIST, homes);
                    startActivity(intent);
                }
            }
        });

        btnGetData.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {
                new HttpManager(){
                    @Override
                    protected void onPostExecute(String s) {
                        httpResponse = JsonParser.parseJson(s);
                        if(httpResponse!=null){
                            Toast.makeText(getApplicationContext(), httpResponse.getHomes().toString(), Toast.LENGTH_LONG).show();
                            homes.addAll(httpResponse.getHomes());
                        }
                    }
                }.execute(URL);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_OFFER && resultCode == RESULT_OK && data != null) {
            HomeExchange home = (HomeExchange) data.getParcelableExtra(AddHomeActivity.ADD_HOME_KEY);
            if (home != null) {
                homes.add(home);
                insertIntoDb(home);
                Toast.makeText(getApplicationContext(), home.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void insertIntoDb(HomeExchange home) {
        new HomeExchangeService.Insert(getApplicationContext()) {
            @Override
            protected void onPostExecute(HomeExchange result) {
                if (result != null) {
                    Log.i("DB Insert>>>>>>>>", result.toString() + "was inserted");
                }
            }
        }.execute(home);
    }
}
