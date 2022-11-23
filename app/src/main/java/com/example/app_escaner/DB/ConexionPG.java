package com.example.app_escaner.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.StrictMode;

import androidx.annotation.Nullable;

import com.example.app_escaner.ConsultaCiudadano;
import com.example.app_escaner.R;
import com.example.app_escaner.entidades.Ciudadanos;
import com.example.app_escaner.entidades.FuerzaPublica;

import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;


public class ConexionPG {
    public Connection connection = null;
    public String host = "ec2-44-209-57-4.compute-1.amazonaws.com";
    public String database = "d1ehbtn7v0nr86";
    public int port = 5432;
    public String user = "gzjbuhiiiwulyf";
    public String password = "d69af02503ad4cb86beaa0f47c5e884133e1769affe128fdd59548dbe2e3a9e0";
    public String url = "jdbc:postgresql://%s:%d/%s";
    public boolean status;
    java.sql.Statement st;
    ResultSet resultQuery;
    ConsultaCiudadano consultaCiudadano = new ConsultaCiudadano();
    private Connection connect;


    public void Database() {
        this.url = String.format(this.url, this.host, this.port, this.database);
        System.out.println("This is de url: " + this.url);
        Connection con = connect();
        //this.disconnect();
        System.out.println("connection status: " + status + " this is the connection: " + con);
    }

