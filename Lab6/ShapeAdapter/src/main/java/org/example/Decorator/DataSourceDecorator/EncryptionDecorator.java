package org.example.Decorator.DataSourceDecorator;

import org.example.Decorator.DataSource;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class EncryptionDecorator extends DataSourceDecorator {
    public EncryptionDecorator(DataSource wrappee) {
        super(wrappee);
    }

    private final int multiplier = 3;
    private final int adder = 11;


    @Override
    public void writeData(String data) throws BadPaddingException, NoSuchAlgorithmException, IOException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException {
        wrappee.writeData(
                data.chars()
                        .mapToObj(ch -> (char) ch)
                        .map(character -> character * multiplier + adder)
                        .map(integer ->(char)((int)integer))
                        .collect(StringBuilder::new,StringBuilder::appendCodePoint,StringBuilder::append)
                        .toString());
    }

    @Override
    public String readData() throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        return wrappee
                .readData()
                .chars()
                .mapToObj(ch -> (char) ch)
                .map(character -> (character-adder) / multiplier )
                .map(integer ->(char)((int)integer))
                .collect(StringBuilder::new,StringBuilder::appendCodePoint,StringBuilder::append)
                .toString();
    }
/* poddalismy się
    private SecretKey secretKey;
    private Cipher cipher;

    public EncryptionDecorator(DataSource wrappee) throws NoSuchPaddingException, NoSuchAlgorithmException {
        super(wrappee);
        this.cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");

        SecureRandom secureRandom = new SecureRandom();
        int keyBitSize = 256;
        keyGenerator.init(keyBitSize, secureRandom);

        secretKey = keyGenerator.generateKey();
    }

    @Override
    public void writeData(String data) throws NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException, NoSuchPaddingException {
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] plainText  = data.getBytes(StandardCharsets.UTF_8);
        byte[] cipherText = cipher.doFinal(plainText);

        wrappee.writeData(new String(cipherText));
    }

    @Override
    public String readData() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {
        String result = wrappee.readData();
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] plaintext = cipher.doFinal(result.getBytes((StandardCharsets.UTF_8)));


        return new String(plaintext);

    }*/


}
