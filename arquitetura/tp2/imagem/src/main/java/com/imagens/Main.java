package com.imagens;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main {
  public static int[][] lerPixels(String caminho) {
    BufferedImage bufferedImage;
    try {
      bufferedImage = ImageIO.read(new File(caminho));
      int largura = bufferedImage.getWidth(null);
      int altura = bufferedImage.getHeight(null);

      int[][] pixels = new int[largura][altura];
      for (int i = 0; i < largura; i++) {
        for (int j = 0; j < altura; j++) {
          // normalizando de forma simplificada para imagem escala de cinza (é esperado
          // ocorrer "clareamento")
          float vermelho = new Color(bufferedImage.getRGB(i, j)).getRed();
          float verde = new Color(bufferedImage.getRGB(i, j)).getGreen();
          float azul = new Color(bufferedImage.getRGB(i, j)).getBlue();
          int escalaCinza = (int) (vermelho + verde + azul) / 3;

          pixels[i][j] = escalaCinza;
        }
      }
      return pixels;
    } catch (IOException ex) {
      System.err.println("Erro no caminho indicado pela imagem");
    }
    return null;
  }

  public static void gravarPixels(String caminhoGravar, int pixels[][]) {
    caminhoGravar = caminhoGravar
        .replace(".png", "_modificado.png")
        .replace(".jpg", "_modificado.jpg");

    int largura = pixels.length;
    int altura = pixels[0].length;

    BufferedImage imagem = new BufferedImage(largura, altura, BufferedImage.TYPE_BYTE_GRAY);

    // transformando a mat. em um vetor de bytes
    byte bytesPixels[] = new byte[largura * altura];
    for (int x = 0; x < largura; x++) {
      for (int y = 0; y < altura; y++) {
        bytesPixels[y * (largura) + x] = (byte) pixels[x][y];
      }
    }

    // copaindo todos os bytes para a nova imagem
    imagem.getRaster().setDataElements(0, 0, largura, altura, bytesPixels);

    // criamos o arquivo e gravamos os bytes da imagem nele
    File ImageFile = new File(caminhoGravar);
    try {
      ImageIO.write(imagem, "png", ImageFile);
      System.out.println("Nova Imagem dispon. em: " + caminhoGravar);
    } catch (IOException e) {
      System.err.println("Erro no caminho indicado pela imagem");
    }
  }

  public static int[][] corrigirImagem(int imgMat[][]) {
    int largura = imgMat.length;
    int altura = imgMat[0].length;

    int[][] novaMatriz = new int[largura][altura];

    for (int i = 0; i < largura; i++) {
      for (int j = 0; j < altura; j++) {
        if (imgMat[i][j] == 0 || imgMat[i][j] == 255) {
          int sum = 0;
          int count = 0;

          if (i > 0) {
            sum += imgMat[i - 1][j];
            count++;
          }
          if (i < largura - 1) {
            sum += imgMat[i + 1][j];
            count++;
          }
          if (j > 0) {
            sum += imgMat[i][j - 1];
            count++;
          }
          if (j < altura - 1) {
            sum += imgMat[i][j + 1];
            count++;
          }

          novaMatriz[i][j] = (count > 0) ? (sum / count) : imgMat[i][j];
        } else {
          novaMatriz[i][j] = imgMat[i][j];
        }
      }
    }

    return novaMatriz;
  }

  public static void main(String[] args) {
    File directory = new File("originais/");
    File imagesFile[] = directory.listFiles();

    // iamgens que precisam ser corrigidas
    for (File img : imagesFile) {
      int imgMat[][] = lerPixels(img.getAbsolutePath());

      // fica a seu critério modificar essa invocação
      imgMat = corrigirImagem(imgMat);

      // grava nova imagem com as correções
      if (imgMat != null) {
        gravarPixels(img.getAbsolutePath().replace("originais", "modificadas"), imgMat);
      }
    }
  }
}
