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
package edu.eci.pdsw.samples.persistence;

import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import java.util.List;


/**
 *
 * @author hcadavid
 */
public interface DaoPaciente {

    
    public Paciente load(int id, String tipoid) throws PersistenceException;
    
    public void save(Paciente p) throws PersistenceException;
    
    public void update(Paciente p) throws PersistenceException;
    
    public List<Paciente> getPacientes() ;
    
    public List<Consulta> getConsultas(int idPaciente, String tipoid);
}
