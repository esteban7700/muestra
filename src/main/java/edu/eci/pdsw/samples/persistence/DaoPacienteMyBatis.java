/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.persistence;

import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.mybatis.mappers.PacienteMapper;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author 2105534
 */
public class DaoPacienteMyBatis implements DaoPaciente{
    PacienteMapper pmap;
    public DaoPacienteMyBatis(SqlSession sesion) {
        pmap=sesion.getMapper(PacienteMapper.class);
    }
    
    @Override
    public Paciente load(int id, String tipoid) throws PersistenceException {
        return pmap.loadPacienteById(id, tipoid);
    }

    @Override
    public void save(Paciente p) throws PersistenceException {
        pmap.insertPaciente(p);
    }

    @Override
    public void update(Paciente p) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Paciente> getPacientes() {
        return pmap.loadPacientes();
    }

    @Override
    public List<Consulta> getConsultas(int idPaciente, String tipoid) {
        return pmap.loadConsultas(idPaciente, tipoid);
    }
}
