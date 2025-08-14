import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DataBaseAss implements ActionListener
{
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JButton[] optbtn = new JButton[9];
    static JPanel showTablePanel = new JPanel();
    JLabel showTablelabel = new JLabel();
    static JButton[] showTablebtn;
    DataBaseAss()
    {
        for(int i = 0; i < 9; i++)
        {
            optbtn[i] = new JButton();
            optbtn[i].setText("J");
            panel.add(optbtn[i]);
            optbtn[i].setFont(new Font("Mv Boli",Font.BOLD,18));
            optbtn[i].addActionListener(this);
        }
        optbtn[0].setText("Show All Table");
        optbtn[1].setText("Create Table");
        optbtn[2].setText("Insert Data In Table");
        optbtn[3].setText("Select From Table");
        optbtn[4].setText("Updata Table Data");
        optbtn[5].setText("Show Taable Data");
        optbtn[6].setText("Delete From Table");
        optbtn[7].setText("Drop Table ");
        optbtn[8].setText("Exit");

        panel.setBounds(35,0,700,150);
        panel.setBackground(Color.ORANGE);
        panel.setLayout(new GridLayout(4,3));

        frame.setSize(800,700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.add(panel);
        frame.add(showTablePanel);
    }

    public static void main(String[] args) {
        new DataBaseAss();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == optbtn[0])
        {
            new showTable();
            showTablePanel.setVisible(true);
        }
    }
}

class showTable
{
    JPanel showTablePanel = new JPanel();
    Connection conn;
    Statement st;
    static ResultSetMetaData rm;
    showTable()
    {
        try
        { 
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","database","3435");
            st = conn.createStatement();
            st.executeQuery("select * from employee");
            ResultSet res = st.getResultSet();
            rm = res.getMetaData();
            for(int i = 1; i <= rm.getColumnCount(); i++)
            {
                DataBaseAss.showTablebtn[i] = new JButton();
                DataBaseAss.showTablebtn[i].setText(rm.getColumnName(i));
                DataBaseAss.showTablePanel.add(DataBaseAss.showTablebtn[i]);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}