package org.example.Decorator.DataSourceDecorator;

import lombok.AllArgsConstructor;
import org.example.Decorator.DataSource;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@AllArgsConstructor
public class DataSourceDecorator implements DataSource {

    DataSource wrappee;


    public void writeData(String data) throws NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException, NoSuchPaddingException {

    }

    public String readData() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {
        return null;
    }
}
