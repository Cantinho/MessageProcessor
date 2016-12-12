package br.com.processor;

/**
 * Created by jordao on 12/12/16.
 */
public class MessageProcessor implements IMessageProcessor {

    public synchronized IMessage processMessage(final String message){
        int messageLength = message.length();
        String header = message.substring(0, 2);
        String packetSize = message.substring(2, 4);
        String sequence = message.substring(4, 6);
        String command = message.substring(6, 8);
        String data = message.substring(8, messageLength - 2);
        String checksum = message.substring(messageLength - 2, messageLength);

        return new CloudiaMessage(header, packetSize, sequence, command, data, checksum);
    }

    public synchronized String synthMessage(final IMessage message) {
        return message.getMessage();
    }

}
