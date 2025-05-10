package Team14;

import java.io.*;
import java.util.*;

// Abstract class defining Payroll structure
abstract class Employee {
    protected String name;
    protected int id;
    protected double salary;
    
    public Employee(String name, int id, double salary) {
        this.name = name;
        this.id = id;
        this.salary = salary;
    }
    
    // Abstract method for calculating salary
    public abstract double calculateSalary();
    
    public void displayDetails() {
        System.out.println("ID: " + id + " | Name: " + name + " | Salary: " + calculateSalary());
    }
}

// Full-time Employee class extending Employee
class FullTimeEmployee extends Employee {
    private double bonus;
    
    public FullTimeEmployee(String name, int id, double salary, double bonus) {
        super(name, id, salary);
        this.bonus = bonus;
    }
    
    @Override
    public double calculateSalary() {
        return salary + bonus;
    }
}

// Part-time Employee class implementing Payroll interface
class PartTimeEmployee extends Employee {
    private int hoursWorked;
    private double hourlyRate;
    
    public PartTimeEmployee(String name, int id, double hourlyRate, int hoursWorked) {
        super(name, id, 0);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }
    
    @Override
    public double calculateSalary() {
        return hourlyRate * hoursWorked;
    }
}

// File handling for storing payroll data
class PayrollFileHandler {
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

// Multithreading for payroll processing
class PayrollProcessor extends Thread {
    private Employee emp;
    
    public PayrollProcessor(Employee emp) {
        this.emp = emp;
    }
    
    @Override
    public void run() {
        System.out.println("Processing payroll for: " + emp.name);
        emp.displayDetails();
        PayrollFileHandler.saveEmployee(emp);
    }
}

// Payroll Manager using Collection Framework
class PayrollManager {
    private List<Employee> employees = new ArrayList<>();
    
    public void addEmployee(Employee emp) {
        employees.add(emp);
    }
    
    public void displayAllEmployees() {
        for (Employee emp : employees) {
            emp.displayDetails();
        }
    }
}

// Main class to handle user interaction
public class PayrollManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PayrollManager manager = new PayrollManager();
        
        while (true) {
            System.out.println("Enter Employee Type (1: Full-time, 2: Part-time, 3: Display All, 4: Exit): ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            if (choice == 3) {
                manager.displayAllEmployees();
                continue;
            } else if (choice == 4) {
                break;
            }
            
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
                System.out.println("Invalid choice! Exiting...");
                System.exit(0);
            }
            
            manager.addEmployee(emp);
            PayrollProcessor processor = new PayrollProcessor(emp);
            processor.start();
        }
    }
}
