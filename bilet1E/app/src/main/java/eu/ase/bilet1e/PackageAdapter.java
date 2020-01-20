package eu.ase.bilet1e;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

class PackageAdapter extends ArrayAdapter<DataPackage> {
    private Context context;
    private int resource;
    private ArrayList<DataPackage> packages;
    private LayoutInflater layoutInflater;

    public PackageAdapter(
            Context context,
            int resource,
            ArrayList<DataPackage> packages,
            LayoutInflater layoutInflater) {
        super(context, resource, packages);
        this.context = context;
        this.resource = resource;
        this.packages = packages;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(resource, parent, false);

        DataPackage dataPackage = packages.get(position);
        if (dataPackage != null) {
            addId(view, dataPackage);
            addName(view, dataPackage);
            addLatitude(view, dataPackage);
        }
        return view;
    }

    private void addLatitude(View view, DataPackage dataPackage) {
    }

    private void addName(View view, DataPackage dataPackage) {
    }

    private void addId(View view, DataPackage dataPackage) {
    }
}
