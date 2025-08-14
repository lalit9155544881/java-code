import java.sql.*;

public class Cheak {
    public static void main(String[] args) {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "database", "3435");
            Statement ste = con.createStatement();
            boolean b = ste.execute("select * from empdata");
            ResultSet res = ste.getResultSet();
            ResultSetMetaData rm = res.getMetaData();
            System.out.println(rm.getColumnTypeName(2));
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
