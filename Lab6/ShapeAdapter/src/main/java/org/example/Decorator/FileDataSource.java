package org.example.Decorator;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.*;

@AllArgsConstructor
@Getter
public class FileDataSource implements DataSource {
    private final String filename;

    public void writeData(String data) throws IOException {
        FileWriter fw = new FileWriter(filename);

        fw.write(data);
        fw.close();

    }

    public String readData() throws IOException {
        FileReader fr = new FileReader(filename);
        BufferedReader bufferedReader =  new BufferedReader(fr);
        StringBuilder result= new StringBuilder();
        String line = null;
        while((line = bufferedReader.readLine()) != null) {
            result.append(line);
            result.append("\n");

        }
        bufferedReader.close();
        return result.toString();
    }


}
