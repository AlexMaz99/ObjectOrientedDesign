package org.example.Decorator;

import org.example.Decorator.DataSourceDecorator.CompressionDecorator;
import org.example.Decorator.DataSourceDecorator.EncryptionDecorator;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class DecoratorMain {

    private static final String text = "aaaaabbbccccw";
    public static void main(String[] args) throws IOException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        DataSource dataSource = new FileDataSource("NormalWrite.txt");

        dataSource.writeData(text);
        System.out.println(dataSource.readData());



        FileDataSource dataSource1 = new FileDataSource("EncryptedFile.txt");
        DataSource encryptionDecorator = new EncryptionDecorator(dataSource1);
        encryptionDecorator.writeData(text);

        System.out.println( encryptionDecorator.readData());



        DataSource dataSource2 = new FileDataSource("CompressedFile.txt");
        DataSource compressionDecorator = new CompressionDecorator(dataSource2);
        compressionDecorator.writeData(text);

        System.out.println( compressionDecorator.readData());



    }

}
