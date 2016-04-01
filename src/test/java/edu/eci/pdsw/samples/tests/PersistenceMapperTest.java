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
import java.io.IOException;
import java.io.InputStream;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import edu.eci.pdsw.samples.mybatis.mappers.PacienteMapper;
import java.sql.Date;
import java.util.Set;
import org.junit.Assert;

/**
 *
 * @author hcadavid
 */
public class PersistenceMapperTest {

    public static SqlSessionFactory getSqlSessionFactory() {
        SqlSessionFactory sqlSessionFactory = null;
        if (sqlSessionFactory == null) {
            InputStream inputStream;
            try {
                inputStream = Resources.getResourceAsStream("mybatis-config-h2.xml");
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e.getCause());
            }
        }
        return sqlSessionFactory;
    }

    public PersistenceMapperTest() {}

    @Before
    public void setUp() {
    }
    @Test
    public void OneTest(){
    SqlSessionFactory sessionfact = getSqlSessionFactory();
    SqlSession sqlss = sessionfact.openSession();
    PacienteMapper pmap=sqlss.getMapper(PacienteMapper.class);
    Assert.assertEquals(pmap.loadPacienteById(1, "cc"),null);
    sqlss.commit();
    sqlss.close();
    }
    @Test
    public void TwoTest(){
        int numero=1221946830;
    SqlSessionFactory sessionfact = getSqlSessionFactory();
    SqlSession sqlss = sessionfact.openSession();
    PacienteMapper pmap=sqlss.getMapper(PacienteMapper.class);
    Date date = java.sql.Date.valueOf("1998-06-19");
    Paciente jhordy = new Paciente(numero,"cc","Jhordy Salinis",date);
    pmap.insertPaciente(jhordy);
        
    Assert.assertEquals(jhordy.toString(),pmap.loadPacienteById(numero,"cc").toString());
    sqlss.commit();
    sqlss.close();
    }
    @Test
    public void ThreeTest(){
        int numero=1221946839;
    SqlSessionFactory sessionfact = getSqlSessionFactory();
    SqlSession sqlss = sessionfact.openSession();
    PacienteMapper pmap=sqlss.getMapper(PacienteMapper.class);
    Date date = java.sql.Date.valueOf("1998-06-19");
    Paciente jhordy = new Paciente(numero,"cc","Jhordy Salinis",date);
    pmap.insertPaciente(jhordy);
    Consulta consulta=new Consulta(java.sql.Date.valueOf("2000-01-02"),"hola como estas?");
    pmap.insertConsulta(consulta,jhordy.getId(),jhordy.getTipo_id());
    Set<Consulta> set=jhordy.getConsultas();
    set.add(consulta);
    jhordy.setConsultas(set);
    System.out.println("hola");
        System.out.println(jhordy.toString());
        System.out.println(pmap.loadPacienteById(numero,"cc").toString());
    Assert.assertEquals(jhordy.toString(),pmap.loadPacienteById(numero,"cc").toString());
    sqlss.commit();
    sqlss.close();
    }    
}

