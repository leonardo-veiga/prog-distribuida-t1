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
    private String splittingPath;
    private String mergingPath;

    public FileStruct(String path, String name) {
        this.setName(name);
        this.setPath(path);
        this.setMergingPath(name);
        this.setSplittingPath(name);
        try {
            this.setHash();
        } catch (NoSuchAlgorithmException | IOException err) {
            System.err.println("Ocorreu um erro ao gerar o hash do arquivo: " + name);
            System.err.println("Erro: " + err);
        }
    }

    public String getMergingPath() {
        return mergingPath;
    }

    public void setMergingPath(String _name) {
        this.mergingPath = "src/FileApi/merging/";
    }

    public String getSplittingPath() {
        return splittingPath;
    }

    public void setSplittingPath(String _name) {
        this.splittingPath = "src/FileApi/splitting/";
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
        if (path == null) {
            this.path = "src/assets/" + this.getName();
        }
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash() throws NoSuchAlgorithmException, IOException {
        if (this.path == null) {
            return;
        }
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
