import java.sql.*;

public class DB {
    public static void main(String[] args) {
        try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","database","3435");
            Statement st = con.createStatement();
            st.executeQuery("select * from employee");
            ResultSet res = st.getResultSet();
            ResultSetMetaData rm = res.getMetaData();
            for(int i = 1; i <= rm.getColumnCount(); i++)
            {
                System.out.println(rm.getSchemaName(i));
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
