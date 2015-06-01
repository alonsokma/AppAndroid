package com.example.appandroid;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

public class Actividad {

    private String nombre;

    private Date fecha;

    private String detalle;

    private int tipo; // esto hay q ver cm se maneja, puede ser un int o nose, yo digo 1 para diario, 2 para semanal, 3 para mensual, etc

    private Timestamp hora;

    private Usuario user;
    
    private GregorianCalendar calen;
    
    public Actividad(){}

    public Actividad(String nombre, Date fecha, String detalle, int tipo, Usuario user, Timestamp hora) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.hora = hora;
        this.detalle = detalle;
        this.tipo = tipo;
        this.user = user;
        calen = (GregorianCalendar) GregorianCalendar.getInstance();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Timestamp getHora() {
        return hora;
    }

    public void setHora(Timestamp hora) {
        this.hora = hora;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Actividad{" + "nombre=" + nombre + ", fecha=" + fecha + ", detalle=" + detalle + ", tipo=" + tipo + ", user=" + getUser() + ", hora=" + hora.getTime() + '}';
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
}
