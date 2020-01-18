package eu.ase.bilet6curs;

import android.content.Context;
import android.os.AsyncTask;

public class CursService {
    private static CursDao cursDao;

    public static class Insert extends AsyncTask<Curs, Void, Curs> {
        public Insert(Context context) {
            cursDao = DatabaseManager.getInstance(context).getCursDao();
        }

        @Override
        protected Curs doInBackground(Curs... curs) {
            if (curs != null && curs.length != 1) {
                return null;
            }
            Curs cursNou = curs[0];
            long id = cursDao.insert(cursNou);
            if (id != -1) {
                return cursNou;
            }
            return null;
        }
    }
}
