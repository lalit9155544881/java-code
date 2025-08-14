import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class InsertDataa implements ActionListener {
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    static JLabel dataLabel;
    static JTextField[]  dataField;
    static JLabel[] label;
    JPanel btnPanel = new JPanel();
    JButton insertBtn = new JButton("Insert Data");
    Connection conn;
    Statement st;
    ResultSetMetaData rm;
    int cCount;
    InsertDataa()
    {
        insertBtn.addActionListener(this);
        btnPanel.setBackground(Color.PINK);
        btnPanel.add(insertBtn);


        panel.setBounds(0, 0, 600, 400);
        panel.setLayout(new GridLayout(cCount,1));
        panel.setBackground(Color.lightGray);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 600, 400);
        //frame.setResizable(false);
        frame.setLayout(new GridLayout(2,1));
        frame.setVisible(true);
        frame.add(panel);
        frame.add(btnPanel);

        try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "database", "3435");
            st = conn.createStatement();
            st.executeQuery("select * from student");
            ResultSet res = st.getResultSet();
            rm = res.getMetaData();
            cCount = rm.getColumnCount();
            label = new JLabel[cCount + 1];
            dataField = new JTextField[cCount + 1];
            for(int i = 1; i <= cCount; i++)
            {
                label[i] = new JLabel();
                label[i].setText("       " + rm.getColumnName(i));
                label[i].setVerticalAlignment(SwingConstants.TOP);
                dataField[i] = new JTextField(10);
                dataField[i].setBounds(300,0,200,30);
                label[i].add(dataField[i]);
                panel.add(label[i]);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }
    public static void main(String[] args) {
        new InsertDataa();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       if(e.getSource() == insertBtn)
       {
           String[] dataarr = new String[cCount + 1];
           String insertQuery = null;
           String qu = "";
           dataarr[0] = "";
           for(int i = 1; i <= cCount; i++)
           {
               dataarr[i] = dataField[i].getText().toString().toUpperCase();
               try {
                   if(i == rm.getColumnCount())
                   {
                       qu = qu + rm.getColumnName(i);
                   }
                   else {
                       qu = qu + rm.getColumnName(i) + ",";
                   }
               }
               catch (Exception err)
               {
                   System.out.println(err);
               }
           }
           String columntype = "";

           String d = "";
           for(int i = 1; i <= cCount; i++)
           {
               try
               {
                   columntype = rm.getColumnTypeName(i);
               }
               catch (Exception err)
               {
                   System.out.println(err);
               }
               if(i == cCount)
               {
                   d = d + "'" + dataarr[i] + "'";
               }
               else if(columntype == "NUMBER")
               {
                   d = d + dataarr[i] + ",";
               }
               else
               {
                   d = d + "'" + dataarr[i] + "'" +  ",";
               }
               Object dataobj = (Object)d;
               insertQuery = "insert into student (" + qu + ")" + " values" + " " + "(" + dataobj + ")";
               dataField[i].setText("");
           }
           System.out.println(insertQuery);
           try
           {
               st.executeUpdate(insertQuery);
               System.out.println("Data Inserted");
           }
           catch (Exception err)
           {
               System.out.println(err);
           }
       }
    }
}
