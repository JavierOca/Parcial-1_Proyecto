/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessModelLayer;

import DataAccessLayer.DataAccess;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author javier
 */
public class Producto {

    private DataAccess dataAccess = DataAccess.Instance();
    private int idProducto;
    private String nombre;
    private String caducidad;
    private int stock;
    private int idFarmacia;
    private int activo;

    public Producto() {
    }

    public Producto(int idProducto, String nombre, String caducidad, int stock, int idFarmacia, int activo) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.caducidad = caducidad;
        this.stock = stock;
        this.idFarmacia = idFarmacia;
        this.activo = activo;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCaducidad() {
        return caducidad;
    }

    public void setCaducidad(String caducidad) {
        this.caducidad = caducidad;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getIdFarmacia() {
        return idFarmacia;
    }

    public void setIdFarmacia(int idFarmacia) {
        this.idFarmacia = idFarmacia;
    }

    public int isActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public DefaultTableModel GetAllModel() {
        String query = "SELECT * FROM productos";
        return dataAccess.Query(query);
    }

    public void GetById() {
        String query = "SELECT * FROM productos WHERE idProducto = " + idProducto;
        DefaultTableModel res = dataAccess.Query(query);
        idProducto = (int) res.getValueAt(0, 0);
        nombre = (String) res.getValueAt(0, 1);
        caducidad = ""+(Date) res.getValueAt(0, 2);
        stock = (int) res.getValueAt(0, 3);
        idFarmacia = (int) res.getValueAt(0, 4);
        activo = (int) res.getValueAt(0, 5);
    }

    public boolean Add() {
        String query = "INSERT INTO productos(nombre, caducidad, stock, idFarmacia, activo) "
                + "VALUES('" + nombre + "','" + caducidad + "'," + stock + "," + idFarmacia + "," + activo + ");";
        return dataAccess.Execute(query)>=1;
    }
    
    public boolean Delete() {
        String query = "DELETE FROM productos WHERE idProducto = "+ idProducto;
        return dataAccess.Execute(query)>=1;
    }
    
    public boolean Update() {
        String query = "UPDATE productos SET "
                + "nombre = '" + nombre + "', "
                + "caducidad = '" + caducidad + "', "
                + "stock = " + stock + ", "
                + "idFarmacia = " + idFarmacia + ", "
                + "activo = " + activo + " "
                + "WHERE idProducto = "+idProducto;
        return dataAccess.Execute(query)>=1;
    }
}
