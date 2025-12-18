import java.io.*;
import java.util.*;

class Student implements Serializable {
    private int roll;
    private String name;
    private float marks;

    public Student(int roll, String name, float marks) {
        this.roll = roll;
        this.name = name;
        this.marks = marks;
    }

    public int getRoll() {
        return roll;
    }

    public void display() {
        System.out.println("Roll: " + roll + " | Name: " + name + " | Marks: " + marks);
    }
    public static void addStudent(List<Student> list) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Roll Number: ");
        int roll = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Marks: ");
        float marks = sc.nextFloat();

        list.add(new Student(roll, name, marks));
        saveToFile(list);

        System.out.println("Student added successfully.");
    }

    public static void displayAll(List<Student> list) {
        if (list.isEmpty()) {
            System.out.println("No records found.");
            return;
        }
        System.out.println("\n--- Student Records ---");
        for (Student s : list) {
            s.display();
        }
    }

    public static void searchStudent(List<Student> list) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Roll Number to search: ");
        int r = sc.nextInt();

        for (Student s : list) {
            if (s.getRoll() == r) {
                System.out.println("Record Found:");
                s.display();
                return;
            }
        }
        System.out.println("Record not found.");
    }

    public static void updateStudent(List<Student> list) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Roll Number to update: ");
        int r = sc.nextInt();
        sc.nextLine();

        for (Student s : list) {
            if (s.getRoll() == r) {
                System.out.print("Enter New Name: ");
                s.name = sc.nextLine();
                System.out.print("Enter New Marks: ");
                s.marks = sc.nextFloat();
                saveToFile(list);
                System.out.println("Record updated successfully.");
                return;
            }
        }
        System.out.println("Record not found.");
    }

    public static void deleteStudent(List<Student> list) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Roll Number to delete: ");
        int r = sc.nextInt();

        Iterator<Student> it = list.iterator();
        while (it.hasNext()) {
            if (it.next().getRoll() == r) {
                it.remove();
                saveToFile(list);
                System.out.println("Record deleted successfully.");
                return;
            }
        }
        System.out.println("Record not found.");
    }
    @SuppressWarnings("unchecked")
    public static List<Student> loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("students.dat"))) {
            return (List<Student>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void saveToFile(List<Student> list) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("students.dat"))) {
            oos.writeObject(list);
        } catch (IOException e) {
            System.out.println("File error.");
        }
    }
}

public class studentRecordManagementSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Student> students = Student.loadFromFile();

        int choice;
        do {
            System.out.println("\n1. Add Student\n2. Display Students\n3. Search Student\n4. Update Student\n5. Delete Student\n0. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> Student.addStudent(students);
                case 2 -> Student.displayAll(students);
                case 3 -> Student.searchStudent(students);
                case 4 -> Student.updateStudent(students);
                case 5 -> Student.deleteStudent(students);
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }
}
