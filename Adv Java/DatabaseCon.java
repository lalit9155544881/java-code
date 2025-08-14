import java.sql.*;

public class DatabaseCon {
    public static void main(String[] args) {
        try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","database","3435");
            Statement statement = con.createStatement();
            boolean b = statement.execute("select * from empdata");
            ResultSet res = statement.getResultSet();
            while(res.next())
            {
                System.out.println(res.getInt(1) + "\t" + res.getString(2) + "\t" + res.getInt(3));
            }
            ResultSetMetaData resultSetMetaData = res.getMetaData();
            System.out.println(resultSetMetaData.getColumnCount());
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }
}
