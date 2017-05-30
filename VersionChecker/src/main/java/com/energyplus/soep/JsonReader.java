package com.energyplus.soep;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class JsonReader
{
    private Gson g;
    public JsonReader()
    {
        g = new Gson();
    }
    public Map<String,SrcAstClass> readMap_String_SrcAstClass(String jsonInput)
    {
        return g.fromJson(jsonInput, new TypeToken<HashMap<String,SrcAstClass>>(){}.getType());
    }
}
