/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icas.admin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author gabo
 */
public class AgregarVigencia {
    public void agregarVigencia(ConexionGenerica conexion, PreparedStatement preparedStatement, String rut) throws SQLException{
        String consulta = "INSERT INTO vigenciaEmpleado(rutEmpleado, fecha, idRazon, vigente)"
                + " VALUES(?,?,?,?)";
        preparedStatement = conexion.getConnection().prepareStatement(consulta);
        preparedStatement.setString(1, rut);
        preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
        preparedStatement.setInt(3, 4);
        preparedStatement.setBoolean(4, true);
        preparedStatement.executeUpdate();
    }
}
