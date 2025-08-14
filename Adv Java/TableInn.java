import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TableInn extends JFrame {
    private JPanel contentPane;
    private JTextField[] inputFields;
    private String[] columnNames = {"ID", "First Name", "Last Name", "Email"};

    Connection conn;
    Statement st;
    ResultSetMetaData rm;
    int cCount = 0;

    public TableInn() throws SQLException {
        try
        {
            st = conn.createStatement();
            st.executeQuery("select * from manish");
            ResultSet res = st.getResultSet();
            rm = res.getMetaData();
            cCount = rm.getColumnCount();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 150);
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        // Add input fields
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(0, 2));
        contentPane.add(inputPanel, BorderLayout.CENTER);

        inputFields = new JTextField[cCount];
        for (int i = 0; i < cCount; i++) {
            JLabel label = new JLabel(rm.getColumnName(i) + ":");
            inputPanel.add(label);
            inputFields[i] = new JTextField();
            inputPanel.add(inputFields[i]);
        }

        // Add submit button
        JPanel buttonPanel = new JPanel();
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get data from input fields
                String[] data = new String[cCount];
                for (int i = 0; i < cCount; i++) {
                    data[i] = inputFields[i].getText();
                }
                try {

                    Class.forName("oracle.jdbc.OracleDriver");
                    conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","database","3435");

                    PreparedStatement stmt = conn.prepareStatement("INSERT INTO mytable (id, firstName, lastName, email) VALUES (?, ?, ?, ?)");
                    for (int i = 0; i < cCount; i++) {
                        stmt.setString(i + 1, data[i]);
                    }
                    stmt.executeUpdate();


                    // Clear input fields
                    for (int i = 0; i < cCount; i++) {
                        inputFields[i].setText("");
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });
        buttonPanel.add(submitButton);
    }

    public static void main(String[] args) {
        TableInn frame = null;
        try {
            frame = new TableInn();
        } catch (Exception err) {
            System.out.println(err);
        }
        frame.setVisible(true);
    }
}
