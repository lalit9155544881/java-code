import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseCon3 {
    public static void main(String[] args) {
        try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","database","3435");
            Statement stm = con.createStatement();
            stm.executeUpdate("create table t1(name varchar2(10),id number(3),add varchar2(10))");
            System.out.println("Table Created");
            stm.executeQuery("insert into t1 (name,id,add) values ('laiba',101,'Patna')");
            System.out.println("Data Inserted");
            System.out.println("Table Created");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
