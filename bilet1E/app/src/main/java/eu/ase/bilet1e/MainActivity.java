package eu.ase.bilet1e;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ADD_PACKAGE = 200;
    public static final String SEND_LIST = "sendList";
    private ArrayList<DataPackage> packages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_package) {
            Intent intent = new Intent(MainActivity.this, AddPackageActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_PACKAGE);
        }
        if (item.getItemId() == R.id.list_package) {
            sendToListActivity();
        }
        if (item.getItemId() == R.id.back_up_packages) {
            if (packages != null) {
                for (int i = 0; i < packages.size(); i++) {
                    insertIntoDb(packages.get(i));
                }
            }
        }
        return true;
    }

    @SuppressLint("StaticFieldLeak")
    private void insertIntoDb(DataPackage dataPackage) {
        new DataPackageService.Insert(getApplicationContext()) {
            @Override
            protected void onPostExecute(DataPackage result) {
                if (result != null) {
                    Log.i("DB INSERT >>>>>>>>>", result.toString());
                }
            }
        }.execute(dataPackage);
    }

    private void sendToListActivity() {
        Intent intent = new Intent(MainActivity.this, ListPackages.class);
        intent.putParcelableArrayListExtra(SEND_LIST, packages);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_PACKAGE && resultCode == RESULT_OK && data != null) {
            DataPackage pack = (DataPackage) data.getParcelableExtra(AddPackageActivity.ADD_PACKAGE_KEY);
            if (pack != null) {
                packages.add(pack);
                Toast.makeText(getApplicationContext(), pack.toString(), Toast.LENGTH_LONG).show();
                sendToListActivity();
            }
        }
    }
}
