package com.example.appandroid;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

public class Actividad {

    private String nombre;

    private String fecha;

    private String detalle;

    private int tipo; // esto hay q ver cm se maneja, puede ser un int o nose, yo digo 1 para diario, 2 para semanal, 3 para mensual, etc

    private String hora;

    private String user;
    
    private GregorianCalendar calen;
    
    public Actividad(){}

    public Actividad(String nombre, String fecha, String hora, String detalle,int tipo, String user) {
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
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
        return "Actividad{" + "nombre=" + nombre + ", fecha=" + fecha + ", detalle=" + detalle + ", tipo=" + tipo + ", user=" + user + ", hora=" + hora + '}';
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
