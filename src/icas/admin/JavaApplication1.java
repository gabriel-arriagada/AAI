/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icas.admin;

/**
 *
 * @author gabo
 */
public class JavaApplication1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        
        Empleado administrador = new Empleado();
        administrador.setNombre("Gabriel");
        administrador.setApellido("Arriagada");
        administrador.setRut("17873151-3");
        administrador.setClave("jazzbass");
        administrador.setIdRol(1);
        administrador.setCorreo("gabriel.arriagada02@inacapmail.cl");
        Crear crearAdministrador = new CrearEmpleado(new ConexionPostgreSql(), administrador);
        crearAdministrador.crear();
        
        Empleado empleado = new Empleado();
        empleado.setNombre("Sistema");
        empleado.setApellido("Ventas");
        empleado.setRut("1-9");
        empleado.setClave("ergt34tgefbddsc");
        empleado.setIdRol(2);
        empleado.setCorreo("-");        
        
        Crear crear = new CrearEmpleado(new ConexionPostgreSql(), empleado);
        crear.crear();
        
    }
    
}
