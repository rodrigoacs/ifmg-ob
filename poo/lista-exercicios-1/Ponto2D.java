public class Ponto2D {
  double x, y;

  public void setX(double x) {
    this.x = x;
  }

  public void setY(double y) {
    this.y = y;
  }

  public double getX() {
    return this.x;
  }

  public double getY() {
    return this.y;
  }

  public Ponto2D() {
    this.setX(0);
    this.setY(0);
  }

  public Ponto2D(double x, double y) {
    this.setX(x);
    this.setY(y);
  }

  public Ponto2D(Ponto2D ponto) {
    this.setX(ponto.getX());
    this.setY(ponto.getY());
  }

  public void movePonto() {
    this.setX(0);
    this.setY(0);
  }

  public void movePonto(double x, double y) {
    this.setX(x);
    this.setY(y);
  }

  public void movePonto(Ponto2D destino) {
    this.setX(destino.getX());
    this.setY(destino.getY());
  }

  public boolean comparaPontos(Ponto2D ponto) {
    return this.getX() == ponto.getX() && this.getY() == ponto.getY() ? true : false;
  }

  public String representaString() {
    return "(" + this.getX() + "," + this.getY() + ")";
  }

  public double calculaDistancia(Ponto2D ponto) {
    return Math.sqrt(Math.pow(this.x - ponto.x, 2) + Math.pow(this.y - ponto.y, 2));
  }

}
