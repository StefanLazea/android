package eu.ase.biletjucator;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class JucatorService {
    private static JucatorDao jucatorDao;

    public static class Insert extends AsyncTask<Jucator, Void, Jucator> {
        public Insert(Context context) {
            jucatorDao = DatabaseManager.getInstance(context).getJucatorDao();
        }

        @Override
        protected Jucator doInBackground(Jucator... jucators) {
            if (jucators != null && jucators.length != 1) {
                return null;
            }
            Jucator jucator = jucators[0];

            long id = jucatorDao.insert(jucator);
            if (id != -1) {
                jucator.setId(id);
                return jucator;
            }
            return null;
        }
    }

    public static class GetAll extends AsyncTask<Void, Void, List<Jucator>> {
        public GetAll(Context context) {
            jucatorDao = DatabaseManager.getInstance(context).getJucatorDao();
        }

        @Override
        protected List<Jucator> doInBackground(Void... voids) {
            return jucatorDao.getAll();
        }
    }
}
