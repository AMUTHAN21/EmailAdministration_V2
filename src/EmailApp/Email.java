package EmailApp;
import java.util.*;
import java.sql.*;

public class Email {
    private String firstName;
    private String lastName;
    private String password;
    private String department;
    private String email;
    private int mailboxCapacity=500;
    private int defaultPasswordLength=10;
    private String alternateEmail;
    private String companySuffix="wwenetwork.com";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/email_app";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD="root";

    public Email(String firstName, String lastName)
    {
        this.firstName=firstName;
        this.lastName=lastName;

        this.department=setDepartment();

        this.password= randomPassword(defaultPasswordLength);
        System.out.println("Your Password is: "+this.password);

        email= firstName.toLowerCase()+"."+lastName.toLowerCase()+"@"+department+"."+companySuffix;
    }

    private String setDepartment()
    {
        System.out.println("New Employee: "+firstName+" "+lastName+"\nDEPARTMENT CODES:\n1 for Sales\n2 for Development\n3 for Accounting\n0 for none\nEnter Department Code:");
        Scanner sc=new Scanner(System.in);
        int deptChoice=sc.nextInt();
        if(deptChoice==1)
        {
            return "Sales";
        }
        else if(deptChoice==2)
        {
            return "Development";
        }
        else if(deptChoice==3)
        {
            return "Accounting";
        }
        else
        {
            return "";
        }
    }
    private String randomPassword(int length)
    {
        String passwordSet="ABCDEFGHIJKLMNOPQRSTUVXYZ0123456789!@#$&";
        char[] password=new char[length];
        for(int i=0;i<length;i++)
        {
            int rand=(int) (Math.random()* passwordSet.length());
            password[i]=passwordSet.charAt(rand);
        }
        return new String(password);
    }
    public void setMailboxCapacity(int capacity)
    {
        this.mailboxCapacity=capacity;
    }
    public void setAlternateEmail(String altEmail)
    {
        this.alternateEmail=altEmail;
    }
    public void changePassword(String password)
    {
        this.password=password;
    }

    public int getMailboxCapacity()
    {
        return mailboxCapacity;
    }
    public String getAlternateEmail()
    {
        return alternateEmail;
    }
    public String getPassword()
    {
        return password;
    }

    public void saveToDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO employees (first_name, last_name, department, email, password, mailbox_capacity) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, firstName);
                statement.setString(2, lastName);
                statement.setString(3, department);
                statement.setString(4, email);
                statement.setString(5, password);
                statement.setInt(6, mailboxCapacity);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Employee data inserted successfully!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database connection failed!");
        }
    }

    public String showInfo()
    {
        return "DISPLAY NAME: " +firstName+" "+lastName+
                "\nCOMPANY EMAIL: "+email+
                "\nMAILBOX CAPACITY: "+mailboxCapacity+"mb";
    }
}
