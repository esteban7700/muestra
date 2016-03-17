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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author casvad
 */
public class ServiciosPacientesDAO extends ServiciosPacientes{
    private DaoPaciente dao;

    @Override
    public Paciente consultarPaciente(int idPaciente, String tipoid) throws ExcepcionServiciosPacientes {
        try {
            return dao.load(idPaciente, tipoid);
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosPacientes(ex.getMessage());
        }
    }

    @Override
    public void registrarNuevoPaciente(Paciente p) throws ExcepcionServiciosPacientes {
        try {
            dao.save(p);
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosPacientes(ex.getMessage());
        }
    }

    @Override
    public void agregarConsultaAPaciente(int idPaciente, String tipoid, Consulta c) throws ExcepcionServiciosPacientes {
        Paciente p;
        try {
            p = dao.load(idPaciente, tipoid);
            p.getConsultas().add(c); //no se si funcione bien
            dao.update(p);
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
