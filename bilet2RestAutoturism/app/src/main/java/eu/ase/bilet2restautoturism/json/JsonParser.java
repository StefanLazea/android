package eu.ase.bilet2restautoturism.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import eu.ase.bilet2restautoturism.AddAutoActivity;
import eu.ase.bilet2restautoturism.Autovehicul;

public class JsonParser {
    public static HttpResponse jsonParse(String json) {
        if (json == null) {
            return null;
        }

        try {
            JSONObject jsonObject = new JSONObject(json);
            List<Autovehicul> autos = getAutoListFromJsonArray(jsonObject.getJSONArray("autovehicule"));
            return new HttpResponse(autos);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<Autovehicul> getAutoListFromJsonArray(JSONArray array) throws JSONException {
        if (array == null) {
            return null;
        }
        List<Autovehicul> autovehicule = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Autovehicul auto = getAutoObjectFromJson(array.getJSONObject(i));
            if (auto != null) {
                autovehicule.add(auto);
            }
        }
        return autovehicule;
    }

    private static Autovehicul getAutoObjectFromJson(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null) {
            return null;
        }

        String numar = jsonObject.getString("numar_inamtriculare");
        String dateStr = jsonObject.getString("data_inregistrare");
        Date data = null;
        try {
            data = new SimpleDateFormat(AddAutoActivity.DATE_FORMAT, Locale.US).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int nrLoc = jsonObject.getInt("id_loc_parcare");
        boolean plataEfectuata = jsonObject.getBoolean("plata_efectuata");

        return new Autovehicul(numar, data, nrLoc, plataEfectuata);
    }
}
