package eu.ase.bilet4rezervare.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import eu.ase.bilet4rezervare.AddReservationActivity;
import eu.ase.bilet4rezervare.Rezervare;

public class JsonParser {

    public static HttpResponse parseJson(String json) {
        if (json == null) {
            return null;
        }
        try {
            JSONObject jsonObject = new JSONObject(json);

            List<Rezervare> rezervari = getItemListFromJson(jsonObject.getJSONArray("rezervari"));

            return new HttpResponse(rezervari);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<Rezervare> getItemListFromJson(JSONArray array) throws JSONException {
        if (array == null) {
            return null;
        }

        List<Rezervare> rezervari = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Rezervare rez = getRezervareFromJsonObject(array.getJSONObject(i));
            if (rez != null) {
                rezervari.add(rez);
            }
        }
        return rezervari;
    }

    private static Rezervare getRezervareFromJsonObject(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null) {
            return null;
        }

        long id = jsonObject.getLong("id_rezervare");
        String nume = jsonObject.getString("nume_client");
        String tipCamera = jsonObject.getString("tip_camera");
        int durata = jsonObject.getInt("durata");
        float suma = jsonObject.getInt("suma");
        Date cazare = null;
        try {
            cazare = new SimpleDateFormat(AddReservationActivity.DATE_FORMAT, Locale.US).parse(jsonObject.getString("data_cazare"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Rezervare(id, nume, tipCamera, durata, suma, cazare);

    }

}
