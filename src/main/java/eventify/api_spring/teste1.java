package eventify.api_spring;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class teste1 {
    public static void main(String[] args) {
        File currentDirectory = new File(System.getProperty("user.dir"));
        File diretorioPai = currentDirectory.getParentFile();
        System.setProperty("user.dir", diretorioPai.getAbsolutePath());
        String caminho = diretorioPai + "\\web-app\\public\\img-buffet";


    }
}



