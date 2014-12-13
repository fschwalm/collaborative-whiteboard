package br.org.tutty.collaborative_whiteboard.transmition.services;

import br.org.tutty.collaborative_whiteboard.exceptions.DataNotFoundException;
import br.org.tutty.collaborative_whiteboard.transmition.exceptions.MessageMountException;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by drferreira on 12/12/14.
 */
public class MessageProblemsBuilder {
    public static JSONObject getMessage(Exception exception) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("date", new Date());
            jsonObject.put("user", "");

            if (exception instanceof DataNotFoundException) {
                jsonObject.put("messageValue", "USUARIO DESLOGADO");

            } else if (exception instanceof MessageMountException) {
                jsonObject.put("messageValue", "ERRO AO MONTAR MENSAGEM");
            } else {
                jsonObject.put("messageValue", "PROBLEMA AO ENVIAR MENSAGEM");
            }

            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
            return jsonObject;
        }
    }
}
