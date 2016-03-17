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
package edu.eci.pdsw.samples.managedbeans;

import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.services.ExcepcionServiciosPacientes;
import edu.eci.pdsw.samples.services.ServiciosPacientes;
import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.swing.JOptionPane;

/**
 *
 * @author hcadavid
 */
@ManagedBean(name="Registro")
@SessionScoped
public class RegistroConsultaBean implements Serializable{
    private ServiciosPacientes sp=ServiciosPacientes.getInstance();
    private int id;
    private String tipoId;
    private String nombre;
    private String fecha;
    private List<Paciente> listaPacientes;
    private List<Consulta> listaConsultas;
    private Paciente pacienteSeleccionado;
    private String resumen;

    public RegistroConsultaBean() throws ExcepcionServiciosPacientes {
        this.listaPacientes = sp.getPacientes();
    }
        
    
    public void accionAgregarConsulta() {
        FacesContext context=FacesContext.getCurrentInstance();
        boolean continua=true;
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            Date date=java.sql.Date.valueOf(dateFormat.format(cal.getTime()));
            sp.agregarConsultaAPaciente(pacienteSeleccionado.getId(), pacienteSeleccionado.getTipo_id(), new Consulta(date, resumen));
            setListaConsultas(sp.getConsultas(pacienteSeleccionado.getId(), pacienteSeleccionado.getTipo_id()));
            if(continua){
                context.addMessage(null, new FacesMessage("Bien hecho: ","Se ha agregado su consulta"));
            }
        } catch (Exception ex) {
            continua=false;
            context.addMessage(null, new FacesMessage("Error: ", ex.getMessage()));
        }
    }
    public void accionAgregarPaciente() {
        FacesContext context=FacesContext.getCurrentInstance();
        boolean continua=true;
        try {        
            sp.registrarNuevoPaciente(new Paciente(id,tipoId,nombre,java.sql.Date.valueOf(fecha.replace('/','-'))));
            listaPacientes=sp.getPacientes();
            if(continua){
                context.addMessage(null, new FacesMessage("Bien hecho: ","Se ha registrado bien el paciente"));
            }
        } catch (Exception ex) {
            continua=false;
            context.addMessage(null, new FacesMessage("Error: ", ex.getMessage()));
        }
    }
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the tipoId
     */
    public String getTipoId() {
        return tipoId;
    }

    /**
     * @param tipoId the tipoId to set
     */
    public void setTipoId(String tipoId) {
        this.tipoId = tipoId;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the listaPacientes
     */
    public List<Paciente> getListaPacientes() {
        return listaPacientes;
    }

    /**
     * @param listaPacientes the listaPacientes to set
     */
    public void setListaPacientes(List<Paciente> listaPacientes) {
        this.listaPacientes = listaPacientes;
    }

    /**
     * @return the pacienteSeleccionado
     */
    public Paciente getPacienteSeleccionado() {
        return pacienteSeleccionado;
    }

    /**
     * @param pacienteSeleccionado the pacienteSeleccionado to set
     */
    public void setPacienteSeleccionado(Paciente pacienteSeleccionado) {
        this.pacienteSeleccionado = pacienteSeleccionado;
    }

    /**
     * @return the resumen
     */
    public String getResumen() {
        return resumen;
    }

    /**
     * @param resumen the resumen to set
     */
    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    /**
     * @return the listaConsultas
     */
    public List<Consulta> getListaConsultas() {
        try{
        setListaConsultas(sp.getConsultas(pacienteSeleccionado.getId(), pacienteSeleccionado.getTipo_id()));}
        catch(Exception e){
            setListaConsultas(new ArrayList<Consulta>());
        }
        return listaConsultas;
    }

    /**
     * @param listaConsultas the listaConsultas to set
     */
    public void setListaConsultas(List<Consulta> listaConsultas) {
        this.listaConsultas = listaConsultas;
    }
    
}
