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
public class ComplexMessageProcessor implements IMessageProcessor {

    public synchronized IMessage processMessage(final String message){
        //System.out.println("Complex Message Processor - processMessage:" + message);
        int messageLength = message.length();
        String header = message.substring(0, 2);
        String packetSize = message.substring(2, 4);
        String sequence = message.substring(4, 6);
        String command = message.substring(6, 8);
        String data = message.substring(8, messageLength - 2);
        String checksum = message.substring(messageLength - 2, messageLength);

        return new ComplexMessage(header, packetSize, sequence, command, data, checksum);
    }

    public synchronized String synthMessage(final IMessage message) {
        return message.getMessage();
    }

    public synchronized String getStatusMessage(String message, boolean statusCode) {

        final ComplexMessage complexMessage = (ComplexMessage) processMessage(message);
        String statusMessage;
        switch (complexMessage.getCommand()) {
            case ComplexMessage.CONNECT:
                statusMessage = (statusCode ? ComplexMessage.CONNECT_OK : ComplexMessage.CONNECT_ERROR);
                break;
            default:
                statusMessage = (statusCode ? ComplexMessage.OK : ComplexMessage.ERROR);
                break;
        }

        ComplexMessage responseComplexMessage = new ComplexMessage(complexMessage.getHeader(),
                complexMessage.getSequence(), complexMessage.getCommand(), statusMessage, "");

        responseComplexMessage.recalculateChecksum();
        return responseComplexMessage.getMessage();
    }
}
