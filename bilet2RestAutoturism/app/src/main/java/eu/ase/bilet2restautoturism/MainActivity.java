package eu.ase.bilet2restautoturism;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String SHARED_PREF_NAME = "sharedPrefName";
    private static final String AUTHOR_NAME = "authorName";
    public static final String ADD_LIST_KEY = "addList";

    private static final int REQUEST_CODE_ADD_AUTO = 200;
    private SharedPreferences preferences;

    private ArrayList<Autovehicul> autovehicule = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        putPref();
    }

    private void putPref() {
        if (getApplicationContext() != null) {
            preferences = getApplicationContext()
                    .getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(AUTHOR_NAME, getString(R.string.author));
            editor.apply();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.about) {
            String nume = preferences.getString(AUTHOR_NAME, "");
            Toast.makeText(getApplicationContext(), nume, Toast.LENGTH_LONG).show();
        }
        if (item.getItemId() == R.id.add_auto) {
            Intent intent = new Intent(MainActivity.this, AddAutoActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_AUTO);
        }
        if (item.getItemId() == R.id.list_auto) {
            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            if (autovehicule != null) {
                intent.putExtra(ADD_LIST_KEY, autovehicule);
                startActivity(intent);
            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_AUTO && resultCode == RESULT_OK && data != null) {
            Autovehicul auto = data.getParcelableExtra(AddAutoActivity.ADD_AUTO_KEY);
            if (auto != null) {
                Toast.makeText(getApplicationContext(), auto.toString(), Toast.LENGTH_LONG).show();
                autovehicule.add(auto);
            }

        }
    }

}
