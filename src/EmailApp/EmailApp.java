package EmailApp;
import java.util.*;

public class EmailApp {
    public static void main(String[] args)
    {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter your First Name: ");
        String fname=sc.next();
        System.out.println("Enter your Last Name: ");
        String lname=sc.next();

        Email em1 = new Email(fname,lname);
        System.out.println(em1.showInfo());
        em1.saveToDatabase();
    }
}
