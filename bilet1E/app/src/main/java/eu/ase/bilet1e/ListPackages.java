package eu.ase.bilet1e;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collection;

public class ListPackages extends AppCompatActivity {
    private static final String PACKAGES_STATE = "pachete";
    private static final int REQUEST_CODE_UPDATE_PACK = 201;
    private Intent intent;
    private ListView listView;
    private ArrayList<DataPackage> listaPachete = new ArrayList<>();
    private int selectedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(PACKAGES_STATE)) {
            listaPachete.clear();
            listaPachete = savedInstanceState.getParcelableArrayList(PACKAGES_STATE);
        }
        setContentView(R.layout.activity_list_packages);
        intent = getIntent();
        initComponents();
        if (savedInstanceState != null && savedInstanceState.containsKey(PACKAGES_STATE)) {
            notifyAdapter();
        }
        if (intent.hasExtra(MainActivity.SEND_LIST)) {
            ArrayList<DataPackage> list = intent.getParcelableArrayListExtra(MainActivity.SEND_LIST);
            if (list != null) {
                listaPachete.addAll(list);
                notifyAdapter();
            }
        }
    }

    private void notifyAdapter() {
        ArrayAdapter<DataPackage> adapter = (ArrayAdapter<DataPackage>) listView.getAdapter();
        adapter.notifyDataSetChanged();

    }

    private void initComponents() {
        listView = findViewById(R.id.lvPackages);


        ArrayAdapter<DataPackage> adapter =
                new ArrayAdapter<DataPackage>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, listaPachete);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedIndex = position;
                DataPackage pack = listaPachete.get(position);
                if (pack != null) {
                    intent = new Intent(ListPackages.this, AddPackageActivity.class);
                    intent.putExtra(AddPackageActivity.ADD_PACKAGE_KEY, pack);
                    startActivityForResult(intent, REQUEST_CODE_UPDATE_PACK);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_UPDATE_PACK && resultCode == RESULT_OK && data != null) {
            DataPackage pack = (DataPackage) data.getParcelableExtra(AddPackageActivity.ADD_PACKAGE_KEY);
            if (pack != null) {
                updatePack(pack);
                notifyAdapter();
            }
        }
    }

    private void updatePack(DataPackage pack) {
        listaPachete.get(selectedIndex).setLatitude(pack.getLatitude());
        listaPachete.get(selectedIndex).setLongitude(pack.getLongitude());
        listaPachete.get(selectedIndex).setPackageType(pack.getPackageType());
        listaPachete.get(selectedIndex).setTimestamp(pack.getTimestamp());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(PACKAGES_STATE, listaPachete);
    }
}
