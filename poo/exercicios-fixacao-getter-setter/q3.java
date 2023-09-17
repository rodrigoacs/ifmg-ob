public class q3 {
  public static void main(String[] args) {
    Retangulo r = new Retangulo();
    r.setBase(10);
    r.setAltura(5);
    System.out.println("Area: " + r.area());
    System.out.println("Perimetro: " + r.perimetro());
  }

  static class Retangulo {
    private double base = 1;
    private double altura = 1;

    public double getBase() {
      return base;
    }

    public void setBase(double base) {
      if (base > 0 && base <= 20) {
        this.base = base;
      }
    }

    public double getAltura() {
      return altura;
    }

    public void setAltura(double altura) {
      if (altura > 0 && altura <= 20) {
        this.altura = altura;
      }
    }

    public double area() {
      return base * altura;
    }

    public double perimetro() {
      return 2 * (base + altura);
    }
  }
}
