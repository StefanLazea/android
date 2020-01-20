package eu.ase.bilet62018;

import android.content.Context;
import android.os.AsyncTask;

public class HomeExchangeService {
    private static HomeExchangeDao homeExchangeDao;

    public static class Insert extends AsyncTask<HomeExchange, Void, HomeExchange> {

        public Insert(Context context) {
            homeExchangeDao = DatabaseManager.getInstance(context).getHomeExchangeDao();
        }

        @Override
        protected HomeExchange doInBackground(HomeExchange... homeExchanges) {
            if (homeExchanges != null && homeExchanges.length != 1) {
                return null;
            }

            HomeExchange home = homeExchanges[0];
            long id = homeExchangeDao.insert(home);
            if (id != -1) {
                home.setId(id);
                return home;
            }
            return null;
        }
    }
}
