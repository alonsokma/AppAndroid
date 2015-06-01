package com.example.appandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
public class DBAdapter {
	
    static final String TAG = "DBAdapter";
    static final String DATABASE_NAME = "Calendar";
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_CREATE =
    	"CREATE TABLE `Usuarios` (`nick` TEXT NOT NULL,`nombre`	TEXT,`pass`	TEXT,PRIMARY KEY(nick));"
    	+"CREATE TABLE `Actividades` (`nombre` TEXT NOT NULL,`fecha` TEXT,`hora` TEXT,"
    	+"`detalle`	TEXT,`tipo`	INTEGER,`usuario` TEXT NOT NULL,PRIMARY KEY(nombre,usuario),"
    	+"FOREIGN KEY(`usuario`) REFERENCES Usuario ( nick ));"
    	+"CREATE TABLE `ActividadContactos` ("
    	+"`nombreContacto` TEXT NOT NULL,`telefonoContacto`	TEXT NOT NULL,`nombreActividad`	TEXT NOT NULL,"
    	+"`nombreUsuario` TEXT NOT NULL,PRIMARY KEY(nombreContacto,telefonoContacto,nombreActividad,nombreUsuario),"
    	+"FOREIGN KEY(`nombreUsuario`) REFERENCES Actividades ( usuario ));";
    
    final Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;
    
    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }   
    
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db)
        {
        	Log.i(this.getClass().toString(), "Creando base de datos");
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
           db.execSQL("DROP TABLE IF EXISTS usuarios");
           onCreate(db);
        }
    }
    
    //---Abrimos la base datos---
    public DBAdapter open() throws SQLException 
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }
    
    //---Cerramos la base de datos ---
    public void close() 
    {
        DBHelper.close();
    }
    
    //---Insertamos un dato en la BD---
    public boolean insertUsuario(String nick, String nombre,String pass){
    	try{
    	db.execSQL("INSERT INTO USUARIOS(nick,nombre,pass) VALUES('"+nick+"'"
    			+ ",'"+nombre+"','"+pass+"')");
    	return true;
    	}catch(Exception e){
    		return false;
    	}
    }
    
    public long insertActividad(String nombre, String fecha,String hora,
    	String detalle,int tipo,String usuario) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("nombre", nombre);
        initialValues.put("fecha", fecha);
        initialValues.put("hora", hora);
        initialValues.put("detalle", detalle);
        initialValues.put("tipo", tipo);
        initialValues.put("usuario", usuario);
        return db.insert("Actividades", null, initialValues);
    }
    
    //---Borramos un dato particular---
    public boolean BorrarUsuario(String nick){
        return db.delete("Usuarios", "nick" + " = " + nick, null) > 0;
    } 
    
    public boolean BorrarActividad(String nick,String nombre){
        return db.delete("Actividad",
        		"usuario" + " = " + nick +" and nombre="+nombre
        		, null) > 0;
    } 
    
    //---Recuperamos todo los datos---
    public Cursor CargarTodosLosUsuarios() {
        return db.query("Usuarios", new String[] {"nick", "nombre",
                "pass"}, null, null, null, null, null);
    }
    
    public Cursor CargarTodaslasActividades() {
        return db.query("Actividades", new String[] {"nombre","fecha","hora",
        	"detalle","tipo","usuario"}, null, null, null, null, null);
    }
    
    //---recuperamos un dato particular---
    public Cursor ObtenerUsuario(String nick,String pass) throws SQLException {
        Cursor mCursor =
                db.query(true, "Usuarios", new String[] {"nick", "nombre",
                        "pass"}, "nick" + " = '" + nick +"' and pass = '"+pass+"'", null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    
    public Cursor ObtenerActividadFecha(String usuario,String fecha) throws SQLException {
        Cursor mCursor =
                db.query(true, "Usuarios", new String[] {"nombre","fecha","hora",
                    	"detalle","tipo","usuario"}, 
                    	"fecha = " + fecha + "and usuario = "+ usuario, null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    
    
    //---Actualizamos un dato---
    public boolean ActualizarActividad(String nick, String nombre,String pass){
        ContentValues args = new ContentValues();
        args.put("nick", nick);
        args.put("nombre", nombre);
        args.put("pass", pass);
        return db.update(
            "Usuarios", args, "nick" + "=" + nick, null) > 0;
    }
}


// FIN DE LA CLASE DatabaseHelper
