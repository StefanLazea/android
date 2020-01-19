package eu.ase.bilet1restantaarticol.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import eu.ase.bilet1restantaarticol.Articol;

public class JsonParser {
    public static HttpResponse parseJson(String json) {
        if (json == null) {
            return null;
        }

        try {
            JSONObject jsonObject = new JSONObject(json);
            List<Articol> articols = getArticoleListFromJsonArray(jsonObject.getJSONArray("articole"));
            return new HttpResponse(articols);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<Articol> getArticoleListFromJsonArray(JSONArray articole) throws JSONException {
        if (articole == null) {
            return null;
        }
        List<Articol> articols = new ArrayList<>();
        for (int i = 0; i < articole.length(); i++) {
            Articol a = getArticolFromJsonObject(articole.getJSONObject(i));
            if (a != null) {
                articols.add(a);
            }
        }
        return articols;
    }

    private static Articol getArticolFromJsonObject(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null) {
            return null;
        }

        String titlu = jsonObject.getString("titlu");
        String prima = jsonObject.getString("prima_pagina");
        String ultima = jsonObject.getString("ultima_pagina");
        int nrAut = jsonObject.getInt("numar_autori");
        return new Articol(titlu, prima, ultima, nrAut);

    }
}
