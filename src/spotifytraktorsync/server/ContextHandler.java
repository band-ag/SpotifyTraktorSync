package spotifytraktorsync.server;

import java.io.IOException;
import java.io.OutputStream;

@FunctionalInterface
public interface ContextHandler {

    void handle(String row, OutputStream out) throws IOException;

}
