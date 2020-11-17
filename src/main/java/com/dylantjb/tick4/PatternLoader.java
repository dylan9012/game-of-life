package com.dylantjb.tick4;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;

public class PatternLoader {

    public static List<Pattern> loadFromURL(String url) throws IOException {
        URL destination = new URL(url);
        URLConnection conn = destination.openConnection();
        return load(new InputStreamReader(conn.getInputStream()));
    }

    public static List<Pattern> load(Reader r) throws IOException {
        String currentLine;
        List<Pattern> resultList = new LinkedList<>();
        BufferedReader buff = new BufferedReader(r);

        int index = -1;
        while ((currentLine = buff.readLine()) != null) {
            index++;
            try {
                resultList.add(new Pattern(currentLine, index));
            } catch (PatternFormatException ignored) {
            }
        }

        return resultList;
    }

    public static List<Pattern> loadFromDisk(String filename) throws IOException {
        return load(new FileReader(filename));
    }

}

