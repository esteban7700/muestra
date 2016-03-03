/*
 * Copyright (C) 2016 hcadavid
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
package edu.eci.pdsw.samples.tests;

import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.managedbeans.RegistroConsultaBean;
import edu.eci.pdsw.samples.services.ExcepcionServiciosPacientes;
import edu.eci.pdsw.samples.services.ServiciosPacientesStub;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Date;
import junit.framework.Assert;
/**
 *
 * @author hcadavid
 */
public class PacientesTest {
    
    public PacientesTest() {
    }
    
    /*1:Registrar un paciente que no exite - Pos*/
    @Test
    public void registroPacienteTestUno(){        
        Date date = new Date(1850, 11, 19);
        Paciente jhordy = new Paciente(1121946483,"Cedula","Jhordy Salinas", (java.sql.Date) date);
        ServiciosPacientesStub servicio = new ServiciosPacientesStub();
        try{
            servicio.registrarNuevoPaciente(jhordy);
            Assert.assertTrue(true);
        }
        catch(ExcepcionServiciosPacientes e){       
            fail("El paciente ya existe!!!");
        }
    }
    
    /*2:Registrar un paciente ya existente - Neg*/
    @Test
    public void registroPacienteTestDos(){        
        Date date = new Date(1850, 11, 19);
        Paciente jhordy = new Paciente(1121946483,"Cedula","Jhordy Salinas", (java.sql.Date) date);
        ServiciosPacientesStub servicio = new ServiciosPacientesStub();
        try{
            servicio.registrarNuevoPaciente(jhordy);
            servicio.registrarNuevoPaciente(jhordy);
            fail("El paciente ya existe!!!");
        }
        catch(ExcepcionServiciosPacientes e){
            Assert.assertTrue(true);
        }
    } 
}
