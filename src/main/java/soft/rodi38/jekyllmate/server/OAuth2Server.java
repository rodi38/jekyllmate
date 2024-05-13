package soft.rodi38.jekyllmate.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class OAuth2Server {
    public void startServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/callback", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                Map<String, String> params = parseQueryParameters(exchange.getRequestURI().getQuery());
                String code = params.get("code");

                String response = "Login efetuado com sucesso!";

                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });
        server.start();
    }

    private Map<String, String> parseQueryParameters(String query) {
        Map<String, String> parameters = new HashMap<>();

        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                parameters.put(entry[0], entry[1]);
            } else {
                parameters.put(entry[0], "");
            }
        }
        return parameters;
    }
}

