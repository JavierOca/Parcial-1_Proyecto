/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer;

import BusinessModelLayer.Farmacia;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author javier
 */
public class DataAccess {
    private Farmacia farmacia;
    private static DataAccess instance;
    private Connection con = null;
    private Statement statement;
    private ResultSet resultSet;

    private DataAccess() {
    }

    public static DataAccess Instance() {
        if (instance == null) {
            instance = new DataAccess();
        }
        return instance;
    }

    public void ConectarDB() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmaciadb",
                    "fbd",
                    "Javiocma140300$");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en la conexion: " + e.getMessage());
        }
    }

    private void DesconectarDB() {
        try {
            statement.close();
            resultSet.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en la desconexion: " + e.getMessage());
        }
    }

    public DefaultTableModel Query(String query) {
        try {
            ConectarDB();
            statement = con.createStatement();
            resultSet = statement.executeQuery(query);
            Vector<String> colNames = new Vector<String>();
            int colCount = resultSet.getMetaData().getColumnCount();
            for (int col = 1; col <= colCount; col++) {
                colNames.add(resultSet.getMetaData().getColumnName(col));
            }
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (resultSet.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int colIndex = 1; colIndex <= colCount; colIndex++) {
                    vector.add(resultSet.getObject(colIndex));
                }
                data.add(vector);
            }
            return new DefaultTableModel(data, colNames) {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en la consultaaaaa: " + e.getMessage());
            return null;
        } finally {
            DesconectarDB();
        }
    }

    public ResultSet Consulta(String query) {
        ConectarDB();
        ResultSet res = null;
        try {
            PreparedStatement pstm = con.prepareStatement(query);
            res = pstm.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en la consulta");
        }
        return res;
    }

//    public DefaultComboBoxModel<Farmacia> GetComboFarma() {
//        DefaultComboBoxModel<Farmacia> comboFarmacia = new DefaultComboBoxModel<Farmacia>();
//        ResultSet res = this.Consulta("SELECT * FROM farmacias ORDER BY idfarmacias");
//        try {
//            while(res.next()) {
//                comboFarmacia.addElement(res.getString("nombre"));
//            }
//            res.close();
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Error en el combo");
//        }
//    }
    public int Execute(String query) {
        try {
            ConectarDB();
            statement = con.createStatement();
            return statement.executeUpdate(query);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en el comando: " + e.getMessage());
            return 0;
        } finally {
            DesconectarDB();
        }
    }
}
