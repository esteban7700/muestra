/*
 * Copyright (C) 2015 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.pdsw.samples.persistence.jdbcimpl;

import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.persistence.DaoPaciente;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class JDBCDaoPaciente implements DaoPaciente {

    Connection con;

    public JDBCDaoPaciente(Connection con) {
        this.con = con;
    }
        

    @Override
    public Paciente load(int idpaciente, String tipoid) throws PersistenceException {
        PreparedStatement ps;
        Paciente p;
        LinkedHashSet<Consulta> consultas=new LinkedHashSet<Consulta>();
        try {
            String consultaSugerida="select pac.nombre, pac.fecha_nacimiento, con.idCONSULTAS, con.fecha_y_hora, con.resumen from PACIENTES as pac left join CONSULTAS as con on con.PACIENTES_id=pac.id and con.PACIENTES_tipo_id=pac.tipo_id where pac.id=? and pac.tipo_id=?";
            ps=con.prepareStatement(consultaSugerida);
            //Asignar parámetros
            ps.setInt(1,idpaciente);
            ps.setString(2,tipoid);
            
            
            //usar executeQuery
            ResultSet rs=ps.executeQuery();
            rs.next();
            p=new Paciente(idpaciente, tipoid,rs.getString(1), rs.getDate(2));
            Consulta consulta;
            if(!(rs.getDate(4)==null || rs.getString(5)==null)){
                consulta=new Consulta(rs.getDate(4),rs.getString(5));
                consulta.setId(rs.getInt(3));
                consultas.add(consulta);
            }
            //////Agregando consultas
            while(rs.next()){
                consulta=new Consulta(rs.getDate(4),rs.getString(5));
                consulta.setId(rs.getInt(3));
                consultas.add(consulta);
            }
            p.setConsultas(consultas);
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading "+idpaciente+" "+ex.getMessage(),ex);
        }
        return p;
    }

    @Override
    public void save(Paciente p) throws PersistenceException { 
        String consultarC="SELECT * FROM PACIENTES WHERE id=? and tipo_id=?";
        ResultSet r = null;
        PreparedStatement prepared;
        
        try {
            prepared=con.prepareStatement(consultarC);
            prepared.setInt(1, p.getId());
            prepared.setString(2, p.getTipo_id());          
            r=prepared.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(JDBCDaoPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
        if(!r.next()){
            
            
            PreparedStatement ps;
                //Crear preparedStatement
            PreparedStatement statement;
            String consulta="INSERT INTO PACIENTES (id,tipo_id,nombre,fecha_nacimiento) values (?,?,?,?)" ;
            statement=con.prepareStatement(consulta);
            //Asignar parámetros
            statement.setInt(1,p.getId());
            statement.setString(2,p.getTipo_id());
            statement.setString(3,p.getNombre());
            statement.setDate(4, p.getFechaNacimiento());
            //usar 'execute'

            statement.execute();

            for (Consulta c:p.getConsultas()){
                PreparedStatement agregarConsulta;
                String cons="INSERT INTO CONSULTAS (fecha_y_hora,resumen,PACIENTES_id,PACIENTES_tipo_id) "
                        + "VALUES(?,?,?,?)";
                agregarConsulta=con.prepareStatement(cons);
                agregarConsulta.setDate(1, c.getFechayHora());
                agregarConsulta.setString(2, c.getResumen());
                agregarConsulta.setInt(3, p.getId());
                agregarConsulta.setString(4, p.getTipo_id());
                agregarConsulta.execute();
            }
        }
        else{
            
            throw new PersistenceException("An error ocurred while saving a product.");
        }
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while saving a product."+ex.getMessage(),ex);
        }
    }

    @Override
    public void update(Paciente p) throws PersistenceException {
        String consultarC="DELETE FROM PACIENTES WHERE id=? and tipo_id=?";
        ResultSet r = null;
        PreparedStatement prepared;
        PreparedStatement prepare; 
        try {
            String eliminarResto="DELETE FROM CONSULTAS WHERE PACIENTES_id=? and PACIENTES_tipo_id=?";
            prepare=con.prepareStatement(eliminarResto);
            prepare.setInt(1, p.getId());
            prepare.setString(2, p.getTipo_id());
            prepare.execute();
            prepared=con.prepareStatement(consultarC);
            prepared.setInt(1, p.getId());
            prepared.setString(2, p.getTipo_id());
            prepared.execute();
            save(p);
   
        }
        catch (SQLException ex) {
            Logger.getLogger(JDBCDaoPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Paciente> getPacientes() {
        String consultarC="SELECT id,tipo_id,nombre,fecha_nacimiento FROM PACIENTES";
        ResultSet r = null;
        List<Paciente> pacientes = new ArrayList<Paciente>();
        PreparedStatement prepared;
        try {
            prepared=con.prepareStatement(consultarC);
            r=prepared.executeQuery();
            while(r.next()){
                pacientes.add(new Paciente(r.getInt(1), r.getString(2), r.getString(3),  r.getDate(4)));
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(JDBCDaoPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pacientes;
    }

    @Override
    public List<Consulta> getConsultas(int idPaciente, String tipoid) {
        String consultarC="SELECT fecha_y_hora,resumen,idCONSULTAS FROM CONSULTAS WHERE PACIENTES_id=? AND PACIENTES_tipo_id=?" ;
        ResultSet r = null;
        List<Consulta> consultas = new ArrayList<Consulta>();
        PreparedStatement prepared;
        try {
            prepared=con.prepareStatement(consultarC);
            prepared.setInt(1, idPaciente);
            prepared.setString(2, tipoid);
            r=prepared.executeQuery();
            while(r.next()){
                Consulta con=new Consulta(r.getDate(1),r.getString(2));
                con.setId(r.getInt(3));
                consultas.add(con);
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(JDBCDaoPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return consultas;
    }
    
}
