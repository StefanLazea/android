package eu.ase.bilet6curs.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import eu.ase.bilet6curs.Curs;

public class JsonParser {
    public static HttpResponse parseJson(String json) {
        if (json == null) {
            return null;
        }

        try {
            JSONObject jsonObject = new JSONObject(json);
            List<Curs> cursuri = getCursuriListFromJson(jsonObject.getJSONArray("cursuri"));
            return new HttpResponse(cursuri);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static List<Curs> getCursuriListFromJson(JSONArray array) throws JSONException {
        if (array == null) {
            return null;
        }

        List<Curs> results = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Curs curs = getCursFromJsonObject(array.getJSONObject(i));
            if (curs != null) {
                results.add(curs);
            }
        }
        return results;
    }

    private static Curs getCursFromJsonObject(JSONObject jsonObject) throws JSONException {
        long id = jsonObject.getLong("id");
        String denumire = jsonObject.getString("denumire");
        int nrPart = jsonObject.getInt("nr_participanti");
        String sala = jsonObject.getString("sala");
        String prof = jsonObject.getString("prof_titular");
        return new Curs(id, denumire, nrPart, sala, prof);
    }
}
