public class Main {

    // Base class Person
    static class Person {
        private String name;
        private int age;

        // Constructor
        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        // Getter and Setter for name
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        // Getter and Setter for age
        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        // Method to display info
        public void displayInfo() {
            System.out.println("Name: " + name);
            System.out.println("Age: " + age);
        }
    }

    // Subclass Student
    static class Student extends Person {
        private String studentId;

        // Constructor
        public Student(String name, int age, String studentId) {
            super(name, age); // Call the constructor of Person
            this.studentId = studentId;
        }

        // Getter and Setter for studentId
        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        // Overridden method to display info
        @Override
        public void displayInfo() {
            super.displayInfo(); // Call the method from Person
            System.out.println("Student ID: " + studentId);
        }
    }

    // Main method
    public static void main(String[] args) {
        // Create a Student object
        Student student = new Student("Paul Nathan Tapel", 20, "S123456");

        // Display student information
        student.displayInfo();
    }
}
