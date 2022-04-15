package FileApi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Mapping {
    private static List<String> files = new ArrayList<String>();
    private static String path = "src/assets";

    public static void main(String[] args) {
        mapDir();
        print();
    }

    public static void mapDir() {
        File[] dirFiles = new File(path).listFiles();

        if (dirFiles == null) {
            System.out.println("Erro! O diretorio informado nao existe");
        }

        for (File file : dirFiles) {
            System.out.println(file);
            if (file.isFile()) {
                files.add(file.getName());
            }
        }
    }

    public static List<String> getFiles() {
        return files;
    }

    public static void print() {
        for (String fileName : files) {
            System.out.println("Arquivo: " + fileName);
        }
    }
}
