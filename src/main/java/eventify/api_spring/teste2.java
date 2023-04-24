package eventify.api_spring;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class teste2 {

    public static void main(String[] args) throws IOException {
        File currentDirectory = new File(System.getProperty("user.dir"));


        // Obtem o caminho atual
        System.out.println("Caminho atual: " + currentDirectory);

        // Cria um objeto File para representar o diretório pai
        File diretorioPai = currentDirectory.getParentFile();

        // Verifica se o diretório pai existe
        if (diretorioPai.exists()) {

            // Define o diretório pai como o diretório atual
            System.setProperty("user.dir", diretorioPai.getAbsolutePath());
            System.out.println("Caminho atualizado: " + System.getProperty("user.dir"));
        } else {
            System.out.println("O diretório pai não existe.");
        }
        // Define o caminho completo para a pasta que deseja criar
        String caminho = diretorioPai + "\\web-app\\public\\img-buffet";

        // Cria um objeto File que representa a pasta que deseja criar
        File pasta = new File(caminho);

        // Cria a pasta usando o método mkdir()

        // Verifica se a pasta foi criada com sucesso
        if (!pasta.exists()) {
            boolean criado = pasta.mkdir();
            if (criado) {
                System.out.println("Pasta criada com sucesso!");
                System.out.println("Caminho completo: " + pasta.getAbsolutePath());
            } else {
                System.out.println("Não foi possível criar a pasta.");
            }
        } else {
            System.out.println("A pasta já existe no caminho: " + pasta.getAbsolutePath());
        }

    }
}
