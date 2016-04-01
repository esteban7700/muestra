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
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*/
import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.persistence.DaoFactory;
import edu.eci.pdsw.samples.persistence.DaoPaciente;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import edu.eci.pdsw.samples.persistence.jdbcimpl.JDBCDaoPaciente;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
/**
*
* @author hcadavid
*/
public class PersistenceDaoTest {
    @Test
    public void databaseConnectionTest() throws IOException, PersistenceException{
        int enetero=20640;
        InputStream input = null;
        input = ClassLoader.getSystemResourceAsStream("applicationconfig_test.properties");
        Properties properties=new Properties();
        properties.load(input);
        
        DaoFactory daof=DaoFactory.getInstance(properties);
        daof.beginSession();
        //IMPLEMENTACION DE LAS PRUEBAS
        DaoPaciente bd=daof.getDaoPaciente();
        Paciente p=new Paciente(enetero,"cc","carlos",java.sql.Date.valueOf("2000-01-01"));
        Set<Consulta> consultas=new LinkedHashSet<>();
        Consulta consulta=new Consulta(java.sql.Date.valueOf("2000-01-01"), "se esta agregando una nueva consulta");
        consultas.add(consulta);
        p.setConsultas(consultas);
        
        bd.save(p);
        
        Paciente aMirar=bd.load(enetero,"cc");
        for(Consulta c:aMirar.getConsultas()){
            consulta.setId(c.getId());
        }
        
        daof.commitTransaction();
        daof.endSession();
        Assert.assertEquals(aMirar.toString(),p.toString());
    }
    @Test
        public void databaseConnection2Test() throws IOException, PersistenceException{
             int enetero=20631;
        InputStream input = null;
        input = ClassLoader.getSystemResourceAsStream("applicationconfig_test.properties");
        Properties properties=new Properties();
        properties.load(input);
        DaoFactory daof=DaoFactory.getInstance(properties);
        daof.beginSession();
        //IMPLEMENTACION DE LAS PRUEBAS
        DaoPaciente bd=daof.getDaoPaciente();
        Paciente p=new Paciente(enetero,"cc","carlos",java.sql.Date.valueOf("2000-01-01"));
        
        bd.save(p);
        
        Paciente aMirar=bd.load(enetero,"cc");
        daof.commitTransaction();
        daof.endSession();

        Assert.assertEquals(aMirar.toString(),p.toString());
    }
    @Test
        public void databaseConnection3Test() throws IOException, PersistenceException{
            int enetero=20622;
        InputStream input = null;
        input = ClassLoader.getSystemResourceAsStream("applicationconfig_test.properties");
        Properties properties=new Properties();
        properties.load(input);
        DaoFactory daof=DaoFactory.getInstance(properties);
        daof.beginSession();
        //IMPLEMENTACION DE LAS PRUEBAS
        DaoPaciente bd=daof.getDaoPaciente();
        Paciente p=new Paciente(enetero,"cc","carlos",java.sql.Date.valueOf("2000-01-01"));
        Set<Consulta> consultas=new LinkedHashSet<>();
        Consulta c=new Consulta(java.sql.Date.valueOf("2000-01-01"), "se esta agregando una nueva consulta");
        consultas.add(c);
        Consulta c1=new Consulta(java.sql.Date.valueOf("2000-02-02"), "se esta agregando otra nueva consulta");
        
        consultas.add(c1);
        Consulta c2=new Consulta(java.sql.Date.valueOf("2000-02-04"), "se esta agregando otra otra nueva consulta");
        consultas.add(c2);
        p.setConsultas(consultas);
        bd.save(p);
        Paciente aMirar=bd.load(enetero,"cc");
        int i=0;
        for(Consulta con:aMirar.getConsultas()){
            if(i==0){
                c.setId(con.getId());
            }
            else if(i==1){
                c1.setId(con.getId());
            }
            else {
                c2.setId(con.getId());
            }
            i++;
        }
        daof.commitTransaction();
        daof.endSession();
        Assert.assertEquals(aMirar.toString(),p.toString());
    }
    @Test
    
        public void databaseConnection4Test() throws IOException, PersistenceException{
            int enetero=20613;
        InputStream input = null;
        input = ClassLoader.getSystemResourceAsStream("applicationconfig_test.properties");
        Properties properties=new Properties();
        properties.load(input);
        DaoFactory daof=DaoFactory.getInstance(properties);
        daof.beginSession();
        //IMPLEMENTACION DE LAS PRUEBAS
        DaoPaciente bd=daof.getDaoPaciente();
        Paciente p=new Paciente(enetero,"cc","carlos",java.sql.Date.valueOf("2000-01-01"));
        Set<Consulta> consultas=new LinkedHashSet<Consulta>();
        Consulta c=new Consulta(java.sql.Date.valueOf("2000-01-01"), "se esta agregando una nueva consulta");
        c.setId(10);
        consultas.add(c);
        c=new Consulta(java.sql.Date.valueOf("2000-02-02"), "se esta agregando otra nueva consulta");
        c.setId(125);
        consultas.add(c);
        p.setConsultas(consultas);
        try{
        bd.save(p);
        
        bd.save(p);
        fail("Se pudo registrar el paciente dos veces");
        }catch(PersistenceException e){
        daof.commitTransaction();
        daof.endSession();
        Assert.assertTrue(true);
    }
}
}