package zcchat;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyHttpClient {
    public static void main(String[] args) throws IOException {
        // Configurando conexão
        String url = "http://localhost:8000/";
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/octet-stream");
        connection.setDoOutput(true);

        // Montando payload
        Payload payloadObj = new Payload("teste", "argumento 1");
        String requestBody = Payload.serializeHashMap(payloadObj);

        // Enviando para servidor
        // Escreve o corpo da requisição no fluxo de saída
        try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
            outputStream.writeBytes(requestBody);
            outputStream.flush();
        }

        // Obtém o código de resposta HTTP
        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        // Fecha a conexão
        connection.disconnect();

    }
}
