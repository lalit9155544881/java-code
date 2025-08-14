import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DBMGUI implements ActionListener {
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JButton[] optbtn = new JButton[9];
    DBMGUI()
    {
        for(int i = 0; i < 9; i++)
        {
            optbtn[i] = new JButton();
            optbtn[i].setText("J");
            panel.add(optbtn[i]);
            optbtn[i].setFont(new Font("Mv Boli",Font.BOLD,15));
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

        panel.setBounds(0,0,700,150);
        panel.setBackground(Color.gray);
        panel.setLayout(new GridLayout(3,3));

        frame.setSize(715,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setResizable(true);
        frame.add(panel);
        frame.add(showTablee.showTablePanel);
    }
    public static void main(String[] args) {
        new DBMGUI();
        new showTablee();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

class showTablee
{
    static JPanel showTablePanel = new JPanel();
    ResultSetMetaData rm;
    ResultSet res;
    int cCount;
    showTablee()
    {
        try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","database","3435");
            Statement st = con.createStatement();
            st.executeQuery("select * from employee");
            res = st.getResultSet();
            rm = res.getMetaData();
            cCount = rm.getColumnCount();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        JLabel[] tables = new JLabel[cCount + 1];
        JLabel[] tableData = new JLabel[cCount + 1];

        for(int i = 1; i <= cCount; i++)
        {
            tables[i] = new JLabel();
            tableData[i] = new JLabel();
            try
            {
                tables[i].setText(rm.getColumnName(i));
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
            showTablePanel.add(tables[i]);
            showTablePanel.add(tableData[i]);
        }
        try
        {
            while (res.next())
            {
                for(int i = 1; i <= cCount; i++)
                {
                    tableData[i].setText((String) res.getObject(i));
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        showTablePanel.setBounds(0,150,700,450);
        showTablePanel.setBackground(Color.CYAN);
        showTablePanel.setLayout(new GridLayout(1,3));

    }

}
