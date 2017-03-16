package com.example.radog.patm_ws_1;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by radog on 02/03/2017.
 */

public class ListarEmpleados extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener {

    @BindView(R.id.ltvEmpleado)
    ListView ltvEmpleado;

    private RequestQueue qSolicitudes;
    private ArrayAdapter<String> modelo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lista_empleados);
        ButterKnife.bind(this);

        modelo = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        qSolicitudes = Volley.newRequestQueue(this);
        listarEmp();
    }

    private void listarEmp() {
        //String URL = "http://172.20.108.81:8082/centro_comercial/apirest/empleado/listado/" + usuario + "/" + pass + "/" + token;
        String URL = "http://172.20.108.24:8082/centro_comercial/apirest/empleado/listado/rubensin/hola/8cdb9446ac61ee68ca61f60f6d40769e";

        StringRequest solInsCte = new StringRequest(Request.Method.GET, URL, this, this) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                //HashMap<String, String> headers = new HashMap<String, String>();

                //HEADERS =  encabezados para la petición

                Map<String, String> headers = new HashMap<String, String>();
                headers.put(
                        "Authorization",
                        String.format("Basic %s", Base64.encodeToString(
                                String.format("%s:%s", "root", "root").getBytes(), Base64.DEFAULT)));

                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        qSolicitudes.add(solInsCte);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {
        String puesto = "";

        try {
            JSONObject objJSON = new JSONObject(response);
            JSONArray arrEmp = objJSON.getJSONArray("empleado");

            for (int i = 0; i < arrEmp.length(); ++i) {
                JSONObject objEmp = arrEmp.getJSONObject(i);
                JSONObject objPue = new JSONObject(objEmp.getString("puesto"));

                if (objPue.length() == 2) {
                    puesto = objPue.getString("puesto");
                }

                modelo.add(objEmp.getString("nombre") + " " + puesto);
            }
            ltvEmpleado.setAdapter(modelo);

        } catch (JSONException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
