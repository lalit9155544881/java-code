import java.sql.*;

public class GetTablesNames
{
    public static void main(String[] args) {
        try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","database","3435");
            Statement st = con.createStatement();
            st.executeQuery("select * from user_tables");
            ResultSet res = st.getResultSet();
            int i = 1;
            while (res.next())
            {
                System.out.println(i + ". "+ res.getString(1));
                i++;

            }

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}