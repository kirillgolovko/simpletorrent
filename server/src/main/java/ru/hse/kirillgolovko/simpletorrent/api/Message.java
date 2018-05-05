package ru.hse.kirillgolovko.simpletorrent.api;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Scanner;

@JsonIgnoreProperties({"rawData"})
public class Message {
    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    }

    public static Message getMessageFromInputStream (InputStream inputStream) throws IOException {
        Scanner scanner = new Scanner(inputStream);
        int jsonLenght = 0;
        int rawLenght = 0;
        while (inputStream.available() < 4){
            Thread.yield();
        }
        byte[] bytes = new byte[4];
        inputStream.read(bytes);
        jsonLenght = ByteBuffer.wrap(bytes).getInt();
        while (inputStream.available() < 4){
            Thread.yield();
        }
        inputStream.read(bytes);
        rawLenght = ByteBuffer.wrap(bytes).getInt();
        byte[] jsonBytes = new byte[jsonLenght];
        inputStream.read(jsonBytes);

        while (inputStream.available() < rawLenght){
            Thread.yield();
        }
        byte[] rawData = new byte[rawLenght];
        inputStream.read(rawData);

        String json = new String(jsonBytes, Charset.forName("UTF-8"));

        Message next;
        synchronized (objectMapper){
            next = objectMapper.readValue(json, Message.class);
        }
        next.messageBody.rawData = rawData;
        next.messageBody.jsonLenght = jsonLenght;
        next.messageBody.rawDataLenght = rawLenght;
        return next;
    }

    public static void writeMessageToOutputStream(Message message, OutputStream outputStream) throws Exception{
        byte[] json;
        String jsonString;
        synchronized (objectMapper){
            jsonString = objectMapper.writeValueAsString(message);
        }
        json = jsonString.getBytes("UTF-8");
        outputStream.write(ByteBuffer.allocate(4).putInt(json.length).array());
        outputStream.write(ByteBuffer.allocate(4).putInt(
                message.messageBody.rawData != null ? message.messageBody.rawData.length : 0)
                .array());
        outputStream.write(json);
        if(message.messageBody.rawData != null){
            outputStream.write(message.messageBody.rawData);
        }
        outputStream.flush();
    }

    public Message(){

    }

    private MessageBody messageBody;

    public Message(MessageBody messageBody){
        this.messageBody = messageBody;
    }

    public MessageBody getMessageBody() {
        return messageBody;
    }
}
