package Team14;

import java.io.*;
import java.util.Scanner;

abstract class Employee1 {
    protected String name;
    protected int id;
    protected double salary;
    
    public Employee1(String name, int id, double salary) {
        this.name = name;
        this.id = id;
        this.salary = salary;
    }
    
    public abstract double calculateSalary();
    
    public void displayDetails() {
        System.out.println("ID: " + id + " | Name: " + name + " | Salary: " + calculateSalary());
    }
}

class FullTimeEmployee1 extends Employee {
    private double bonus;
    
    public FullTimeEmployee1(String name, int id, double salary, double bonus) {
        super(name, id, salary);
        this.bonus = bonus;
    }
    
    @Override
    public double calculateSalary() {
        return salary + bonus;
    }
}

class PartTimeEmployee1 extends Employee {
    private int hoursWorked;
    private double hourlyRate;
    
    public PartTimeEmployee1(String name, int id, double hourlyRate, int hoursWorked) {
        super(name, id, 0);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }
    
    @Override
    public double calculateSalary() {
        return hourlyRate * hoursWorked;
    }
}

class PayrollFileHandler1 {
    public static void saveEmployee(Employee emp) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("payroll.txt", true))) {
            writer.write(emp.id + "," + emp.name + "," + emp.calculateSalary());
            writer.newLine();
            System.out.println("Employee data saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving payroll data: " + e.getMessage());
        }
    }
}

class PayrollProcessor1 extends Thread {
    private Employee emp;
    
    public PayrollProcessor1(Employee emp) {
        this.emp = emp;
    }
    
    @Override
    public void run() {
        System.out.println("Processing payroll for: " + emp.name);
        emp.displayDetails();
        PayrollFileHandler.saveEmployee(emp);
    }
}

public class PayrollManagementSystem1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        
        while (!exit) {
            System.out.println("\nPayroll Management System");
            System.out.println("1. Add Employee");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");
            int option = scanner.nextInt();
            scanner.nextLine();
            
            if (option == 1) {
                System.out.println("Enter Employee Type (1: Full-time, 2: Part-time): ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                System.out.println("Enter Name: ");
                String name = scanner.nextLine();
                
                System.out.println("Enter ID: ");
                int id = scanner.nextInt();
                
                Employee emp = null;
                
                if (choice == 1) {
                    System.out.println("Enter Basic Salary: ");
                    double salary = scanner.nextDouble();
                    
                    System.out.println("Enter Bonus: ");
                    double bonus = scanner.nextDouble();
                    emp = new FullTimeEmployee(name, id, salary, bonus);
                } else if (choice == 2) {
                    System.out.println("Enter Hourly Rate: ");
                    double hourlyRate = scanner.nextDouble();
                    
                    System.out.println("Enter Hours Worked: ");
                    int hoursWorked = scanner.nextInt();
                    emp = new PartTimeEmployee(name, id, hourlyRate, hoursWorked);
                } else {
                    System.out.println("Invalid choice! Try again.");
                    continue;
                }
                
                PayrollProcessor processor = new PayrollProcessor(emp);
                processor.start();
            } else if (option == 2) {
                exit = true;
                System.out.println("Exiting Payroll Management System...");
            } else {
                System.out.println("Invalid option! Try again.");
            }
        }
        scanner.close();
    }
}

