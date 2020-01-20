package eu.ase.bilet1e;

import android.content.Context;
import android.os.AsyncTask;

public class DataPackageService {
    public static DataPackageDao dataPackageDao;

    public static class Insert extends AsyncTask<DataPackage, Void, DataPackage> {

        public Insert(Context context) {
            dataPackageDao = DatabaseManager.getInstance(context).getDataPackageDao();
        }

        @Override
        protected DataPackage doInBackground(DataPackage... dataPackages) {
            if (dataPackages != null && dataPackages.length != 1) {
                return null;
            }

            DataPackage pack = dataPackages[0];
            long id = dataPackageDao.insert(pack);
            if (id != -1) {
                return pack;
            }
            return null;
        }
    }
}
