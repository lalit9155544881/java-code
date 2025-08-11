import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

class Info
{
    Scanner sc = new Scanner(System.in);
    static String userName;
    static String passWord;
    static Connection con;
    static Statement st = null;
    static int choice;
    static boolean loginSucc = false;
    Info()
    {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",userName,passWord);
            loginSucc = true;
        }catch (Exception e)
        {
            System.out.println(e);
        }
    }
    public void showTable()
    {
        try
        {
            st = con.createStatement();
            st.executeQuery("select * from user_tables");
            ResultSet showTableres = st.getResultSet();
            System.out.println("---------------------------------------------------------------------------------------");
            System.out.println("You Have Following Tables: ");
            while (showTableres.next())
            {
                System.out.print(showTableres.getString(1) + "       ");
            }
            System.out.println();
            System.out.println("---------------------------------------------------------------------------------------");
        }
        catch (Exception e)
        {
            System.out.println("Error" + " " + e);
        }
    }
    static String tableName;
    public void createTable(){
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.print("Enter New Table Name: ");
        tableName = sc.next();
        System.out.println("Press 1. For Command Line \t" + "\t Press 2. With GUI");
        int ch = sc.nextInt();
        switch (ch)
        {
            case 1:
            {
                ArrayList<String> col = new ArrayList<>();
                ArrayList<String> datatype = new ArrayList<>();
                ArrayList<String> len = new ArrayList<>();
                boolean ext = false;
                try
                {
                    st = con.createStatement();
                    st.executeQuery("select * from user_tables");
                    ResultSet res = st.getResultSet();
                    ArrayList<String> tabs= new ArrayList<String>();
                    while (res.next())
                    {
                        tabs.add(res.getString(1));
                    }
                    for(int i = 0; i < tabs.size(); i++)
                    {
                        if(tabs.get(i).equals(tableName.toUpperCase()))
                        {
                            ext = true;
                        }
                    }
                }
                catch (Exception e)
                {
                    System.out.println(e);
                }
                if(ext == false)
                {
                    boolean conti = true;
                    int times = 0;
                    while (conti)
                    {
                        System.out.println("---------------------------------------------------------------------------------------");
                        System.out.print("Column Name: ");
                        col.add(sc.next());
                        System.out.print("Data Type: ");
                        datatype.add(sc.next());
                        System.out.print("Length: ");
                        len.add(sc.next());
                        System.out.println("---------------------------------------------------------------------------------------");
                        System.out.println("Stop Inserting ? Press (0) else Press (1)");
                        if(sc.nextInt() == 0)
                        {
                            conti = false;
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
                    String finalQ = "create table " + tableName + " " + "(" +  c + ")";
                    //String finalQ = "create table emppp (num number(2),name varchar2(10),id number(10))";

                    System.out.println(finalQ);
                    try {
                        st = con.createStatement();
                        st.executeQuery(finalQ);
                        System.out.println("Table Created");
                    }
                    catch (Exception e)
                    {
                        System.out.println(e);
                    }
                    System.out.println("---------------------------------------------------------------------------------------");
                }
                else
                {
                    System.out.println("---------------------------------------------------------------------------------------");
                    System.out.println("Table Name Already Exists :(");
                    System.out.println("---------------------------------------------------------------------------------------");
                }
                break;
            }
            case 2:
            {
                new CreateTableGUI();
                break;
            }
        }

    }
    static String insertDatatableName;
    public void insertData() throws Exception
    {
        showTable();
        //System.out.println("---------------------------------------------------------------------------------------");
        System.out.print("Select  Table: ");
        insertDatatableName = sc.next();
        System.out.println("Press 1. For Command Line \t" + "\t Press 2. With GUI");
        int chh = sc.nextInt();
        switch (chh)
        {
            case 1:
            {
                ArrayList<String> dataArr = new ArrayList<>();
                ResultSetMetaData rm;

                String que = "";
                try {
                    st = con.createStatement();
                    st.executeQuery("select * from " + insertDatatableName);
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
                        dataArr.add(i,sc.next());
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
                    String finalData = "insert into " + insertDatatableName + "(" + que + ")" + " " +  "values" + " " + "(" + objData + ")";
                    System.out.println(finalData);
                    st.executeQuery(finalData);
                    System.out.println("Data Inserted");
                    System.out.println("---------------------------------------------------------------------------------------");
                }
                catch (Exception e)
                {
                    System.out.println(e);
                }
                break;
            }
            case 2:
            {
                new InsertDataGUI();
                break;
            }
            default:
            {
                System.out.println("Wrong Input");
            }
        }
    }

    public void selectFromTable()
    {
        showTable();
        System.out.print("Select Table: ");
        String selTab = sc.next();
        try {
            st = con.createStatement();
            st.executeQuery("select * from " + selTab);
            ResultSet res = st.getResultSet();
            ResultSetMetaData rm = res.getMetaData();
            System.out.println("---------------------------------------------------------------------------------------");
            System.out.println("You Have Following Columns: ");
            for (int i = 1; i <= rm.getColumnCount(); i++) {
                System.out.print(rm.getColumnName(i) + "\t");
            }
            System.out.println();
            System.out.println("---------------------------------------------------------------------------------------");
            System.out.print("Select Column Name: ");
            String selCol = sc.next();
            String newQue = "select " + selCol + " from " + selTab;
            st.executeQuery(newQue);
            ResultSet ress = st.getResultSet();
            System.out.println("---------------------------------------------------------------------------------------");
            System.out.println("> \t" + selCol.toUpperCase());
            while(ress.next())
            {
                for(int i = 1; i <= rm.getColumnCount(); i++) {
                    System.out.println("* " + ress.getObject(i));
                }
            }
            System.out.println("---------------------------------------------------------------------------------------");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public void updateTable()
    {
        showTable();
        System.out.print("Select Table: ");
        String selTab = sc.next();
        try {
            st = con.createStatement();
            st.executeQuery("select * from " + selTab);
            ResultSet res1 = st.getResultSet();
            ResultSetMetaData rm = res1.getMetaData();
            System.out.println("---------------------------------------------------------------------------------------");
            for (int i = 1; i <= rm.getColumnCount(); i++)
            {
                System.out.print(rm.getColumnName(i) + "\t");
            }
            System.out.println();
            System.out.print("Select column: ");
            String selCol = sc.next();
            int colIndex = 0;
            for (int i = 1; i <= rm.getColumnCount(); i++)
            {
                if(rm.getColumnName(i).equals(selCol.toUpperCase()))
                {
                    colIndex = i;
                    //System.out.println(rm.getColumnTypeName(colIndex));
                }
            }

            System.out.print("Select Condition Column: ");
            String conCol  = sc.next();
            int conColIndex = 0;
            for (int i = 1; i <= rm.getColumnCount(); i++)
            {
                if(rm.getColumnName(i).equals(conCol.toUpperCase()))
                {
                    conColIndex = i;
                }
            }
            System.out.print("Where  " + conCol + "(Condition)" + " = " );
            String condition = sc.next().toUpperCase();
            Object conditionObj = (Object)condition;
            System.out.print("New Value: ");
            String newVal = sc.next();
            Object newValObj = (Object)newVal;
            String updateQue = "";
            if(rm.getColumnTypeName(colIndex) == "VARCHAR2")
            {
                updateQue = "update " + selTab + " set " + selCol + " = " + "'" + newValObj + "'" + " where " + conCol + " = " + "'" + condition  + "'";
            }
            else {
                if (rm.getColumnTypeName(conColIndex) == "VARCHAR2") {
                    updateQue = "update " + selTab + " set " + selCol + " = " + newValObj + " where " + conCol + " = " + "'" +  condition + "'";
                }
                else
                {
                    updateQue = "update " + selTab + " set " + selCol + " = " + newValObj + " where " + conCol + " = " + condition;
                }
            }
            //String quer = "select " + selCol + " from " + selTab + " where " + selCol + " = " + conditionObj;
            System.out.println("---------------------------------------------------------------------------------------");
            System.out.println(updateQue);
            st.executeQuery(updateQue);
            System.out.println("Data Updated");
            System.out.println("---------------------------------------------------------------------------------------");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    static String showTableDataTabName;
    public void showTableData()
    {
        showTable();
        System.out.print("Select Table: ");
        showTableDataTabName = sc.next();
        System.out.println("Press 1. Command Line" + "\t" + "Press 2. With GUI");
        int chh = sc.nextInt();
        switch (chh)
        {
            case 1:
            {
                System.out.println("---------------------------------------------------------------------------------------");
                try
                {
                    st = con.createStatement();
                    st.executeQuery("select * from " + showTableDataTabName);
                    ResultSet res = st.getResultSet();
                    ResultSetMetaData rm = res.getMetaData();
                    for(int i = 1; i <= rm.getColumnCount(); i++)
                    {
                        System.out.print(rm.getColumnName(i) + "\t");
                    }
                    System.out.println();
                    while (res.next())
                    {
                        for(int i = 1; i <= rm.getColumnCount(); i++) {
                            System.out.print(res.getObject(i) + "\t");
                        }
                        System.out.println();
                    }
                    System.out.println("---------------------------------------------------------------------------------------");
                }
                catch (Exception e)
                {
                    System.out.println(e);
                }
                break;
            }
            case 2:
            {
                TableGUI gui = new TableGUI();
                gui.setVisible(true);
                break;
            }
            default:
            {
                System.out.println("Wrong Choice");
            }
        }
    }

    public void deleteRowFromTable()
    {
        showTable();
        System.out.print("Select Table: ");
        String selTab = sc.next();
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.print("Select Condition Column: ");
        String conCol = sc.next();
        System.out.print("Where " + conCol+ "(Condition)" + " = ");
        String conVal = sc.next().toUpperCase();
        System.out.println("---------------------------------------------------------------------------------------");
        String delQue;
        try
        {
            st = con.createStatement();
            st.executeQuery("select * from " + selTab);
            ResultSet res = st.getResultSet();
            ResultSetMetaData rm = res.getMetaData();
            int conColIndex = 0;
            for(int i = 1; i <= rm.getColumnCount(); i++)
            {
                if(rm.getColumnName(i).equals(conCol.toUpperCase()))
                {
                    conColIndex = i;
                }
            }
            if(rm.getColumnTypeName(conColIndex) == "VARCHAR2")
            {
                delQue = "delete from " + selTab + " where " + conCol + " = " + "'" +conVal + "'";
            }
            else
            {
                delQue = "delete from " + selTab + " where " + conCol + " = " + conVal;
            }
            st.executeQuery(delQue);
            System.out.println("Row Deleted :)");
            System.out.println("---------------------------------------------------------------------------------------");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public void dropTable()
    {
        showTable();
        System.out.print("Select Table You Wanna Drop: ");
        String dropTableName = sc.next();
        try
        {
            st = con.createStatement();
            st.executeQuery("drop table " + dropTableName);
            System.out.println("Table " + dropTableName + " Droped :)");
            System.out.println("---------------------------------------------------------------------------------------");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public void alterTable()
    {
        System.out.println("1. Add Column" + "         " + "2. Drop Column");
        System.out.print("Select Above: ");
        int ch = sc.nextInt();
        switch (ch)
        {
            case 1:
            {
                showTable();
                System.out.print("Select Table: ");
                String selTab = sc.next();
                System.out.print("Enter New Column Name: ");
                String newCol = sc.next();
                System.out.print("DataType: ");
                String dataType = sc.next();
                System.out.print("Length: ");
                String len = sc.next();
                System.out.println("---------------------------------------------------------------------------------------");
                String addQue = "alter table " + selTab + " add " + newCol + " " + dataType + "("+len+")";
                try
                {
                    st = con.createStatement();
                    st.executeQuery(addQue);
                    System.out.println("---------------------------------------------------------------------------------------");
                    System.out.println("Column Added");
                    System.out.println("---------------------------------------------------------------------------------------");
                }
                catch (Exception e)
                {
                    System.out.println(e);
                }
                //System.out.println(addQue);
                break;
            }
            case 2:
            {
                showTable();
                System.out.print("Select Table: ");
                String selTable = sc.next();
                System.out.println("---------------------------------------------------------------------------------------");
                try {
                    st = con.createStatement();
                    st.executeQuery("select * from " + selTable);
                    ResultSet res = st.getResultSet();
                    ResultSetMetaData rm = res.getMetaData();
                    System.out.println("You Have Following Column: ");
                    for(int i = 1; i <= rm.getColumnCount(); i++)
                    {
                        System.out.print(rm.getColumnName(i) + "\t");
                    }
                    System.out.println();
                    System.out.print("Select Column You Wanna Drop: ");
                    String dropCol = sc.next();
                    String dropQue = "alter table " + selTable + " drop column " + dropCol;

                    st.executeQuery(dropQue);
                    System.out.println("---------------------------------------------------------------------------------------");
                    System.out.println("Column Dropped");
                    System.out.println("---------------------------------------------------------------------------------------");
                }
                catch (Exception e)
                {
                    System.out.println(e);
                }
                break;
            }
            default:
            {
                System.out.println("Wrong Choice");
            }
        }
    }
    public void insertInColumn()
    {
        showTable();
        System.out.print("Select Table: ");
        String selTab = sc.next();
        try
        {
            st = con.createStatement();
            st.executeQuery("select * from " + selTab);
            ResultSet res = st.getResultSet();
            ResultSetMetaData rm = res.getMetaData();
            System.out.println("Following Columns: ");
            int selColIndex = 0;
            for(int i = 1; i <= rm.getColumnCount(); i++)
            {
                System.out.print(rm.getColumnName(i) + "\t");
            }
            System.out.println();
            System.out.print("Select Column: ");
            String selCol = sc.next();
            System.out.println("---------------------------------------------------------------------------------------");
            for(int i = 1; i <= rm.getColumnCount(); i++)
            {
                if(rm.getColumnName(i).equals(selCol.toUpperCase()))
                {
                    selColIndex = i;
                }
            }
            System.out.print("Insert Value: ");
            String insValue = sc.next();
            String fQue;
            if(rm.getColumnTypeName(selColIndex) == "VARCHAR2")
            {
                fQue = "insert into " + selTab + " (" + selCol + ")" + " values " + "(" + "'" + insValue+ "'" + ")";
            }
            else
            {
                fQue = "insert into " + selTab + "(" + selCol + ")" + " values " + "(" + insValue + ")";
            }
            //System.out.println(fQue);
            st.executeQuery(fQue);
            System.out.println("Data Inserted");
            System.out.println("---------------------------------------------------------------------------------------");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
public class Assignment
{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Your USERNAME: ");
        Info.userName = sc.next();
        System.out.print("Enter Your PASSWORD: ");
        Info.passWord = sc.next();
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println();
        Info x1 = new Info();
        if(Info.loginSucc == true) {
            while (true) {
                System.out.println("Options: ");
                System.out.println("Press 1. Show All Table" + "         " + "Press 2. Create Table" + "          " + "Press 3. Insert Data In Table");
                System.out.println("Press 4. Insert In Column" + "       " + "Press 5. Select From Table" + "     " + "Press 6. Update Table Data" + "     ");
                System.out.println("Press 7. Show Table Data" + "        " + "Press 8. Delete From Table" + "     " + "Press 9. Modify Table(Alter)" + "   ");
                System.out.println("Press 10. Drop Table" + "            " + "Press 11. Exit");
                System.out.println("---------------------------------------------------------------------------------------");
                System.out.print("Choose Option: ");
                int ch = sc.nextInt();
                switch (ch) {
                    case 1: {
                        x1.showTable();
                        break;
                    }
                    case 2: {
                        x1.createTable();
                        break;
                    }
                    case 3: {
                        try
                        {
                            x1.insertData();
                        }
                        catch (Exception e)
                        {
                            System.out.println(e);
                        }
                        break;
                    }
                    case 4:
                    {
                        x1.insertInColumn();
                        break;
                    }
                    case 5: {
                        x1.selectFromTable();
                        break;
                    }
                    case 6: {
                        x1.updateTable();
                        break;
                    }
                    case 7: {
                        x1.showTableData();
                        break;
                    }
                    case 8: {
                        x1.deleteRowFromTable();
                        break;
                    }
                    case 9: {
                        x1.alterTable();
                        break;
                    }
                    case 10:
                    {
                        x1.dropTable();
                        break;
                    }
                    case 11: {
                        System.exit(1);
                    }
                    default: {
                        System.out.println("---------------------------------------------------------------------------------------");
                        System.out.println("Wrong Choice");
                    }
                }
            }
        }
        else
        {
            System.out.println("****** Run Again ******");
        }

    }
}


class TableGUI extends JFrame {
    JPanel contentPane;
    TableGUI() {
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        try {
            Info.st = Info.con.createStatement();
            ResultSet rs = Info.st.executeQuery("SELECT * FROM " + Info.showTableDataTabName);

            ResultSetMetaData rm = rs.getMetaData();
            int columnCount = rm.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = rm.getColumnName(i);
            }
            Object[][] data = new Object[100][columnCount];
            int rowCount = 0;
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    data[rowCount][i - 1] = rs.getObject(i);
                }
                rowCount++;
            }
            Object[][] dataObj = new Object[rowCount][columnCount];
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < columnCount; j++) {
                    dataObj[i][j] = data[i][j];
                }
            }
            JTable table = new JTable(dataObj, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            contentPane.add(scrollPane, BorderLayout.CENTER);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}

class InsertDataGUI implements ActionListener {
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    static JLabel dataLabel;
    static JTextField[]  dataField;
    static JLabel[] label;
    JPanel btnPanel = new JPanel();
    JButton insertBtn = new JButton("Insert Data");
    ResultSetMetaData rm;

    int cCount;
    InsertDataGUI()
    {
        insertBtn.addActionListener(this);
        btnPanel.setBackground(Color.PINK);
        btnPanel.add(insertBtn);

        panel.setBounds(0, 0, 600, 400);
        panel.setLayout(new GridLayout(cCount,1));
        panel.setBackground(Color.lightGray);

        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setBounds(100, 100, 600, 400);
        //frame.setResizable(false);
        frame.setLayout(new GridLayout(2,1));
        frame.setVisible(true);
        frame.add(panel);
        frame.add(btnPanel);

        try
        {
            Info.st = Info.con.createStatement();
            Info.st.executeQuery("select * from " + Info.insertDatatableName);
            ResultSet res = Info.st.getResultSet();
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
                dataField[i].setBounds(300,0,200,25);
                label[i].add(dataField[i]);
                panel.add(label[i]);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

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
                insertQuery = "insert into  "+ Info.insertDatatableName  + "(" + qu + ")" + " values" + " " + "(" + dataobj + ")";
                dataField[i].setText("");
            }
            //System.out.println(insertQuery);
            try
            {
                Info.st.executeUpdate(insertQuery);
                //System.out.println("Data Inserted");
                insertBtn.setText("Data Inserted");
                insertBtn.setBackground(Color.green);
                insertBtn.setEnabled(false);

            }
            catch (Exception err)
            {
                System.out.println(err);
            }
        }
    }
}


class CreateTableGUI implements ActionListener {
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JPanel btnPanel = new JPanel();
    JButton getBtn = new JButton("Add Column");
    JButton execbtn = new JButton("Execute");
    //JButton stabtn = new JButton("Table Created");
    JLabel label1 = new JLabel();
    JLabel label2 = new JLabel();
    JLabel label3 = new JLabel();
    int times = 0;

    JTextField nameField = new JTextField(10);
    JTextField datatypeField = new JTextField(10);
    JTextField lenField = new JTextField(10);


    ArrayList<String> cName = new ArrayList<String>();
    ArrayList<String> datatype = new ArrayList<String>();
    ArrayList<String> len = new ArrayList<String>();

    CreateTableGUI() {

        /*stabtn.setVisible(false);
        stabtn.setBounds(95, 190, 130, 25);
        stabtn.setBackground(Color.green);*/

        execbtn.setBounds(95, 160, 130, 25);
        execbtn.addActionListener(this);


        getBtn.addActionListener(this);
        getBtn.setBounds(95, 130, 130, 25);
        btnPanel.setLayout(null);
        btnPanel.add(getBtn);
        btnPanel.add(execbtn);
        //btnPanel.add(stabtn);

        nameField.setBounds(130, 0, 150, 20);
        datatypeField.setBounds(130, 0, 150, 20);
        lenField.setBounds(130, 0, 150, 20);

        label3.setText("    " + "Length: ");
        label3.setVerticalAlignment(SwingConstants.TOP);
        label3.add(lenField);
        label2.setText("    " + "DataType: ");
        label2.setVerticalAlignment(SwingConstants.TOP);
        label2.add(datatypeField);
        label1.setText("    " + "Column Name: ");
        label1.setVerticalAlignment(SwingConstants.TOP);

        label1.add(nameField);

        panel.setBounds(0, 0, 600, 400);
        panel.setLayout(new GridLayout(3, 1));
        panel.setBackground(Color.PINK);
        panel.add(label1);
        panel.add(label2);
        panel.add(label3);

        frame.setBounds(100, 100, 600, 400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 2));
        frame.setResizable(false);
        frame.add(panel);
        frame.add(btnPanel);
    }

    public static void main(String[] args) {
        new FirstAssig();
    }

    int times2 = 1;

    @Override
    public void actionPerformed(ActionEvent e) {
		/*cName.add(0,"");
		datatype.add(0,"");
		len.add(0,"");*/
        if (e.getSource() == getBtn) {
            cName.add(nameField.getText().toString().toUpperCase());
            datatype.add(datatypeField.getText().toString().toUpperCase());
            len.add(lenField.getText().toString());
            times++;
            nameField.setText("");
            datatypeField.setText("");
            lenField.setText("");

        }
        String que = "";
        if (e.getSource() == execbtn) {
            Iterator column = cName.iterator();
            Iterator datatypeit = datatype.iterator();
            Iterator length = len.iterator();

            while (column.hasNext()) {
                if (times2 < times) {
                    que = que + column.next() + " " + datatypeit.next() + " " + "(" + length.next() + ")" + ",";
                    times2++;
                } else {
                    que = que + column.next() + " " + datatypeit.next() + " " + "(" + length.next() + ")";
                }
            }
            String fQue = "create table " + Info.tableName + " (" + que + ")";
            try
            {
                Info.st = Info.con.createStatement();
                Info.st.executeQuery(fQue);
                execbtn.setText("Table Created");
                execbtn.setBackground(Color.green);
                execbtn.setEnabled(false);
                //System.out.println("Table Created");
            }
            catch (Exception err)
            {
                System.out.println(err);
            }

            //System.out.println(fQue);
        }
    }
}
