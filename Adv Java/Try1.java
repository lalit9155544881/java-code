import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Try1 {
    Scanner sc = new Scanner(System.in);
    Connection con;
    Statement st;
    Try1()
    {
        try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","database","3435");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    public void insertData1()
    {
        System.out.println("---------------------------------------------------------------------------------------");
        ArrayList<String> dataArr = new ArrayList<>();
        ResultSetMetaData rm;
        System.out.print("Select  Table: ");
        String tableName = sc.nextLine();

        String que = "";
        try {
            st = con.createStatement();
            st.executeQuery("select * from " + tableName);
            ResultSet res = st.getResultSet();
            rm = res.getMetaData();
            System.out.println("---------------------------------------------------------------------------------------");
            for(int i = 1; i  <= rm.getColumnCount(); i++)
            {
                //System.out.println(i + ". " + rm.getColumnName(i));
                if(i < rm.getColumnCount())
                {
                    que = que + rm.getColumnName(i) + ",";
                }
                else
                {
                    que = que + rm.getColumnName(i);
                }
            }
            dataArr.add(0,"");
            String dataStr = "";
            for(int i = 1; i <= rm.getColumnCount(); i++)
            {
                System.out.print(rm.getColumnName(i) + ": ");
                dataArr.add(i,sc.nextLine());
                System.out.println("---------------------------------------------------------------------------------------");
            }

            for(int i = 1; i <= rm.getColumnCount(); i++)
            {
                if(i == rm.getColumnCount())
                {
                    if(rm.getColumnTypeName(i) == "NUMBER")
                    {
                        dataStr = dataStr + dataArr.get(i);
                    }
                    else
                    {
                        dataStr = dataStr + "'" + dataArr.get(i) + "'";
                    }
                }
                else
                {
                    if(rm.getColumnTypeName(i) == "NUMBER")
                    {
                        dataStr = dataStr + dataArr.get(i) + ",";
                    }
                    else
                    {
                        dataStr = dataStr + "'" + dataArr.get(i) + "'" + ",";
                    }
                }
            }

            Object objData = (Object)dataStr.toUpperCase();
            String finalData = "insert into " + tableName + "(" + que + ")" + " " +  "values" + " " + "(" + objData + ")";
            /*System.out.println(finalData);
            st.executeQuery(finalData);
            System.out.println("Data Inserted");*/
            System.out.println("---------------------------------------------------------------------------------------");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
        Try1 c2 = new Try1();
        c2.insertData1();
    }
}
