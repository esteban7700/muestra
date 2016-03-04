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
package edu.eci.pdsw.samples.tests;

import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.services.ExcepcionServiciosPacientes;
import edu.eci.pdsw.samples.services.ServiciosPacientesStub;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class ConsultasTest {
    
   
    //Clase de equivalencia uno: agregar una consulta a un paciente que existe
    @Test
    public void consultaTest1(){
        ServiciosPacientesStub servicios=new ServiciosPacientesStub();
        Paciente p=new Paciente(1,"cc","Carlos Sanchez",java.sql.Date.valueOf("2000-01-01"));
        try {
            servicios.registrarNuevoPaciente(p);
            Consulta consulta=new Consulta(java.sql.Date.valueOf("2000-01-02"),"hola como estas?");
            servicios.agregarConsultaAPaciente(1,"cc",consulta);
            for(Consulta s: p.getConsultas()){
                if(s.equals(consulta)){
                    Assert.assertTrue(s.equals(consulta));
                }
            }
        } catch (ExcepcionServiciosPacientes ex) {
            fail("No se pudo agregar la consulta a un paciente que ya existe"+ex.getMessage());   
        }
        
        
                
    }     
   //Clase de equivalencia dos:  agregar una consulta a un paciente que no existe
   @Test
    public void consultaTest2(){
        ServiciosPacientesStub servicios=new ServiciosPacientesStub();
        try {
            Consulta consulta=new Consulta(java.sql.Date.valueOf("2000-01-01"),"hola como estas?");
            servicios.agregarConsultaAPaciente(1,"cc",consulta);
            fail("Se pudo agregar la consulta de un paciente que ya existe");
        } catch (ExcepcionServiciosPacientes ex) {
            Assert.assertTrue(true);    
        }
        
                
    }
    
    
}