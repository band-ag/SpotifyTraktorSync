package spotifytraktorsync.auth;

import com.google.gson.JsonObject;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Credentials {

    public static String
        CLIENT_ID = "",
        CLIENT_SECRET = "";

    public static String AUTHORIZATION = Base64.getEncoder().encodeToString((CLIENT_ID + ":" + CLIENT_SECRET).getBytes(StandardCharsets.UTF_8));
    public static String ACCESS_TOKEN = "null";
    public static String USER_ID = "null";

}
