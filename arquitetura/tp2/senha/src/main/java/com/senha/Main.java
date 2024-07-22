package com.senha;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.FileHeader;

public class Main {

  public static final String caminho = "./arquivosTP/";
  public static final AtomicBoolean senhaEncontrada = new AtomicBoolean(false);
  public static String senhaFinal = "";
  public static final int MIN_ASCII = 33;
  public static final int MAX_ASCII = 127;

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
      // System.out.println("Erro ao extrair arquivo final.zip");
    }
  }

  public static void main(String[] args) {
    if (args.length == 0) {
      // System.out.println("Informe o número de threads");
      return;
    }

    // System.out.println(System.getProperty("user.dir"));

    String[] arquivos = { "doc1.zip", "doc2.zip", "doc3.zip", "doc4.zip" };
    int nThreads = Integer.parseInt(args[0]);

    for (String arquivo : arquivos) {
      senhaEncontrada.set(false);
      Hacker[] hackers = new Hacker[nThreads];
      int range = (MAX_ASCII - MIN_ASCII) / nThreads;

      for (int i = 0; i < nThreads; i++) {
        int start = MIN_ASCII + i * range;
        int end = (i == nThreads - 1) ? MAX_ASCII : start + range - 1;

        hackers[i] = new Hacker(caminho + arquivo, start, end);
        hackers[i].start();
      }

      for (Hacker hacker : hackers) {
        try {
          hacker.join();
        } catch (InterruptedException e) {
          // System.out.println(e);
        }
      }

      if (senhaEncontrada.get()) {
        // System.out.println("senha: " + senhaFinal);
      } else {
        // System.out.println("Senha não encontrada para o arquivo: " + arquivo);
      }
    }

    // System.out.println("Senha final completa: " + senhaFinal);
    testaFinal(senhaFinal);

  }

  static class Hacker extends Thread {

    private final int start;
    private final int end;
    ZipFile file;

    Hacker(String arquivo, int start, int end) {
      this.start = start;
      this.end = end;

      // System.out.println("hacker: " + arquivo + " [" + start + ", " + end + "]");

      try {
        if (new File(arquivo).exists()) {
          file = new ZipFile(new File(arquivo));
        } else
          throw new FileNotFoundException("O arquivo não foi encontrado!");
      } catch (Exception e) {
        // System.out.println(e.getMessage());
      }
    }

    @Override
    public void run() {

      if (file == null)
        return;

      try {
        if (!file.isEncrypted()) {
          return;
        }
      } catch (Exception e) {
      }

      int i = start;
      int j = MIN_ASCII;
      int k = MIN_ASCII;
      char[] senha = new char[3];

      while (i <= end && !senhaEncontrada.get()) {

        senha[0] = (char) i;
        senha[1] = (char) j;
        senha[2] = (char) k;

        k++;

        if (k > MAX_ASCII) {
          k = MIN_ASCII;
          j++;
        }

        if (j > MAX_ASCII) {
          j = MIN_ASCII;
          i++;
        }

        if (testaSenha(senha)) {
          senhaEncontrada.set(true);
          senhaFinal += (char) senha[0];
          senhaFinal += (char) senha[1];
          senhaFinal += (char) senha[2];
          return;
        }
      }
    }

    public boolean testaSenha(char[] senha) {
      try {
        file.setPassword(senha);

        for (FileHeader fileHeader : file.getFileHeaders()) {
          file.extractFile(fileHeader, caminho);
          return true;
        }
      } catch (net.lingala.zip4j.exception.ZipException ex) {
        return false;
      }
      return false;
    }
  }
}