    public Connection connect() {

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
            //connection = DriverManager.getConnection(url);
            status = true;
            System.out.println("Connected to database:" + status);
        } catch (Exception e) {
            status = false;
            System.out.print(e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    public void close_conexion(Connection con) throws Exception {
        con.close();
    }


    public ArrayList<Ciudadanos> consultarCiudadanos() {
        String rueba = "";

        ArrayList<Ciudadanos> listaCiudadanos = new ArrayList<>();
        Ciudadanos ciudadano = null;

        try {
            Connection connection = connect();
            st = connection.createStatement();
            String sql = "SELECT id_ciudadano, numero_identificacion, nombre, apellidos, fecha_nacimento, rh, grupo_sanguineo, sexo, requerido FROM ciudadanos;";
            resultQuery = st.executeQuery(sql);
            String resultado = "";
            while (resultQuery.next()) {
                ciudadano = new Ciudadanos();
                ciudadano.setId_ciudadano(Integer.parseInt(resultQuery.getString("id_ciudadano")));
                ciudadano.setNumero_identificacion(Integer.parseInt(resultQuery.getString("numero_identificacion")));
                ciudadano.setNombre(resultQuery.getString("nombre"));
                ciudadano.setApellidos(resultQuery.getString("apellidos"));
                ciudadano.setFecha_nacimento(resultQuery.getString("fecha_nacimento"));
                ciudadano.setRh(resultQuery.getString("rh"));
                ciudadano.setGrupo_sanguineo(resultQuery.getString("grupo_sanguineo"));
                ciudadano.setSexo(resultQuery.getString("sexo"));
                ciudadano.setRequerido(resultQuery.getString("requerido"));
                ciudadano.setIconImg(R.drawable.person);
                listaCiudadanos.add(ciudadano);

            }
            st.close();
            close_conexion(connection);
        } catch (Exception e) {
            System.out.println("Errrrrrrrrrrrrrrrrrrrrrrrrrrrooooooooooooooooooooorrrrrrrrrrrrrrrrrrrr");
        }
        return listaCiudadanos;
    }

    public Ciudadanos verCiudadanos(int id) {

        Ciudadanos ciudadano = null;

        try {
            Connection connection = connect();
            st = connection.createStatement();
            String sql = "SELECT id_ciudadano, numero_identificacion, nombre, apellidos, fecha_nacimento, rh, grupo_sanguineo, sexo, requerido FROM ciudadanos where id_ciudadano = "+ id +" LIMIT 1;";
            resultQuery = st.executeQuery(sql);
            String resultado = "";
            while (resultQuery.next()) {
                ciudadano = new Ciudadanos();
                ciudadano.setId_ciudadano(Integer.parseInt(resultQuery.getString("id_ciudadano")));
                ciudadano.setNumero_identificacion(Integer.parseInt(resultQuery.getString("numero_identificacion")));
                ciudadano.setNombre(resultQuery.getString("nombre"));
                ciudadano.setApellidos(resultQuery.getString("apellidos"));
                ciudadano.setFecha_nacimento(resultQuery.getString("fecha_nacimento"));
                ciudadano.setRh(resultQuery.getString("rh"));
                ciudadano.setGrupo_sanguineo(resultQuery.getString("grupo_sanguineo"));
                ciudadano.setSexo(resultQuery.getString("sexo"));
                ciudadano.setRequerido(resultQuery.getString("requerido"));
                ciudadano.setIconImg(R.drawable.person);
            }
            st.close();
            close_conexion(connection);
        } catch (Exception e) {
            System.out.println("Errrrrrrrrrrrrrrrrrrrrrrrrrrrooooooooooooooooooooorrrrrrrrrrrrrrrrrrrr");
        }
        return ciudadano;
    }

    public boolean consultaIdentidadCiudadanos(int id) {


        try {
            int cont = 0;
            Connection connection = connect();
            st = connection.createStatement();
            String sql = "SELECT id_ciudadano, numero_identificacion, nombre FROM ciudadanos where numero_identificacion = "+ id +" LIMIT 1;";
            resultQuery = st.executeQuery(sql);
            String resultado = "";
            while (resultQuery.next()) {
                cont ++;
                System.out.println("///////////////////////////////////////////////////////////");
                System.out.println(Integer.parseInt(resultQuery.getString("id_ciudadano")));
                System.out.println(Integer.parseInt(resultQuery.getString("numero_identificacion")));
            }
            if (cont == 0){
                st.close();
                close_conexion(connection);
                return true;
            }
            st.close();
            close_conexion(connection);
            return false;
        } catch (Exception e) {
            System.out.println("Errrrrrrrrrrrrrrrrrrrrrrrrrrrooooooooooooooooooooorrrrrrrrrrrrrrrrrrrr");
            return false;
        }

    }


    public FuerzaPublica consultaClaveFuerzaPublica(String correo) {

        FuerzaPublica fuerzaPublica = null;

        try {
            Connection connection = connect();
            st = connection.createStatement();
            String sql = "SELECT id_fuerza_publica, identificacion, clave, login, nombre, apellidos, fuerza_publica, rango, id_fuerza, correo_electronico FROM personal_fuerza_publica where correo_electronico = '"+ correo +"' LIMIT 1;";
            resultQuery = st.executeQuery(sql);
            String resultado = "";
            while (resultQuery.next()) {
                fuerzaPublica = new FuerzaPublica();
                fuerzaPublica.setId_fuerza_publica(Integer.parseInt(resultQuery.getString("id_fuerza_publica")));
                fuerzaPublica.setIdentificacion(Integer.parseInt(resultQuery.getString("identificacion")));
                fuerzaPublica.setClave(resultQuery.getString("clave"));
                fuerzaPublica.setLogin(Integer.parseInt(resultQuery.getString("login")));
                fuerzaPublica.setNombre(resultQuery.getString("nombre"));
                fuerzaPublica.setApellidos(resultQuery.getString("apellidos"));
                fuerzaPublica.setFuerza_publica(resultQuery.getString("fuerza_publica"));
                fuerzaPublica.setRango(resultQuery.getString("rango"));
                fuerzaPublica.setId_fuerza(Integer.parseInt(resultQuery.getString("id_fuerza")));
                fuerzaPublica.setCorreo_electronico(resultQuery.getString("correo_electronico"));
            }
            st.close();
            close_conexion(connection);
        } catch (Exception e) {
            System.out.println("Errrrrrrrrrrrrrrrrrrrrrrrrrrrooooooooooooooooooooorrrrrrrrrrrrrrrrrrrr");
        }
        return fuerzaPublica;
    }


    public String[] insertarFuerzaPublica(int identificacionFuerza, String nombreFuerza, String apellidoFuerza, String fuerzaPublica, String rango, int idFuerza, String correo) {
        String respuesta[] = new String[2] ;
        String clave = crearClaveFuerzaPublica();
        try {
            Connection connection = connect();
            st = connection.createStatement();

            System.out.println("Claveeeeeeeeeeeeeeeeeeeeeeee");
            System.out.println(clave);
            String sql = "insert into personal_fuerza_publica (identificacion, clave, login, nombre, apellidos, fuerza_publica, rango, id_fuerza, correo_electronico) " +
                    "values ("+ identificacionFuerza +", '"+clave+"', "+ 0 + ", '" + nombreFuerza +"', '" + apellidoFuerza + "', '"+ fuerzaPublica + "', '" + rango +"', "+ idFuerza + ", '" + correo +"') ";
            System.out.println("Consulta a base sde datossssssssssss");
            System.out.println(sql);
            st.executeUpdate(sql);
            st.close();
            close_conexion(connection);
            System.out.println("Se registro correctamente la fuerza pública.");
            respuesta[0] = "Se registro correctamente la fuerza pública";
            respuesta[1] = clave;
            return respuesta;
        } catch (Exception e) {
            System.out.println("Falla: en la inserción de datos."+  e.getMessage()+ ".\n  Vuelva a intentar" );
            respuesta[0] = "Falla: en la inserción de datos."+  e.getMessage()+ ".\n  Vuelva a intentar";
            respuesta[1] = clave;
            return respuesta;
        }

    }

    public String crearClaveFuerzaPublica(){
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+-*/$%&/()";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        // cada iteración del bucle elige aleatoriamente un carácter del dado
        // rango ASCII y lo agrega a la instancia `StringBuilder`

        for (int i = 0; i < 9; i++)
        {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        return sb.toString();
    }

    //int numero_identificacion, String nombre, String apellidos, String fecha_nacimento, String rh, String grupo_sanguineo, String sexo, String requerido
    public String insertarCiudadano(int numero_identificacion, String nombres, String apellidos, String fechaNacimento, char grupoSanguineo, char rh, String sexo, String requerido) {
        try {
            Connection connection = connect();
            st = connection.createStatement();
            String sql = "insert into ciudadanos (numero_identificacion, nombre, apellidos, fecha_nacimento, grupo_sanguineo, rh, sexo, requerido) " +
                    "values ("+ numero_identificacion +", '"+nombres+"', '"+ apellidos + "', '" + fechaNacimento +"', '" + grupoSanguineo + "', '"+ rh + "', '" + sexo+"', '"+ requerido+ "') ";
            System.out.println("Consulta a base sde datossssssssssss");
            System.out.println(sql);
            st.executeQuery(sql);
            st.close();
            close_conexion(connection);
            System.out.println("Insertada correctamente el ciudadano.");
            return "El ciudadano fue insertado correctamente";
        } catch (Exception e) {
            System.out.println("Falla: en la inserción de datos."+  e.getMessage()+ ".\n  Vuelva a intentar" );
            return "Falla: en la inserción de datos."+  e.getMessage()+ ".\n  Vuelva a intentar";
        }

    }

    public String actualizarCiudadano(int id, int numero_identificacion, String nombres, String apellidos, String fechaNacimento, char grupoSanguineo, char rh, String sexo, String requerido) {
        boolean flag = false;

        try {
            Connection connection = connect();
            st = connection.createStatement();
            String sql = "update ciudadanos set numero_identificacion = "+ numero_identificacion +", nombre = '"+nombres+"', apellidos = '"+ apellidos + "', " +
                    "fecha_nacimento = '" + fechaNacimento +"', grupo_sanguineo = '" + grupoSanguineo + "', rh = '"+ rh + "', sexo = '" + sexo+"', requerido = '"+ requerido+ "' " +
                    "where id_ciudadano = "+ id +";";
            System.out.println("Consulta a base sde datossssssssssss");
            System.out.println(sql);
            st.executeUpdate(sql);
            st.close();
            close_conexion(connection);

            System.out.println("El ciudadano fue actualizado correctamente.");
            return "El ciudadano fue actualizado correctamente";
        } catch (Exception e) {
            System.out.println("Falla: en la actualización de datos."+  e.getMessage()+ ".\n  Vuelva a intentar" );
            return "Falla: en la actualización de datos."+  e.getMessage()+ ".\n  Vuelva a intentar";

        }

    }

    public String eliminarCiudadano(int id) {
        boolean flag = false;

        try {
            Connection connection = connect();
            st = connection.createStatement();
            String sql = "delete from ciudadanos where id_ciudadano = "+ id +" ;";
            System.out.println("Consulta a base sde datossssssssssss");
            System.out.println(sql);
            st.executeUpdate(sql);
            st.close();
            close_conexion(connection);

            System.out.println("El ciudadano fue eliminado correctamente.");
            return "El ciudadano fue eliminado correctamente";
        } catch (Exception e) {
            System.out.println("Falla: en la eliminado de datos."+  e.getMessage()+ ".\n  Vuelva a intentar" );
            return "Falla: en la eliminado de datos."+  e.getMessage()+ ".\n  Vuelva a intentar";

        }

    }
}
