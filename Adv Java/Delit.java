import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Delit
{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter New Table Name: ");
        String tableName = sc.nextLine();
        ArrayList<String> col = new ArrayList<>();
        ArrayList<String> datatype = new ArrayList<>();
        ArrayList<String> len = new ArrayList<>();

        boolean b = false;
        int times = 0;
        while (b == false)
        {
            System.out.println("Column Name: ");
            col.add(sc.next());
            System.out.println("Data Type ");
            datatype.add(sc.next());
            System.out.println("Length");
            len.add(sc.next());
            System.out.println("Insert More..?");
            if(sc.nextInt() == 0)
            {
                b = true;
            }
            times++;

        }

        Iterator it1 = col.iterator();
        Iterator it2 = datatype.iterator();
        Iterator it3 = len.iterator();
        String c = "";
        int times2 = 1;
        while (it1.hasNext())
        {
            if(times2 < times)
            {
                c = c +  it1.next()  + " " + it2.next() + "("+  it3.next() + ")" + ",";
                times2++;
            }
            else
            {
                c = c +  it1.next()  + " " + it2.next() + "("+  it3.next() + ")";
            }
        }
        String finalQueary = "create table " + tableName + " " + "(" +  c + ")";

        System.out.println(finalQueary);
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","database","3435");
            Statement st = con.createStatement();
            st.executeQuery(finalQueary);
            System.out.println("Table Created");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }
}