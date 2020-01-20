package eu.ase.bilet62018;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

class OfferAdapter extends ArrayAdapter<HomeExchange> {
    private Context context;
    private int resource;
    private ArrayList<HomeExchange> homes;
    private LayoutInflater layoutInflater;

    public OfferAdapter(Context context,
                        int resource,
                        ArrayList<HomeExchange> homes,
                        LayoutInflater layoutInflater) {
        super(context, resource, homes);
        this.context = context;
        this.resource = resource;
        this.homes = homes;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(resource, parent, false);

        HomeExchange home = homes.get(position);
        if (home != null) {
            addAdresa(view, home.getAdresa());
            addData(view, home.getData());
            addTip(view, home.getTipLocuinta());
        }
        return view;
    }

    private void addTip(View view, String tipLocuinta) {
        TextView textView = view.findViewById(R.id.lv_tipLocuinta);
        if (tipLocuinta != null && !tipLocuinta.trim().isEmpty()) {
            textView.setText(tipLocuinta);
        }
    }

    private void addData(View view, Date data) {
        TextView textView = view.findViewById(R.id.lv_data);
        if (data != null) {
            textView.setText(new SimpleDateFormat(AddHomeActivity.DATE_FORMAT, Locale.US).format(data));
        } else {
            textView.setText("-");
        }
    }

    private void addAdresa(View view, String adresa) {
        TextView textView = view.findViewById(R.id.lv_adresa);
        if (adresa != null && !adresa.trim().isEmpty()) {
            textView.setText(adresa);
        }

    }
}
