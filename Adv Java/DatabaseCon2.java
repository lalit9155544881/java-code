import java.sql.*;
import java.util.Scanner;

public class DatabaseCon2 {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.print("Enter Name Of Table: ");
        try
        {
            //Class.forName("oracle.jdbc.OracleDriver");
            Class.forName("com.mysql.jdbc.Driver");
            //Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","database","3435");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yamlok","root","");
            Statement sta = conn.createStatement();
            boolean b = sta.execute("select * from " + sc.next());
            ResultSet res = sta.getResultSet();
            ResultSetMetaData rsmd = res.getMetaData();
            for(int i = 1; i <= rsmd.getColumnCount(); i++)
            {
                System.out.print(rsmd.getColumnName(i) + "\t");
            }
            while (res.next())
            {
                System.out.println();
                for(int i = 1; i <= rsmd.getColumnCount(); i++)
                {
                    System.out.print(res.getObject(i) + "\t");
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }
}
