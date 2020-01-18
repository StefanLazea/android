package eu.ase.biletjucator.json;

import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import eu.ase.biletjucator.AddPlayerActivity;
import eu.ase.biletjucator.Jucator;

public class JsonParser {
    public static HttpResponse parseJson(String json) {
        if (json == null) {
            return null;
        }

        try {
            JSONObject jsonObject = new JSONObject(json);
            List<Item> goalkeepers = getItemListFromJson(jsonObject.getJSONArray("goalkeeper"));
            List<Item> inters = getItemListFromJson(jsonObject.getJSONArray("goalkeeper"));
            List<Item> wingers = getItemListFromJson(jsonObject.getJSONArray("goalkeeper"));
            List<Item> centers = getItemListFromJson(jsonObject.getJSONArray("goalkeeper"));
            return new HttpResponse(goalkeepers, inters, wingers, centers);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<Item> getItemListFromJson(JSONArray array) throws JSONException {
        if (array == null) {
            return null;
        }

        List<Item> results = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Item item = getItemFromJsonObject(array.getJSONObject(i));
            if (item != null) {
                results.add(item);
            }
        }
        return results;

    }

    private static Item getItemFromJsonObject(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null) {
            return null;
        }
        String team = jsonObject.getString("team");
        Jucator jucator = getJucatorFromJsonObject(jsonObject.getJSONObject("jucator"));
        return new Item(team, jucator);
    }


    private static Jucator getJucatorFromJsonObject(JSONObject jucator) throws JSONException {
        if (jucator == null) {
            return null;
        }

        String name = jucator.getString("nume");
        Date dataNastere = null;

        try {
            dataNastere = new SimpleDateFormat(AddPlayerActivity.DATE_FORMAT, Locale.US)
                    .parse(jucator.getString("data-nastere"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int numar = jucator.getInt("numar");
        String pozitie = jucator.getString("pozite");
        return new Jucator(name, numar, dataNastere, pozitie);

    }


}
