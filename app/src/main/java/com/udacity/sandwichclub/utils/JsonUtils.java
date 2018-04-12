package com.udacity.sandwichclub.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    // here  referred https://jsonformatter.curiousconcept.com/ and
    // https://stackoverflow.com/questions/9605913/how-to-parse-json-in-android
    // to understand the Json format apply

    private static final String LOG = JsonUtils.class.getSimpleName();
    private static final String SAND_NAME = "name";
    private static final String SAND_MAIN_NAME= "mainName";
    private static final String SAND_ALSO= "alsoKnownAs";
    private static final String SAND_PLACE_ORIGIN= "placeOfOrigin";
    private static final String SAND_DESCRIPTION= "description";
    private static final String SAND_IMAGE= "image";
    private static final String SAND_INGREDIENTS= "ingredients";


    @NonNull
    public static Sandwich parseSandwichJson(String json) {
        JSONObject jsonObject;
        String mainName = null;
        String placeOfOrigin = null;
        String description = null;
        String image = null;

        List<String> ingredients = new ArrayList<>();
        List<String> alsoKnownAs = new ArrayList<>();

        try {
            jsonObject = new JSONObject(json);
            JSONObject sandwichJson = new JSONObject(json);
            JSONObject sandwichName = sandwichJson.getJSONObject("name");
            JSONObject jsonObjectName = jsonObject.getJSONObject(SAND_NAME);
            mainName = (String) jsonObjectName.opt(SAND_MAIN_NAME);
            placeOfOrigin = (String) jsonObject.opt(SAND_PLACE_ORIGIN);
            description = (String) jsonObject.opt(SAND_DESCRIPTION);
            image = (String) jsonObject.opt(SAND_IMAGE);

            alsoKnownAs = jsonArrayList(jsonObjectName.getJSONArray(SAND_ALSO));
            JSONArray alsoKnownAsArray = sandwichName.getJSONArray("alsoKnownAs");

            for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                alsoKnownAs.add(alsoKnownAsArray.getString(i));
                }
            ingredients = jsonArrayList(jsonObject.getJSONArray(SAND_INGREDIENTS));
            JSONArray ingredientsArray = sandwichJson.getJSONArray("ingredients");
            for (int i = 0; i < ingredientsArray.length(); i++) {
                ingredients.add(ingredientsArray.getString(i));
            }



        } catch (JSONException e) {
            Log.e(LOG, "Problems in parsing", e);
        }
        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }

    private static List<String> jsonArrayList(JSONArray jsonArray){
        List<String> list = new ArrayList<>(0);
        if (jsonArray!=null){
            for (int i=0; i<jsonArray.length();i++){
                try {
                    list.add(jsonArray.getString(i));
                } catch (JSONException e) {
                    Log.e(LOG, "Problems with array list", e);
                }
            }
        }
        return list;
    }
}
