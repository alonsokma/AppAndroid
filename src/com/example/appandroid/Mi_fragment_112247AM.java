package com.example.appandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Mi_fragment_112247AM extends BaseFragment {
    @Override  
       public View onCreateView(LayoutInflater inflater, ViewGroup container,  
                 Bundle savedInstanceState) {  
            v = inflater.inflate(R.layout.mi_fragment_112247am, container,false);
            LlenarListView_112257AM(v); OnClickdeListView_112257AM(v);
            
            return v;//Mnemosina: No modificar esta linea   
       }   
    
} // Fin de clase Mi_fragment_112247AM