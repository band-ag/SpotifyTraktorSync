package spotifytraktorsync.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class AuthServer extends Thread{

    public HashMap<String, ContextHandler> handlers = new HashMap<String, ContextHandler>();
    private ServerSocket server;

    public AuthServer(){
        try {
            this.server=new ServerSocket(80);
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){

        while(true){
            try{
                Socket client = server.accept();

                InputStream in = client.getInputStream();
                OutputStream ou = client.getOutputStream();

                Thread.sleep(100);

                byte[] b = new byte[in.available()];
                in.read(b);

                String raw = new String(b, StandardCharsets.UTF_8);

                String re = raw.split("\n")[0].split(" ")[1];

                if(handlers.keySet().stream().anyMatch(re::startsWith)){
                    handlers.get(handlers.keySet().stream().filter(re::startsWith).findFirst().get()).handle(raw,ou);
                }else{
                    ou.write("404 Not Found".getBytes(StandardCharsets.UTF_8));
                }

                in.close();
                ou.close();
                client.close();
            }catch(Exception e){}
        }

    }

}
