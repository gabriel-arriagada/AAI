/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package icas.admin;

import java.sql.Connection;

/**
 *
 * @author Gabriel
 */
public abstract class ConexionGenerica {    
   protected Connection connection;
   public abstract boolean abrirConexion();
   public abstract boolean cerrarConexion();
   public abstract Connection getConnection();
}
