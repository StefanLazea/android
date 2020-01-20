package eu.ase.bilet62018.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import eu.ase.bilet62018.AddHomeActivity;
import eu.ase.bilet62018.HomeExchange;

public class JsonParser {
    public static HttpResponse parseJson(String json) {
        if (json == null) {
            return null;

        }
        try {
            JSONObject jsonObject = new JSONObject(json);
            ArrayList<HomeExchange> homes = getListFromJsonObject(jsonObject.getJSONArray("homes"));

            return new HttpResponse(homes);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }

    private static ArrayList<HomeExchange> getListFromJsonObject(JSONArray array) throws JSONException {
        if (array == null) {
            return null;
        }

        ArrayList<HomeExchange> homesExchange = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            HomeExchange home = getHomeFromJsonObject(array.getJSONObject(i));
            if (home != null) {
                homesExchange.add(home);
            }
        }
        return homesExchange;
    }

    private static HomeExchange getHomeFromJsonObject(JSONObject jsonObject) throws JSONException {
        String adresa = jsonObject.getString("adresa");
        int nrCam = jsonObject.getInt("numar_camere");
        float suprafata = jsonObject.getInt("suprafata");
        String dateStr = jsonObject.getString("data");
        Date date = null;
        try {
            date = new SimpleDateFormat(AddHomeActivity.DATE_FORMAT, Locale.US).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String tipLocuinta = jsonObject.getString("tip_locuinta");

        return new HomeExchange(adresa, nrCam, suprafata, date, tipLocuinta);
    }
}
