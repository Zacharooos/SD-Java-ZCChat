package zcchat;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyHttpClient {
    public static void main(String[] args) throws IOException {
        // Configurando conexão
        String url = "http://localhost:8000/";
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "text/plain; charset=ISO-8859-1");
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

        // Obtém o body da resposta do servidor
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(),
        "ISO-8859-1"))) {
            String responseString = Payload.decodeBufferFromSocket(br);
            Payload responseObj = Payload.deserializeHashMap(responseString);
            br.close();
            
            // Imprimir o corpo da resposta
            System.err.println(responseObj.get("response"));

        }


        // Fecha a conexão
        connection.disconnect();

    }
}
