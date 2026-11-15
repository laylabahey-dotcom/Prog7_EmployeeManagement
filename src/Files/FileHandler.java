package Files;

import java.io.*;
import java.util.*;

public abstract class FileHandler<T> {

    protected boolean fileExists(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    public abstract void save(String fileName, ArrayList<T> items);

    public abstract ArrayList<T> load(String fileName);
}
