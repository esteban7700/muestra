/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.services;

import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.persistence.DaoFactory;
import edu.eci.pdsw.samples.persistence.DaoPaciente;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author casvad
 */
public class ServiciosPacientesDAO extends ServiciosPacientes{
    
    private DaoPaciente dao;
    private final DaoFactory daoFactory;
    
    public ServiciosPacientesDAO(){
        InputStream input = null;
        try {    
            input= ServiciosPacientesDAO.class.getClassLoader().getResource("applicationconfig.properties").openStream();
        } catch (IOException ex) {
            Logger.getLogger(ServiciosPacientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        Properties propiedades= new Properties();
        try {
            propiedades.load(input);
        } catch (IOException ex) {
            Logger.getLogger(ServiciosPacientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        daoFactory=DaoFactory.getInstance(propiedades);
    }
    
    @Override
    public Paciente consultarPaciente(int idPaciente, String tipoid) throws ExcepcionServiciosPacientes {
        try {
            daoFactory.beginSession();
            dao=daoFactory.getDaoPaciente();
            Paciente p = dao.load(idPaciente, tipoid);
            daoFactory.commitTransaction();
            daoFactory.endSession();
            return p;
            
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosPacientes(ex.getMessage());
        }
    }

    @Override
    public void registrarNuevoPaciente(Paciente p) throws ExcepcionServiciosPacientes {
        try {
            daoFactory.beginSession();            
            dao.save(p);
            daoFactory.commitTransaction();
            daoFactory.endSession();
            
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosPacientes(ex.getMessage());
        }
    }

    @Override
    public void agregarConsultaAPaciente(int idPaciente, String tipoid, Consulta c) throws ExcepcionServiciosPacientes {
        try {
            daoFactory.beginSession();
            dao=daoFactory.getDaoPaciente();
            dao.load(idPaciente, tipoid).getConsultas().add(c);
            dao.update(dao.load(idPaciente, tipoid));
            daoFactory.commitTransaction();
            daoFactory.endSession();
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosPacientes(ex.getMessage());
        }   
    }

    @Override
    public List<Paciente> getPacientes() {
        List<Paciente> pacientes;
        pacientes = dao.getPacientes();
        return pacientes;
    }

    @Override
    public List<Consulta> getConsultas(int idPaciente, String tipoid) {
        List<Consulta> consultas;
        consultas=dao.getConsultas(idPaciente, tipoid);
        return consultas;
    }
}
