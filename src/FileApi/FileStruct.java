package FileApi;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileStruct {
    private String name;
    private byte[] hash;
    private String path;

    public FileStruct(String path, String name) {
        this.setName(name);
        this.setPath(path);
        try {
            this.setHash();
        } catch (NoSuchAlgorithmException | IOException err) {
            System.err.println("Ocorreu um erro ao gerar o hash do arquivo: " + name);
            System.err.println("Erro: " + err);
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash() throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        try (InputStream is = Files.newInputStream(Paths.get(this.path));
                DigestInputStream dis = new DigestInputStream(is, md)) {
        }
        this.hash = md.digest();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}