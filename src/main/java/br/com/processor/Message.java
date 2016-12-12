package br.com.processor;

/**
 * Created by jordao on 12/12/16.
 */
public class Message implements IMessage {

    private int messageLength;
    private String header;
    private String packetSize;
    private String sequence;
    private String command;
    private String data;
    private String checksum;

    public Message() {}

    public Message(int messageLength, String header, String packetSize, String sequence, String command, String data, String checksum) {
        this.messageLength = messageLength;
        this.header = header;
        this.packetSize = packetSize;
        this.sequence = sequence;
        this.command = command;
        this.data = data;
        this.checksum = checksum;
    }

    public int getMessageLength() {
        return messageLength;
    }

    public void setMessageLength(int messageLength) {
        this.messageLength = messageLength;
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

    public String getMessage() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(header + ";");
        strBuilder.append(packetSize + ";");
        strBuilder.append(sequence + ";");
        strBuilder.append(command + ";");
        strBuilder.append(data + ";");
        strBuilder.append(checksum);
        return strBuilder.toString();
    }
}
