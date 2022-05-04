package spotifytraktorsync.main;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import spotifytraktorsync.auth.Credentials;
import spotifytraktorsync.auth.Spotify;
import spotifytraktorsync.auth.SpotifyAuth;
import spotifytraktorsync.json.JsonParser;
import spotifytraktorsync.server.AuthServer;
import spotifytraktorsync.util.HttpMessenger;

public class SpotifyTraktorSync {

    public static void main(String[] args) {
//        AuthServer server = new AuthServer();
//
//        server.handlers.put("/", (row, out) -> {
//            out.write(HttpMessenger.moved("https://accounts.spotify.com/de/authorize?response_type=code&client_id=7958fce2169348c9bb75ede05e1f12c2&scope=playlist-read-private&redirect_uri=http://localhost/auth"));
//        });
//
//        server.handlers.put("/auth", (row, out) -> {
//            String key = row.split("\n")[0].split(" ")[1];
//            key = key.split("\\?")[1].split("=")[1];
//
//            if(SpotifyAuth.createAccessToken(key)){
//                out.write(HttpMessenger.messageDefault("200 OK", "Zugriff wurde erlaubt.", "text/html"));
//            }else{
//                out.write(HttpMessenger.messageDefault("403 Forbidden", "Ein Fehler ist aufgetreten!", "text/html"));
//            }
//        });

        Credentials.ACCESS_TOKEN = "";
        Credentials.USER_ID = Spotify.me().get("id").getAsString();
        JsonArray playlists = Spotify.getUsersPlaylist(Credentials.USER_ID).get("items").getAsJsonArray();

        String tracksURL = playlists.get(0).getAsJsonObject().get("tracks").getAsJsonObject().get("href").getAsString();

        tracksURL = tracksURL.substring(27, tracksURL.length());

        JsonArray tracks = Spotify.getPlaylistTracks(tracksURL).get("items").getAsJsonArray();

        tracks.forEach(track -> {
            if(!track.isJsonObject()) return;
            JsonObject obj = track.getAsJsonObject().get("track").getAsJsonObject();

            String name = obj.get("name").getAsString();
            String artist = obj.get("album").getAsJsonObject().get("artists").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();
            String href = obj.get("external_urls").getAsJsonObject().get("spotify").getAsString();

            System.out.println(name + " " + artist + " " + href);

        });


    }

}
