package com.example.appandroid;

public class Usuario {

    private String nick;

    private String nombre;

    private String pass;
    
    public Usuario(){
    }
    
    public Usuario(String nombre, String nick, String pass){
        this.nombre = nombre;
        this.nick = nick;
        this.pass = pass;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "Usuario{" + "nick=" + nick + ", nombre=" + nombre + ", pass=" + pass + '}';
    }
}
