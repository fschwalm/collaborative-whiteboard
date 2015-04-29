package transmition.messages;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by drferreira on 16/12/14.
 */
public abstract class Message {
    private Date date;
    private String formatedDate;
    private TypeMessage typeMessage;
    private JSONObject jsonObject;

    protected Message(TypeMessage typeMessage) {
        this.typeMessage = typeMessage;
        this.date = new Date();
        this.formatedDate = formatDate(date);
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
        write("DATE", formatedDate);

        return jsonObject;
    }

    protected String formatDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
    }

    public TypeMessage getTypeMessage() {
        return typeMessage;
    }


    public Date getDate() {
        return date;
    }
}
