package eu.ase.bilet1restantaarticol;

import android.content.Context;
import android.os.AsyncTask;

public class ArticolService {

    private static ArticolDao articolDao;

    public static class Insert extends AsyncTask<Articol, Void, Articol> {

        public Insert(Context context) {
            articolDao = DatabaseManager.getInstance(context).getArticolDao();
        }

        @Override
        protected Articol doInBackground(Articol... articols) {
            if (articols != null && articols.length != 1) {
                return null;
            }
            Articol articolNou = articols[0];
            long id = articolDao.insert(articolNou);
            if (id != 1) {
                articolNou.setId(id);
                return articolNou;
            }
            return null;
        }
    }
}
