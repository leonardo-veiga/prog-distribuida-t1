package FileApi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MapFiles {
    private ArrayList<FileStruct> files = new ArrayList<FileStruct>();
    private String path = "src/assets";

    public MapFiles() {
        File[] dirFiles = new File(path).listFiles();

        // Erro
        if (dirFiles == null) {
            System.out.println("Erro! O diretorio informado nao existe");
            return;
        }

        // Carrega arquivos
        for (File file : dirFiles) {
            if (file.isFile()) {
                FileStruct _file = new FileStruct(file.getPath(), file.getName());
                files.add(_file);
            }
        }

    }

    public List<FileStruct> getFiles() {
        return files;
    }

    public void print() {
        for (FileStruct file : files) {
            System.out.println("/////////////////");
            System.out.println("Nome: " + file.getName());
            System.out.println("Hash: " + file.getHash().toString());
            System.out.println("Path: " + file.getPath());
            System.out.println("/////////////////");
        }
    }
}
