package ru.job4j.cache;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public class DirFileCache extends AbstractCache<String, String> {

    private final String cachingDir;

    public DirFileCache(String cachingDir) {
        this.cachingDir = cachingDir;
    }

    @Override
    protected String load(String key) {
        String res = get(key);
        Path path = Path.of(cachingDir + "\\" + key);
        StringBuilder stringBuilder = new StringBuilder();
        if (res == null) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(String.valueOf(path)))) {
                for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                    stringBuilder.append(line).append("\n");
                }
            } catch (IOException e) {
            e.printStackTrace();
            }
            res = stringBuilder.toString();
            put(key, res);
        }

        return res;
    }
}
