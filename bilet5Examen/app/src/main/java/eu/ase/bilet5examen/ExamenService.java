package eu.ase.bilet5examen;

import android.content.Context;
import android.os.AsyncTask;

public class ExamenService {
    private static ExamenDao examenDao;

    public static class Insert extends AsyncTask<Examen, Void, Examen> {

        public Insert(Context context) {
            examenDao = DatabaseManager
                    .getInstance(context)
                    .getExamenDao();
        }

        @Override
        protected Examen doInBackground(Examen... examen) {
            if (examen != null && examen.length != 1) {
                return null;
            }

            Examen ex = examen[0];

            long id = examenDao.insert(ex);
            if (id != -1) {
                return ex;
            }

            return null;
        }
    }
}
