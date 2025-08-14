import java.sql.*;
import java.util.Scanner;

class Data {
    Scanner sc = new Scanner(System.in);
    static Connection conn;
    static Statement st = null;
    static int choice;

    Data() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/oracle", "root", "");

        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void CreateTable() {
        System.out.println("Enter Table Name You Wanna Create:-");
        try {

            st = conn.createStatement();
            String a = "create table (Id int(20) primary key auto_increment, Name varchar(30) not null, Address varchar(200) not null, Contactno int(10) not null)" + sc.next();
            st.execute(a);

            System.out.println("Table created successfully");
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void SelectFromTable() {
        ShowAllTable();
        System.out.println("Enter Table Name:-");
        try (Scanner sc = new Scanner(System.in)) {
            try {
                st = conn.createStatement();
                ResultSet rst = st.executeQuery("select*from" + sc.next());
                while (rst.next()) {
                    System.out.println(rst.getInt(1) + "\t" + rst.getString(2) + "\t" + rst.getString(3) + "\t" + rst.getInt(4));

                }

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void InsertIntoTable() {

    }

    public void UpdateRecord() {

    }

    public void DeleteFromTable() {
        ShowAllTable();
        try (Scanner adi = new Scanner(System.in)) {
            System.out.println("Enter Table Name You Wanna Delete:-");
            try {
                st = conn.createStatement();
                st.execute("DROP table " + adi.next());
                st.getResultSet();
                System.out.println("Table Deleted Successfully-------");

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void TruncateFromTable() {

    }

    public void DropTable() {

    }

    public void AlterTable() {

    }

    public void ShowAllTable() {
        try {

            st = conn.createStatement();
            st.executeQuery("SHOW tables");
            ResultSet res = st.getResultSet();
            System.out.println("-----------------------------------------------------------------------------");
            System.out.println("You Have Following Table--");
            while (res.next()) {
                System.out.println(res.getString(1) + "     ");
            }
            System.out.println();
            System.out.println("-----------------------------------------------------------------------------");

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

public class FirstAssig {
    public static void main(String[] args) {
        Data x1 = new Data();
        try (Scanner adi = new Scanner(System.in)) {
            System.out.println("----------------------------------------------------------------------------------------------------------------------");
            System.out.println("\t\t\t\t\t\t----------Menu-----------");
            System.out.println("----------------------------------------------------------------------------------------------------------------------\n");

            System.out.print("1.Create Table\t\t\t");
            System.out.print("2.Select From Table\t");
            System.out.println("3.Insert Into Table\t");
            System.out.print("4.Update Record From Table\t");
            System.out.print("5.Delete From Table\t");
            System.out.println("6.Truncate From Table\t");
            System.out.print("7.Drop Table\t\t\t");
            System.out.print("8.Alter Table\t\t");
            System.out.println("9.Show All Table\t");
            System.out.println("10.Exit");

            System.out.println("----------------------------------------------------------------------------------------------------------------------");
            System.out.println("\t\t\t\t\tEnter Your Choice-");
            System.out.println("----------------------------------------------------------------------------------------------------------------------");

            int choice = adi.nextInt();

            switch (choice) {
                case 1: {
                    x1.CreateTable();
                    break;
                }
                case 2: {
                    x1.SelectFromTable();
                    break;
                }
                case 3: {
                    x1.InsertIntoTable();
                    break;
                }

                case 4: {
                    x1.UpdateRecord();
                    break;
                }

                case 5: {
                    x1.DeleteFromTable();
                    break;
                }
                case 6: {
                    x1.TruncateFromTable();
                    break;
                }
                case 7: {
                    x1.DropTable();
                    break;
                }
                case 8: {
                    x1.AlterTable();
                    break;
                }
                case 9: {
                    x1.ShowAllTable();
                    break;
                }
                case 10: {
                    System.exit(1);
                    break;
                }
                default: {
                    System.out.println("----------------------------------------------------------------------------------------------------------------------");
                    System.out.println("Glt Hai Bro Fir Se Choose Kro :(");
                }
            }
        }
    }
}
