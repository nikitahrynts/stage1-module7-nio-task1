package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class FileReader {

    Profile profile = new Profile();

    public Profile getDataFromFile(File file) {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {
            FileChannel fileChannel = randomAccessFile.getChannel();

            long fileSize = fileChannel.size();

            ByteBuffer buffer = ByteBuffer.allocate((int) fileSize);
            fileChannel.read(buffer);
            buffer.flip();

            StringBuilder result = new StringBuilder();
            for (long i = 0; i < fileSize; i++) {
                result.append((char) buffer.get());
            }
            String[] attrs = result.toString().split("\r\n");
            for (String attr : attrs) {
                processData(attr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return profile;
    }

    public void processData(String line) {
        String[] attrs = line.split(" ");
        switch (attrs[0]) {
            case "Name:":
                profile.setName(attrs[1]);
                break;
            case "Age:":
                profile.setAge(Integer.valueOf(attrs[1]));
                break;
            case "Email:":
                profile.setEmail(attrs[1]);
                break;
            case "Phone:":
                profile.setPhone(Long.valueOf(attrs[1]));
                break;
            default:
                break;
        }
    }
}
