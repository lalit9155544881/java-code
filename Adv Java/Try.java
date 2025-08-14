import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

class InsertData1 implements ActionListener {
    JPanel insertPanel = new JPanel();
    JFrame insertFrame = new JFrame();
    static JLabel[] insertLabel;
    static JTextField[]  data;
    static int cCount;
    Statement sta;
    ResultSetMetaData rm=null;
    JButton insertbtn = new JButton("Go");
    InsertData1() {
        insertPanel.setBounds(0,40,700,500);
        insertPanel.setBackground(Color.YELLOW);
        insertPanel.setLayout(new GridLayout(InsertData1.cCount,1));

        insertFrame.setSize(700,600);
        insertFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        insertFrame.setVisible(true);
        insertFrame.setLayout(null);
        insertFrame.add(insertPanel);
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "database", "3435");
            sta = conn.createStatement();
            boolean b = sta.execute("select * from table1");
            ResultSet res = sta.getResultSet();
            rm = res.getMetaData();
            cCount = rm.getColumnCount();
            insertLabel = new JLabel[rm.getColumnCount() + 1];
            data = new JTextField[rm.getColumnCount() + 1];
            for (int i = 1; i <= rm.getColumnCount(); i++) {
                insertLabel[i] = new JLabel();
                data[i] = new JTextField(10);
                insertLabel[i].setText("    "+rm.getColumnName(i));
                insertLabel[i].setFont(new Font("Mv Boli",Font.BOLD,18));
                insertLabel[i].setVerticalAlignment(SwingConstants.TOP);
                insertPanel.add(insertLabel[i]);

                data[i].setBounds(400,0,200,30);
                insertLabel[i].add(data[i]);
            }
            insertbtn.setBounds(800,0,150,30);
            insertbtn.addActionListener(this);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == insertbtn)
        {
            String[] dataarr = new String[cCount + 1];
            String insertQuery = null;
            String qu = "";
            dataarr[0] = "";
            for(int i = 1; i <= cCount; i++)
            {
                dataarr[i] = data[i].getText().toString();
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
                insertQuery = "insert into empdata (" + qu + ")" + " values" + " " + "(" + dataobj + ")";
            }
            System.out.println(insertQuery);
            try
            {
                sta.executeUpdate(insertQuery);
                System.out.println("Data Inserted");
            }
            catch (Exception err)
            {
                System.out.println(err);
            }
        }
    }

    public static void main(String[] args) {
        new InsertData1();
    }
}
