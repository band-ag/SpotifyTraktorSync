package spotifytraktorsync.auth;

import com.google.gson.JsonObject;
import org.apache.commons.io.IOUtils;
import spotifytraktorsync.json.JsonParser;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class SpotifyAuth {

    public static boolean createAccessToken(String key) throws IOException {

        HttpsURLConnection con = (HttpsURLConnection) new URL("https://accounts.spotify.com/api/token/").openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("Authorization", "Basic " + Credentials.AUTHORIZATION);
        con.setDoOutput(true);
        con.setDoInput(true);

        con.getOutputStream().write(("grant_type=authorization_code&code=" + key + "&redirect_uri=http://localhost/auth").getBytes(StandardCharsets.UTF_8));
        con.connect();

        InputStream in = con.getInputStream();
        byte[] bi = new byte[in.available()];
        in.read(bi);

        System.out.println(new String(bi));

        Credentials.ACCESS_TOKEN = JsonParser.gson.fromJson(new String(bi), JsonObject.class).get("access_token").getAsString();

        return con.getResponseCode() == 200;
    }

    public static JsonObject createSpotifyConnection(String url, byte[] toWrite) throws IOException{
        HttpsURLConnection con = (HttpsURLConnection) new URL("https://api.spotify.com/v1/" + url).openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer "+ Credentials.ACCESS_TOKEN);
        //con.setRequestProperty("Content-Length", toWrite != null ? ""+toWrite.length : "0");
        //con.setDoOutput(true);
        con.setDoInput(true);

        if(toWrite != null)
            con.getOutputStream().write(toWrite);
        con.connect();

        byte[] bi = IOUtils.toByteArray(con.getInputStream());

        if(con.getResponseCode() != 200) return null;

      //  System.out.println(new String(bi,StandardCharsets.UTF_8));

        return JsonParser.gson.fromJson(URLDecoder.decode(new String(bi,StandardCharsets.UTF_8)), JsonObject.class);
    }

}
