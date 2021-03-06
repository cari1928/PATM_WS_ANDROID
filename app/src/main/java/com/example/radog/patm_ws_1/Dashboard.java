package com.example.radog.patm_ws_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Dashboard extends AppCompatActivity implements Response.ErrorListener, Response.Listener<String> {

    @BindView(R.id.etNombre)
    TextView etNombre;
    @BindView(R.id.etApellidoP)
    TextView etApellidoP;
    @BindView(R.id.etApellidoM)
    TextView etApellidoM;
    @BindView(R.id.etRFC)
    TextView etRFC;
    @BindView(R.id.etDireccion)
    TextView etDireccion;
    @BindView(R.id.etCorreo)
    TextView etCorreo;
    @BindView(R.id.etTelCasa)
    TextView etTelCasa;
    @BindView(R.id.etTelCel)
    TextView etTelCel;
    @BindView(R.id.rgGenero)
    RadioGroup rgGenero;

    private String usuario;
    private String pass;
    private String token;

    JSONObject jsonObject = new JSONObject();
    private RequestQueue qSolicitudes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);

        usuario = getIntent().getStringExtra("usuario");
        pass = getIntent().getStringExtra("pass");
        token = getIntent().getStringExtra("token");
    }

    //para desplegar el menú
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard, menu); //asociando el menú
        return super.onCreateOptionsMenu(menu);
    }

    //atrapar los eventos del menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemListaEmpleados:
                Intent intLista = new Intent(this, ListarEmpleados.class);
                startActivity(intLista);
                break;

            case R.id.itemAcercaDe:
                Intent intAcerca = new Intent(this, AcercaDe.class);
                startActivity(intAcerca);
                break;

            case R.id.itmProducto:
                Intent intProd = new Intent(this, Producto.class);
                startActivity(intProd);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void insClienteRequest() {
        try {
            // jsonObject.put("nombre", "Karla");
            // jsonObject.put("apellido_p", "Ramirez");
            // jsonObject.put("apellido_m", "Guerrero");
            // jsonObject.put("rfc", "KARAGU7890123");
            // jsonObject.put("direccion", "Rubensin T11");
            // jsonObject.put("correo", "karla.guerrero@hotmail.com");
            // jsonObject.put("tel_casa", "461112313");
            // jsonObject.put("tel_cel", "461112313");
            // jsonObject.put("genero", "F");
            // jsonObject.put("id_puesto", 1);

            String genero = "";
            if (rgGenero.getCheckedRadioButtonId() == R.id.rbHombre) {
                genero = "m";
            } else if (rgGenero.getCheckedRadioButtonId() == R.id.rbMujer) {
                genero = "f";
            }

            jsonObject.put("nombre", etNombre.getText().toString());
            jsonObject.put("apellido_p", etApellidoP.getText().toString());
            jsonObject.put("apellido_m", etApellidoM.getText().toString());
            jsonObject.put("rfc", etRFC.getText().toString());
            jsonObject.put("direccion", etDireccion.getText().toString());
            jsonObject.put("correo", etCorreo.getText().toString());
            jsonObject.put("tel_casa", etTelCasa.getText().toString());
            jsonObject.put("tel_cel", etTelCel.getText().toString());
            jsonObject.put("genero", genero);
            jsonObject.put("id_puesto", 1);

        } catch (JSONException e) {
            // handle exception (not supposed tohappen)
            etNombre.setText(e.toString());
        }

        //String URL = "http://192.168.100.6:8080/PATM2016/apirest/wsclientes/insertarcte/";
        //String URL = "http://10.152.194.248:8082/centro_comercial/apirest/empleado/insertar/rubensin/hola/74b377b68bb9a6795b72342d764d2caf";

        //casa
        //String URL = "http://192.168.1.67:8082/centro_comercial/apirest/empleado/insertar/" + usuario + "/" + pass + "/" + token;
        //escuela
        String URL = "http://172.20.108.81:8082/centro_comercial/apirest/empleado/insertar/" + usuario + "/" + pass + "/" + token;

        StringRequest solInsCte = new StringRequest(Request.Method.POST, URL, this, this) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return jsonObject.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    // not supposed to happen
                    etNombre.setText(uee.toString());
                    return null;
                }
            }

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
    protected void onStart() {
        super.onStart();
        qSolicitudes = Volley.newRequestQueue(this);
    }

    @OnClick(R.id.btnInsEmp)
    public void btnInsEmp() {
        insClienteRequest();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        etNombre.setText(error.toString());
    }

    @Override
    public void onResponse(String response) {

        //transformar string a jsonobject
        //JSONOBject obnjJSON = new JSONobject(response);

        try {
            etNombre.setText(response);

        } catch (Exception e) {
            etNombre.setText(e.toString());
        }
    }
}
