package test;

import org.json.JSONObject;
import usecases.Slack;

public class CliTest {

    public static void main(String[] args) throws Exception {

        Slack slack = new Slack();
        JSONObject message = new JSONObject();

        message.put("text", "Eu sou o deus da guerra");

        slack.sendMessage(message);
    }
}
