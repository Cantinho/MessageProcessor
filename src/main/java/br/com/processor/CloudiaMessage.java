package br.com.processor;

/**
 * Created by jordao on 12/12/16.
 */
public class CloudiaMessage implements IMessage {

    public static final String CONNECT = "43";
    public static final String STATUS = "58";
    public static final String LOCK = "4E";
    public static final String UNLOCK = "4F";
    public static final String OK = "01";
    public static final String ERROR = "02";
    public static final String CONNECT_OK = "01";
    public static final String CONNECT_ERROR = "02";

    private String header;
    private String packetSize;
    private String sequence;
    private String command;
    private String data;
    private String checksum;

    public CloudiaMessage() {}

    public CloudiaMessage(String header, String sequence, String command, String data, String checksum) {
        this(header, calculatePacketSize(data) , sequence, command, data, checksum);
    }

    public CloudiaMessage(String header, String packetSize, String sequence, String command, String data, String checksum) {
        this.header = header;
        this.packetSize = packetSize;
        this.sequence = sequence;
        this.command = command;
        this.data = data;
        this.checksum = checksum;
    }

    private synchronized static String calculatePacketSize(final String data) {
        return Byte.toString((byte) (5 + (data.length() == 0 ? 0 : data.length()/2)));
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getPacketSize() {
        return packetSize;
    }

    public void setPacketSize(String packetSize) {
        this.packetSize = packetSize;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void recalculateChecksum() {
        this.checksum = "FF";
        //TODO FIX ME - RECALCULATE CHECKSUM CORRECTLY
    }

    public String getMessage() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(header);
        strBuilder.append(packetSize);
        strBuilder.append(sequence);
        strBuilder.append(command);
        strBuilder.append(data);
        strBuilder.append(checksum);
        return strBuilder.toString();
    }
}
