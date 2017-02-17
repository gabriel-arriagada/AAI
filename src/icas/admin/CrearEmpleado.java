/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icas.admin;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Gabriel
 */
public class CrearEmpleado extends Crear{

    private PreparedStatement preparedStatement;
    private Encriptar encriptar;
    private final Empleado empleado;

    public CrearEmpleado(ConexionGenerica conexion, Empleado empleado) {
        this.conexion = conexion;
        this.empleado = empleado;
    }

    @Override
    public boolean crear() {
        boolean retorno = false;
        byte[] claveEncriptada = null;
        preparedStatement = null;

        try {
            encriptar = new Encriptar();
            claveEncriptada = encriptar.encriptar(empleado.getClave());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(CrearEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }

        String consulta = "INSERT INTO empleado"
                + " (rut, idRol, clave, nombre, apellido, correo) "
                + " VALUES(?, ?, ?, ?, ?, ?)";

        if (this.conexion.abrirConexion()) {
            try {
                this.conexion.getConnection().setAutoCommit(false);
                preparedStatement = this.conexion.getConnection().prepareStatement(consulta);
                preparedStatement.setString(1, empleado.getRut());
                preparedStatement.setInt(2, empleado.getIdRol());
                preparedStatement.setBytes(3, claveEncriptada);
                preparedStatement.setString(4, empleado.getNombre());
                preparedStatement.setString(5, empleado.getApellido());
                preparedStatement.setString(6, empleado.getCorreo());                
                int filasAfectadas = preparedStatement.executeUpdate();
                if (filasAfectadas > 0) {
                    AgregarVigencia av = new AgregarVigencia();
                    av.agregarVigencia(conexion, preparedStatement, empleado.getRut());
                    this.conexion.getConnection().commit();
                    retorno = true;
                } else {
                    this.conexion.getConnection().rollback();
                }

            } catch (SQLException ex) {
                try {
                    this.conexion.getConnection().rollback();
                    Logger.getLogger(CrearEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex1) {
                    Logger.getLogger(CrearEmpleado.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                if (this.preparedStatement != null) {
                    try {
                        this.preparedStatement.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(CrearEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                try {
                    this.conexion.getConnection().setAutoCommit(true);
                } catch (SQLException ex) {
                    Logger.getLogger(CrearEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.conexion.cerrarConexion();
            }
        }
        return retorno;
    }

}
