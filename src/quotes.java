import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class quotes {

    public static void main(String args[]) throws ParseException {
        pullRandomQuote();
    }

    public static void pullRandomQuote() {
        String totalJson = "";

        try {
            URL url = new URL("https://zenquotes.io/api/random");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String output;
            while ((output = br.readLine()) != null) {
                totalJson += output;
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Parse JSON response
        JSONParser parser = new JSONParser();
        try {
            JSONArray jsonArray = (JSONArray) parser.parse(totalJson);
            JSONObject quoteObj = (JSONObject) jsonArray.get(0);
            String quote = (String) quoteObj.get("q");
            String author = (String) quoteObj.get("a");

            System.out.println("Random Quote:");
            System.out.println("Quote: " + quote);
            System.out.println("Author: " + author);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
