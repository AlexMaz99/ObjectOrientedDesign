package org.example.Decorator.DataSourceDecorator;

import org.example.Decorator.DataSource;
import org.example.Decorator.DataSourceDecorator.DataSourceDecorator;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

// zakłada ze w tekscie nie ma liczb
public class CompressionDecorator extends DataSourceDecorator {
    public CompressionDecorator(DataSource wrappee) {
        super(wrappee);
    }

    @Override
    public void writeData(String data) throws BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, IOException, NoSuchPaddingException, InvalidKeyException {

        StringBuilder compressed = new StringBuilder();

        for (int i = 0; i < data.length(); i++) {

            char currLetter = data.charAt(i);
            int j = i + 1;
            for (; j < data.length() && data.charAt(j) == currLetter; j++) {
            }

            if (j - i > 2) {
                compressed.append(currLetter).append(j - i);
                i = j - 1;
            } else {
                compressed.append(currLetter);
            }
        }
        wrappee.writeData(compressed.toString());
    }

    @Override
    public String readData() throws NoSuchPaddingException, IOException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        String readData = wrappee.readData();
        StringBuilder uncompressed = new StringBuilder();

        for (int i = 0; i < readData.length(); i++) {
            char currLetter = readData.charAt(i);
            if (Character.isDigit(currLetter)) {
                int nrOFOcc = Integer.parseInt(String.valueOf(currLetter));

                char[] repeat = new char[nrOFOcc - 1];
                Arrays.fill(repeat, readData.charAt(i - 1));
                uncompressed.append(new String(repeat));
            } else {
                uncompressed.append(currLetter);
            }
        }
        return uncompressed.toString();
    }


}
