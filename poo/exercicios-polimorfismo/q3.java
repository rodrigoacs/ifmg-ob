public class q3 {
  public static void main(String[] args) {

  }

  static public class Employee {
    private String name;

    public Employee(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public double getPay() {
      return 0;
    }

    public void printPay() {
      System.out.println();
    }
  }

  static public class Salaried extends Employee {
    private double salary;

    public double getSalary() {
      return salary;
    }

    public void setSalary(double salary) {
      this.salary = salary;
    }

    public Salaried(String name) {
      super(name);
    }

  }
}
