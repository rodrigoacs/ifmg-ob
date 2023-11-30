import java.util.ArrayList;
import java.util.Scanner;

public class q3 {

  static Scanner input = new Scanner(System.in);

  public static void main(String[] args) {
    ArrayList<Employee> list = new ArrayList<Employee>();

    System.out.println("Insira o numero de funcionarios: ");
    int n = input.nextInt();
    input.nextLine();

    for (int i = 0; i < n; i++) {
      System.out.println("Insira o tipo de funcionario (1 - Horista, 2 - Assalariado): ");
      int type = input.nextInt();
      input.nextLine();

      if (type == 1) {
        Hourly employee = new Hourly("");
        employee.insertData();
        list.add(employee);
      } else if (type == 2) {
        Salaried employee = new Salaried("");
        employee.insertData();
        list.add(employee);
      }
    }

    for (Employee employee : list) {
      System.out.println("Nome: " + employee.getName());
      employee.printPay();
    }

    double total = 0;
    for (Employee employee : list) {
      total += employee.getPay();
    }
    System.out.println("Total: " + total);

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
      System.out.println("Salario: " + getPay());
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

    @Override
    public double getPay() {
      return getSalary();
    }

    public void insertData() {
      System.out.println("Insira o nome: ");
      setName(input.nextLine());
      System.out.println("Insira o salario: ");
      setSalary(input.nextDouble());
    }
  }

  static public class Hourly extends Employee {
    private double hourlyRate;
    private double hours;

    public double getHourlyRate() {
      return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
      this.hourlyRate = hourlyRate;
    }

    public double getHours() {
      return hours;
    }

    public void setHours(double hours) {
      this.hours = hours;
    }

    public Hourly(String name) {
      super(name);
    }

    @Override
    public double getPay() {
      return getHourlyRate() * getHours();
    }

    public void insertData() {
      System.out.println("Insira o nome: ");
      setName(input.nextLine());
      System.out.println("Insira o valor da hora: ");
      setHourlyRate(input.nextDouble());
      System.out.println("Insira a quantidade de horas: ");
      setHours(input.nextDouble());
    }
  }
}
