package spotifytraktorsync.auth;

import com.google.gson.JsonObject;

import java.io.IOException;

public class Spotify {

    public static JsonObject me(){
        try {
            return SpotifyAuth.createSpotifyConnection("me", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonObject getUsersPlaylist(String userID) {
        try {
            return SpotifyAuth.createSpotifyConnection("users/" + userID + "/playlists", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonObject getPlaylistTracks(String playlistURL) {
        try {
            return SpotifyAuth.createSpotifyConnection(playlistURL, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
