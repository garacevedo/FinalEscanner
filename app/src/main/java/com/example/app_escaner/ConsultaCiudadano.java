package com.example.app_escaner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.app_escaner.DB.ConexionPG;
import com.example.app_escaner.adaptadores.ListaCiudadanosAdapter;
import com.example.app_escaner.entidades.Ciudadanos;

import java.util.*;


public class ConsultaCiudadano extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public static ConexionPG bd = new ConexionPG();
    public static ArrayList<Ciudadanos> resultadoConsultaCiudadanos;
    public ListaCiudadanosAdapter adapter;
    RecyclerView recyclerCiudadanosFilterSearch;
    SearchView txtBuscar;
    CheckBox chkBxNoRequerido, chkBxRequerido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_ciudadano);

        txtBuscar = findViewById(R.id.txtBuscar);
        chkBxRequerido = findViewById(R.id.chkBxNoRequerido);
        //chkBxNoRequerido = findViewById(R.id.chkBxNoRequerido);

        init_reclicer();
        txtBuscar.setOnQueryTextListener(this);

        System.out.println("-----------------------------------------");

    }

    public void init_reclicer() {
        resultadoConsultaCiudadanos = new ArrayList<>();

        recyclerCiudadanosFilterSearch = findViewById(R.id.recicler);
        recyclerCiudadanosFilterSearch.setLayoutManager((new LinearLayoutManager(this)));
        bd.Database();
        resultadoConsultaCiudadanos = bd.consultarCiudadanos();
        System.out.println("esoooooooooooooooooooooooooooooo");
        adapter = new ListaCiudadanosAdapter(resultadoConsultaCiudadanos);
        recyclerCiudadanosFilterSearch.setAdapter(adapter);

    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.filtrarCiudadanoBusqueda(s);
        return false;
    }
}