package granicni_prelaz.javaprojekat2023.json;

import granicni_prelaz.javaprojekat2023.exceptions.MapLoadingException;
import granicni_prelaz.javaprojekat2023.map.Field;
import granicni_prelaz.javaprojekat2023.map.PathOnMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



import java.io.FileReader;
import java.io.IOException;

public class PathJsonParser  {

    protected static JSONParser parser = new JSONParser();

    protected static Object getJsonObjectFromFile(String path) throws IOException, ParseException {
        Object json;

        try (FileReader fileReader = new FileReader(path)) {
            json = parser.parse(fileReader);
        }
        return json;
    }

    public static PathOnMap getPathFromJson(String path) throws MapLoadingException {
        PathOnMap pathOnMap = new PathOnMap();
        try {
            JSONArray jsonArray = (JSONArray) getJsonObjectFromFile(path);
            JSONObject jsonPath = (JSONObject) jsonArray.get(0);

            JSONArray pathJsonArray = (JSONArray) jsonPath.get("path");

            for (Object obj : pathJsonArray) {
                JSONObject pathFieldJson = (JSONObject) obj;
                int x = ((Long) pathFieldJson.get("row")).intValue();
                int y = ((Long) pathFieldJson.get("column")).intValue();

                pathOnMap.addPathField(new Field(x, y));
            }

        } catch (IOException | ParseException exception) {
            throw new MapLoadingException();
        }
        return pathOnMap;

    }
}
