package util;

import org.junit.Test;

import java.io.IOException;

public class FileLoaderTest {

    @Test
    public void loadFile() {
        String fileName = "test.txt";
        try {
            System.out.println(FileLoader.loadFile("/data/", fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}