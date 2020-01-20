package eu.ase.bilet62018;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListOffersActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_UPDATE_HOME = 201;
    private ArrayList<HomeExchange> homeExchanges = new ArrayList<>();
    private Intent intent;
    private ListView lvOffers;
    private int selectedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_offers);
        intent = getIntent();
        initComponents();

        if (intent.hasExtra(MainActivity.SEND_OFFERS_LIST)) {
            homeExchanges.addAll(intent.<HomeExchange>getParcelableArrayListExtra(MainActivity.SEND_OFFERS_LIST));
            Toast.makeText(getApplicationContext(), homeExchanges.toString(), Toast.LENGTH_LONG).show();
            notifyAdapter();
        }

    }

    private void notifyAdapter() {
        OfferAdapter adapter = (OfferAdapter) lvOffers.getAdapter();
        adapter.notifyDataSetChanged();
    }

    private void initComponents() {
        lvOffers = findViewById(R.id.lvOffers);

        OfferAdapter adapter = new OfferAdapter(
                getApplicationContext(),
                R.layout.lv_row_custom,
                homeExchanges,
                getLayoutInflater()
        );

        lvOffers.setAdapter(adapter);

        lvOffers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedIndex = position;
                HomeExchange home = (HomeExchange)
                        homeExchanges.get(position);
                if (home != null) {
                    intent = new Intent(ListOffersActivity.this, AddHomeActivity.class);
                    intent.putExtra(AddHomeActivity.ADD_HOME_KEY, home);
                    startActivityForResult(intent, REQUEST_CODE_UPDATE_HOME);
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_UPDATE_HOME && resultCode == RESULT_OK && data != null) {
            HomeExchange home = (HomeExchange) data.getParcelableExtra(AddHomeActivity.ADD_HOME_KEY);
            if (home != null) {
                updateHomeInList(home);
                notifyAdapter();
            }
        }
    }

    private void updateHomeInList(HomeExchange home) {
        homeExchanges.get(selectedIndex).setAdresa(home.getAdresa());
        homeExchanges.get(selectedIndex).setData(home.getData());
        homeExchanges.get(selectedIndex).setNumarCamere(home.getNumarCamere());
        homeExchanges.get(selectedIndex).setSuprafata(home.getSuprafata());
        homeExchanges.get(selectedIndex).setTipLocuinta(home.getTipLocuinta());
    }
}
