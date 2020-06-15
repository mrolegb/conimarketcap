package API;

import com.google.gson.JsonObject;
import cucumber.api.java.nl.Stel;
import groovy.util.MapEntry;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiTests {

    private final String key = "483f9d8d-390f-4b34-8e39-bbc14af3dd6d";
    private final String url = "https://pro-api.coinmarketcap.com";

    @Test
    public void conversionTest() {
        Response response = given().
                header("X-CMC_PRO_API_KEY", key).
                header("Accept", "application/json").
        when().
                get(url + "/v1/cryptocurrency/map");

        JSONObject json = new JSONObject(response.asString());
        JSONArray data = json.getJSONArray("data");

        String[] currencies = {"BTC", "USDT", "ETH"};
        Map<String, Integer> ids = new HashMap<String, Integer>();

        for(int i=0; i < data.length(); i++) {
            String current = data.getJSONObject(i).getString("symbol");
            if(Arrays.asList(currencies).contains(current)) {
                int id = data.getJSONObject(i).getInt("id");
                ids.put(current, id);
            }
        }

        for(Map.Entry<String, Integer> e : ids.entrySet()){
            response = given().
                    header("X-CMC_PRO_API_KEY", key).
                    header("Accept", "application/json").
                    param("amount", 1).
                    param("id", e.getValue()).
                    param("convert", "BOB").
            when().
                    get(url + "/v1/tools/price-conversion");
            json = new JSONObject(response.asString());
            JSONObject quote = json.getJSONObject("data").getJSONObject("quote");
            Assert.assertEquals("BOB", quote.keys().next());
            Assert.assertTrue(quote.getJSONObject("BOB").getDouble("price") > 0);
        }
    }

    @Test
    public void testEtherium() {
        Response response = given().
                header("X-CMC_PRO_API_KEY", key).
                header("Accept", "application/json").
                param("id", "1027").
        when().
                get(url + "/v1/cryptocurrency/info");

        JSONObject json = new JSONObject(response.asString());
        JSONObject data = json.getJSONObject("data").getJSONObject("1027");
        Assert.assertEquals("ETH", data.getString("symbol"));
        Assert.assertEquals("2015-08-07T00:00:00.000Z", data.getString("date_added"));
        Assert.assertEquals("https://s2.coinmarketcap.com/static/img/coins/64x64/1027.png",
                data.getString("logo"));
        Assert.assertEquals(JSONObject.NULL, data.get("platform"));
        Assert.assertTrue(data.getJSONArray("tags").toList().contains("mineable"));
        Assert.assertTrue(data.getJSONObject("urls").getJSONArray("technical_doc")
                .toList().contains("https://github.com/ethereum/wiki/wiki/White-Paper"));
    }

    @Test
    public void testMineable() {
        int mineable = 0;

        for(int i = 1; i <= 10; i++) {
            Response response = given().
                    header("X-CMC_PRO_API_KEY", key).
                    header("Accept", "application/json").
                    param("id", i).
                    when().
                    get(url + "/v1/cryptocurrency/info");

            JSONObject json = new JSONObject(response.asString());
            if (json.getJSONObject("data").getJSONObject(Integer.toString(i)).
                    getJSONArray("tags").toList().contains("mineable")) {
                mineable++;
            }
        }

        Assert.assertEquals(10, mineable);
    }
}
