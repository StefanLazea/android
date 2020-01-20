package eu.ase.bilet1e;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddPackageActivity extends AppCompatActivity {

    public static final String ADD_PACKAGE_KEY = "addPackageKey";
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    private Intent intent;
    private EditText etId;
    private EditText etLatitude;
    private EditText etLongitude;
    private EditText etTimestamp;
    private Spinner spinner;
    private Button btnSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_package);
        intent = getIntent();
        initComponents();
        if (intent.hasExtra(ADD_PACKAGE_KEY)) {
            DataPackage pack = (DataPackage) intent.getParcelableExtra(ADD_PACKAGE_KEY);
            updateUI(pack);
        }
    }

    private void updateUI(DataPackage pack) {
        etId.setText(String.valueOf(pack.getPackageId()));
        etId.setEnabled(false);
        etLatitude.setText(String.valueOf(pack.getLatitude()));
        etLongitude.setText(String.valueOf(pack.getLongitude()));
        etTimestamp.setText(new SimpleDateFormat(AddPackageActivity.DATE_FORMAT, Locale.US).format(pack.getTimestamp()));
        if(pack.getPackageType()!=null){
            addPackageType(pack);
        }
    }

    private void addPackageType(DataPackage pack) {
        SpinnerAdapter adapter = spinner.getAdapter();
        for(int i=0;i<adapter.getCount();i++){
            if(adapter.getItem(i).toString().equals(pack.getPackageType())){
                spinner.setSelection(i);
                break;
            }
        }
    }

    private void initComponents() {
        etId = findViewById(R.id.etPackId);
        etLatitude = findViewById(R.id.etLatitude);
        etLongitude = findViewById(R.id.etLongitude);
        etTimestamp = findViewById(R.id.etTimestamp);
        spinner = findViewById(R.id.spinnerPackType);
        btnSend = findViewById(R.id.btnSend);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getApplicationContext(),
                R.array.spinnerPackageType,
                R.layout.support_simple_spinner_dropdown_item
        );
        spinner.setAdapter(adapter);


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    DataPackage pack = createPackage();
                    intent.putExtra(ADD_PACKAGE_KEY, pack);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

    }

    private DataPackage createPackage() {
        long id = Long.parseLong(etId.getText().toString());
        String packType = spinner.getSelectedItem().toString();
        double latitude = Double.parseDouble(etLatitude.getText().toString());
        double longitude = Double.parseDouble(etLongitude.getText().toString());
        Date date = null;
        try {
            date = new SimpleDateFormat(DATE_FORMAT, Locale.US).parse(etTimestamp.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new DataPackage(id, packType, latitude, longitude, date);
    }

    private boolean validate() {

        if (etId.getText() == null || etId.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), getString(R.string.error_field), Toast.LENGTH_LONG).show();
            return false;
        }
        if (etTimestamp.getText() == null || etTimestamp.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), getString(R.string.error_field), Toast.LENGTH_LONG).show();
            return false;
        }
        if (etLongitude.getText() == null || etLongitude.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), getString(R.string.error_field), Toast.LENGTH_LONG).show();
            return false;
        }
        if (etLatitude.getText() == null || etLatitude.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), getString(R.string.error_field), Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}
