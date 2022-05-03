package spotifytraktorsync.util;

import java.nio.charset.StandardCharsets;

public class HttpMessenger {

    public static byte[] messageDefault(String responseMessage, String message, String responseType){
        return ("HTTP/1.1 " + responseMessage + "\nContent-Type: " + responseType + "\n\n" + message).getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] moved(String location){
        return ("HTTP/1.1 301 Moved Permanently" + "\nLocation: " + location + "\n\n").getBytes(StandardCharsets.UTF_8);
    }

}
