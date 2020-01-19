package eu.ase.bilet4rezervare;

import android.content.Context;
import android.os.AsyncTask;

public class RezervareService {
    private static RezervareDao rezervareDao;

    public static class Insert extends AsyncTask<Rezervare, Void, Rezervare> {

        public Insert(Context context) {
            rezervareDao = DatabaseManager.getInstance(context).getRezervariDao();
        }

        @Override
        protected Rezervare doInBackground(Rezervare... rezervares) {
            if (rezervares != null && rezervares.length != 1) {
                return null;
            }

            Rezervare rezNoua = rezervares[0];
            long id = rezervareDao.insert(rezNoua);
            if (id != -1) {
                return rezNoua;
            }
            return null;
        }
    }
}
