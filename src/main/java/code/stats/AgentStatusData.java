package code.stats;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AgentStatusData implements Externalizable {

    private static final long serialVersionUID = -6928565296182180076L;

    private String username;
    private String status;
    private long timestamp;

    public AgentStatusData() {
    }

    public AgentStatusData(String username, String status, String dateTimestamp) throws ParseException {
        this.username = username;
        this.status = status;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(dateTimestamp);
        this.timestamp = date.getTime();
    }

    public AgentStatusData(String username, String status, long timestamp) {
        this.username = username;
        this.status = status;
        this.timestamp = timestamp;
    }


    @Override
    public String toString() {
        return "AgentStatusData [" + username + ", status=" + status + ", timestamp=" + timestamp + "]";
    }

    public String getUsername() {
        return username;
    }

    public String getStatus() {
        return status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.username);
        out.writeObject(this.status);
        out.writeLong(this.timestamp);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.username = (String)in.readObject();
        this.status = (String)in.readObject();
        this.timestamp = in.readLong();
    }
}