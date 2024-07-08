package com.senha;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.FileHeader;

public class Main {
  public static final String caminho = "./arquivosTP/";
  public static final AtomicBoolean senhaEncontrada = new AtomicBoolean(false);
  public static String senhaFinal = "";

  public static boolean testaSenha(String senha, String arquivo) {
    ZipFile zipFile = new ZipFile(new File(caminho + arquivo));
    try {
      if (zipFile.isEncrypted()) {
        zipFile.setPassword(senha.toCharArray());
      }

      List<FileHeader> fileHeaderList = zipFile.getFileHeaders();

      for (FileHeader fileHeader : fileHeaderList) {
        zipFile.extractFile(fileHeader, caminho);
        return true;
      }
    } catch (net.lingala.zip4j.exception.ZipException ex) {
      return false;
    }
    return false;
  }

  public static void testaFinal(String senha) {
    ZipFile zipFile = new ZipFile(new File(caminho + "final.zip"));
    try {
      if (zipFile.isEncrypted()) {
        zipFile.setPassword(senha.toCharArray());
      }

      List<FileHeader> fileHeaderList = zipFile.getFileHeaders();

      for (FileHeader fileHeader : fileHeaderList) {
        zipFile.extractFile(fileHeader, caminho);
      }
    } catch (net.lingala.zip4j.exception.ZipException ex) {
      System.out.println("Erro ao extrair arquivo final.zip");
    }
  }

  public static void main(String[] args) {
    String[] arquivos = { "doc1.zip", "doc2.zip", "doc3.zip", "doc4.zip" };
    int nThreads = Runtime.getRuntime().availableProcessors();

    for (String arquivo : arquivos) {
      senhaEncontrada.set(false);
      Hacker[] hackers = new Hacker[nThreads];
      int range = (127 - 33) / nThreads;

      for (int i = 0; i < nThreads; i++) {
        int start = 33 + i * range;
        int end = (i == nThreads - 1) ? 127 : start + range;

        hackers[i] = new Hacker(arquivo, start, end);
        hackers[i].start();
      }

      for (Hacker hacker : hackers) {
        try {
          hacker.join();
        } catch (InterruptedException e) {
          System.out.println(e);
        }
      }

      if (!senhaEncontrada.get()) {
        System.out.println("Senha não encontrada para o arquivo: " + arquivo);
      }
    }

    System.out.println("Senha final: " + senhaFinal);

    testaFinal(senhaFinal);

  }

  static class Hacker extends Thread {
    private final String arquivo;
    private final int start;
    private final int end;

    Hacker(String arquivo, int start, int end) {
      this.arquivo = arquivo;
      this.start = start;
      this.end = end;
    }

    @Override
    public void run() {
      for (int i = start; i < end && !senhaEncontrada.get(); i++) {
        for (int j = 33; j < 127 && !senhaEncontrada.get(); j++) {
          for (int k = 33; k < 127 && !senhaEncontrada.get(); k++) {
            String senha = "" + (char) i + (char) j + (char) k;
            if (testaSenha(senha, arquivo)) {
              System.out.println("Senha encontrada: " + senha + " para o arquivo: " + arquivo);
              senhaFinal += senha;
              senhaEncontrada.set(true);
            }
          }
        }
      }
    }
  }
}
