package my.project;




import my.project.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class TableView extends JFrame implements ActionListener
{
    public TableView(String tab_name) throws SQLException
    {
        ArrayList columnNames = new ArrayList();
        ArrayList data = new ArrayList();

        //  Connect to an MySQL Database, run query, get result set
       
        String sql = "SELECT * FROM "+tab_name;

        // Java SE 7 has try-with-resources
        // This will ensure that the sql objects are closed when the program 
        // is finished with them
        try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:XE","project","project") ){;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery( sql );
        {
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();

            //  Get column names
            for (int i = 1; i <= columns; i++)
            {
                columnNames.add( md.getColumnName(i) );
            }

            //  Get row data
            while (rs.next())
            {
                ArrayList row = new ArrayList(columns);

                for (int i = 1; i <= columns; i++)
                {
                    row.add( rs.getObject(i) );
                }

                data.add( row );
            }
        }
        }
        catch (SQLException e)
        {
            System.out.println( e.getMessage() );
        }

        // Create Vectors and copy over elements from ArrayLists to them
        // Vector is deprecated but I am using them in this example to keep 
        // things simple - the best practice would be to create a custom defined
        // class which inherits from the AbstractTableModel class
        Vector columnNamesVector = new Vector();
        Vector dataVector = new Vector();

        for (int i = 0; i < data.size(); i++)
        {
            ArrayList subArray = (ArrayList)data.get(i);
            Vector subVector = new Vector();
            for (int j = 0; j < subArray.size(); j++)
            {
                subVector.add(subArray.get(j));
            }
            dataVector.add(subVector);
        }

        for (int i = 0; i < columnNames.size(); i++ )
            columnNamesVector.add(columnNames.get(i));

        //  Create table with database data    
        JTable table = new JTable(dataVector, columnNamesVector)
        {
            public Class getColumnClass(int column)
            {
                for (int row = 0; row < getRowCount(); row++)
                {
                    Object o = getValueAt(row, column);

                    if (o != null)
                    {
                        return o.getClass();
                    }
                }

                return Object.class;
            }
        };

        //table.setGridColor(Color.DARK_GRAY);
        //table.setBackground(Color.cyan);
        //table.setLayout();

        JScrollPane scrollPane = new JScrollPane( table );
        getContentPane().add( scrollPane );

        JPanel buttonPanel = new JPanel();
        getContentPane().add( buttonPanel, BorderLayout.SOUTH );
        
        JButton Home=new JButton("Back");
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(Home);
        Home.addActionListener(this);
        
    }


        
        
        
        
        
    
    public void actionPerformed(java.awt.event.ActionEvent evt) {                                     
        // TODO add your handling code here:
      new View().setVisible(true);
       this.setVisible(false);
    }                               

    public static void main(String[] args) throws SQLException
    {
       
    }

    /*@Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
        ew Homepage().setVisible(true);
       this.setVisible(false);//To change body of generated methods, choose Tools | Templates.
    }*/

    
}