package com.example.radog.patm_ws_1;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by radog on 02/03/2017.
 */

public class ListarEmpleados extends AppCompatActivity {

    @BindView(R.id.ltvEmpleado) ListView ltvEmpleado;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lista_empleados);
        ButterKnife.bind(this);
    }
}
