package eu.ase.bilet5examen.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import eu.ase.bilet5examen.Examen;

public class JsonParser {
    public static HttpResponse parseJson(String json) {
        if (json == null) {
            return null;
        }

        try {
            JSONObject jsonObject = new JSONObject(json);
            List<Examen> examene = getExamListFromJsonObject(jsonObject.getJSONArray("examene"));
            return new HttpResponse(examene);
        } catch (JSONException e) {
            e.printStackTrace();

        }

        return null;
    }

    private static List<Examen> getExamListFromJsonObject(JSONArray examene) throws JSONException {
        if (examene == null) {
            return null;
        }

        List<Examen> ex = new ArrayList<>();
        for (int i = 0; i < examene.length(); i++) {
            Examen examen = getExamFromJsonObject(examene.getJSONObject(i));
            if (examen != null) {
                ex.add(examen);
            }
        }
        return ex;
    }

    private static Examen getExamFromJsonObject(JSONObject jsonObject) throws JSONException {
//        if (jsonObject == null) {
//            return null;
//        }

        long id = jsonObject.getLong("id");
        String denumire = jsonObject.getString("denumire_materie");
        int nrStud = jsonObject.getInt("numar_studenti");
        String sala = jsonObject.getString("sala");
        String supraveghetor = jsonObject.getString("supraveghetor");

        return new Examen(id, denumire, nrStud, sala, supraveghetor);
    }
}
