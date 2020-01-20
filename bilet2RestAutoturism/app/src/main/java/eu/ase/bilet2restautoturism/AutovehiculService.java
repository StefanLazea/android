package eu.ase.bilet2restautoturism;

import android.content.Context;
import android.os.AsyncTask;

public class AutovehiculService {
    private static AutovehiculDao autovehiculDao;

    public static class Insert extends AsyncTask<Autovehicul, Void, Autovehicul> {
        public Insert(Context context) {
            autovehiculDao = DatabaseManager.getInstance(context).getAutovehiculDao();
        }

        @Override
        protected Autovehicul doInBackground(Autovehicul... autovehiculs) {
            if (autovehiculs != null && autovehiculs.length != 1) {
                return null;
            }

            Autovehicul auto = autovehiculs[0];
            long id = autovehiculDao.insert(auto);
            if (id != -1) {
                auto.setId(id);
                return auto;
            }
            return null;
        }
    }
}
