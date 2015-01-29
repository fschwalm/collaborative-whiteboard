package transmition.messages;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by drferreira on 16/12/14.
 */
public abstract class Message {
    private String date;
    private TypeMessage typeMessage;
    private JSONObject jsonObject;

    protected Message(TypeMessage typeMessage) {
        this.typeMessage = typeMessage;
        this.date = formatDate(new Date());
        this.jsonObject = new JSONObject();
    }

    public abstract void aditionalWrite();

    public void write(String key, Object value) {
        try {
            jsonObject.put(key, value);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject toJSon() {
        aditionalWrite();
        write("TYPE_MESSAGE", typeMessage);
        write("DATE", date);

        return jsonObject;
    }

    enum TypeMessage {
        STATUS_MESSAGE("STATUS_MESSAGE"),
        SERVER_MESSAGE("SERVER_MESSAGE"),
        USER_MESSAGE("USER_MESSAGE");

        private String typeMessage;

        TypeMessage(String typeMessage) {
            this.typeMessage = typeMessage;
        }
    }

    protected String formatDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
    }
}
