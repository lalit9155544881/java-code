import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DatabaseCon4 {
    public static void main(String[] args) {
        JLabel label = new JLabel();
        try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","database","3435");
            Statement ste = con.createStatement();
            //ResultSet resIn = ste.executeQuery("insert into empdata (id, name, salary) values (105,'Sidra',9999999)");
            //System.out.println("Table Inserted");
            boolean b = ste.execute("select * from empdata");
            ResultSet res = ste.getResultSet();
            ResultSetMetaData rm = res.getMetaData();
            while(res.next())
            {
                System.out.println();
                for(int i = 1; i <= rm.getColumnCount(); i++)
                {
                    System.out.print(res.getObject(i) + "\t");
                    label.setText(String.valueOf(res.getObject(i)));
                }
            }
            JFrame frame =new JFrame();
            JPanel panel = new JPanel();
            JButton btn = new JButton("Go");

            frame.setSize(500,500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(null);
            frame.setVisible(true);
            frame.add(panel);

            panel.setBounds(0,0,500,500);
            panel.setBackground(Color.ORANGE);
            panel.setLayout(null);


            panel.add(label);
            panel.add(btn);
            label.setBounds(0,0,500,300);
            label.setBackground(Color.gray);
            label.setOpaque(true);

            btn.setBounds(200,400,50,50);



        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }
}
