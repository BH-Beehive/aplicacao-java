package usecases;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Slack {
    private String url = "https://hooks.slack.com/services/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";
    public void sendMessage(JSONObject message) throws Exception {
        URL obj = new URL(this.url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setDoOutput(true);

        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(message.toString());

        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();

        System.out.println("Sending 'POST' request to URL: " + this.url);
        System.out.println("POST parameters: " + message.toString());
        System.out.println("Response Code: " + responseCode);

        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String inputLine;

        StringBuffer response = new StringBuffer();

        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }

        reader.close();
        System.out.println("Success.");
    }
}

