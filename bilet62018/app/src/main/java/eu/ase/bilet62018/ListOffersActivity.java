package eu.ase.bilet62018;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListOffersActivity extends AppCompatActivity {
    private ArrayList<HomeExchange> homeExchanges = new ArrayList<>();
    private Intent intent;
    private ListView lvOffers;

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
    }
}
