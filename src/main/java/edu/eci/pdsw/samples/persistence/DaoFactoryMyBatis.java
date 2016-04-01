/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.persistence;

import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.persistence.jdbcimpl.JDBCDaoPaciente;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 *
 * @author 2105534
 */
public class DaoFactoryMyBatis extends DaoFactory{
    private static SqlSessionFactory sesionFactory;
    private SqlSession sesion=null;
    
    public DaoFactoryMyBatis(Properties appProperties) {
        sesionFactory=getSqlSessionFactory(appProperties);
    }
       
    public static SqlSessionFactory getSqlSessionFactory(Properties appProperties) {
        SqlSessionFactory sqlSessionFactory = null;
        if (sqlSessionFactory == null) {
            InputStream inputStream;
            try {
                inputStream = Resources.getResourceAsStream(appProperties.getProperty("config"));
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException ex) {
                throw new RuntimeException(ex.getCause());
            }
        }
        return sqlSessionFactory;
    }
    
    @Override
    public void beginSession() throws PersistenceException {
        sesion=sesionFactory.openSession();
    }

    @Override
    public void commitTransaction() throws PersistenceException {
        sesion.commit();
        
    }

    @Override
    public void rollbackTransaction() throws PersistenceException {
        sesion.rollback();
    }

    @Override
    public void endSession() throws PersistenceException {
        sesion.close();
    }  
    
    @Override
    public DaoPaciente getDaoPaciente() {
        return new DaoPacienteMyBatis(sesion);
    }
}
