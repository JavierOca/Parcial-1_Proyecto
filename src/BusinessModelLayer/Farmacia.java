/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessModelLayer;

import DataAccessLayer.DataAccess;
import java.sql.ResultSet;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author javier
 */
public class Farmacia {
    private DataAccess dataAccess = DataAccess.Instance();
    private int idFarmacia;
    private String nombre;
    private String  domicilio;
    private String telefono;
    private int activo;

    public Farmacia() {
    }

    public Farmacia(int idFarmacia, String nombre, String domicilio, String telefono, int activo) {
        this.idFarmacia = idFarmacia;
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.activo = activo;
    }

    public int getIdFarmacia() {
        return idFarmacia;
    }

    public void setIdFarmacia(int idFarmacia) {
        this.idFarmacia = idFarmacia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public DefaultTableModel GetAllModel() {
        String query = "SELECT * FROM farmacias";
        return dataAccess.Query(query);
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }
    
    
    public void GetById() {
        String query = "SELECT * FROM farmacias WHERE idFarmacias = "+idFarmacia;
        DefaultTableModel res = dataAccess.Query(query);
        idFarmacia = (int) res.getValueAt(0, 0);
        nombre = (String) res.getValueAt(0, 1);
        domicilio = (String) res.getValueAt(0, 2);
        telefono = (String) res.getValueAt(0, 3);
        activo = (int) res.getValueAt(0, 4);
    }
    
    public boolean Add() {
        String query = "INSERT INTO farmacias(nombre, domicilio, telefono, activo) "
                + "VALUES('"+nombre+"','"+domicilio+"',"+telefono+","+activo+");";
        return dataAccess.Execute(query)>=1;
    }
    
    public boolean Delete() {
        String query = "DELETE FROM farmacias WHERE idFarmacias = "+ idFarmacia;
        return dataAccess.Execute(query)>=1;
    }
    
    public boolean Update() {
        String query = "UPDATE farmacias SET "
                + "nombre = '"+nombre+"', "
                + "domicilio = '"+domicilio+"', "
                + "telefono = "+telefono+", "
                + "activo = "+activo+" "
                + "WHERE idFarmacias = "+idFarmacia;
        return dataAccess.Execute(query)>=1;
    }
    
    public void mostrarFarmacias(JComboBox<Farmacia> comboFarma) {
        try {
            dataAccess.ConectarDB();
            ResultSet rs = dataAccess.Consulta("SELECT idfarmacias, nombre, domicilio, telefono, activo FROM farmacias ORDER BY idfarmacias");
            while (rs.next()) {
                comboFarma.addItem(new Farmacia(
                        rs.getInt("idfarmacias"),
                        rs.getString("nombre"),
                        rs.getString("domicilio"),
                        rs.getString("telefono"),
                        rs.getInt("activo")
                )
                );
            }
        } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al cargar los datos");
        }
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}
