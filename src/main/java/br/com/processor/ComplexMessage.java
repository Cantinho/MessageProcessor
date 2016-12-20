package br.com.processor;

/**
 * Copyright 2016 Cantinho. All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * @author Samir Trajano Feitosa
 * @author Jordão Ezequiel Serafim de Araújo
 * @author Cantinho - Github https://github.com/Cantinho
 * @since 2016
 * @license Apache 2.0
 *
 * This file is licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.  For additional information regarding
 * copyright in this work, please see the NOTICE file in the top level
 * directory of this distribution.
 *
 */
public class ComplexMessage implements IMessage {

    public static final String HEADER = "AA";
    public static final String CONNECT = "A0";
    public static final String DISCONNECT = "A1";
    public static final String STATUS = "A2";
    public static final String LOCK = "C0";
    public static final String UNLOCK = "C1";
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

    public ComplexMessage() {}

    public ComplexMessage(String header, String sequence, String command, String data, String checksum) {
        this(header, calculatePacketSize(data) , sequence, command, data, checksum);
    }

    public ComplexMessage(String header, String packetSize, String sequence, String command, String data, String checksum) {
        this.header = header;
        this.packetSize = packetSize;
        this.sequence = sequence;
        this.command = command;
        this.data = data;
        this.checksum = checksum;
    }

    private synchronized static String calculatePacketSize(final String data) {
        return String.format("%02X", (byte)(5 + (data.length() == 0 ? 0 : data.length()/2)));
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
        final String message = getMessage().substring(0, getMessage().length() - 2);
        this.checksum = String.format("%02X", (byte) (checksum(hexStringToByteArray(message), 0)));
    }

    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public synchronized static byte checksum(byte[] byteArray, int method) {
        if(method == 1) {
            //ADDITION
            byte checksum = 0;
            for(int i = 0; i < byteArray.length; i++)
            {
                checksum += byteArray[i];
            }
            return checksum;
        } else {
            //XORING
            byte xorChecksum = 0;
            for(int i = 0; i < byteArray.length-1; i++)
            {
                xorChecksum ^= byteArray[i];
            }
            return xorChecksum;
        }
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
