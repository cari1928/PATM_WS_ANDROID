package com.example.radog.patm_ws_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.data;

public class UpdateActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener {

    @BindView(R.id.etNombre) EditText etNombre;
    @BindView(R.id.etCorreo) EditText etCorreo;

    private String usuario;
    private String pass;
    private String token;

    JSONObject jsonObject = new JSONObject();
    private RequestQueue qSolicitudes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.bind(this);

        usuario = getIntent().getStringExtra("usuario");
        pass = getIntent().getStringExtra("pass");
        token = getIntent().getStringExtra("token");
    }

    @OnClick(R.id.btnUpdate)
    public void btnUpdate() {
        actEmpleadoRequest();
    }

    @Override
    protected void onStart() {
        super.onStart();
        qSolicitudes = Volley.newRequestQueue(this);
        getEmpleado();
    }

    private void getEmpleado()
    {
        //String URL = "http://192.168.100.6:8080/PATM17A/apirest/usuario/validar/rubensin/hola";
        //String URL = "http://10.152.194.248:8082/centro_comercial/apirest/usuario/validar/rubensin/hola";
        //String URL = "http://192.168.1.67:8082/centro_comercial/apirest/usuario/validar/rubensin/hola";

        //casa
        //String URL = "http://192.168.1.67:8082/centro_comercial/apirest/usuario/validar/"+ usuario+"/" + pass;
        //escuela
        String URL = "http://172.20.108.81:8082/centro_comercial/apirest/empleado/ver/25/"+ usuario+"/" + pass + "/" +token;

        StringRequest solGETCte = new StringRequest(Request.Method.GET, URL, this, this) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(
                        "Authorization",
                        String.format("Basic %s", Base64.encodeToString(
                                String.format("%s:%s", "root", "root").getBytes(), Base64.DEFAULT)));
                return params;
            }

        };
        qSolicitudes.add(solGETCte);
    }

    private void actEmpleadoRequest() {
        try {
            jsonObject.put("id", 25);
            jsonObject.put("nombre", etNombre.getText().toString());
            jsonObject.put("apellido_p", "");
            jsonObject.put("apellido_m", "");
            jsonObject.put("rfc", "");
            jsonObject.put("direccion", "");
            jsonObject.put("correo", etCorreo.getText().toString());
            jsonObject.put("tel_casa", "");
            jsonObject.put("tel_cel", "");
            jsonObject.put("genero", "");
            jsonObject.put("id_puesto", 1);

        } catch (JSONException e) {
            // handle exception (not supposed tohappen)
            etNombre.setText("ERROR jsonObject PUT: " +e.toString());
        }

        //String URL = "http://192.168.100.6:8080/PATM2016/apirest/wsclientes/insertarcte/";
        //String URL = "http://10.152.194.248:8082/centro_comercial/apirest/empleado/insertar/rubensin/hola/74b377b68bb9a6795b72342d764d2caf";

        //casa
        //String URL = "http://192.168.1.67:8082/centro_comercial/apirest/empleado/insertar/" + usuario + "/" + pass + "/" + token;
        //escuela
        String URL = "http://172.20.108.81:8082/centro_comercial/apirest/empleado/actualizar/" + usuario + "/" + pass + "/" + token;

        StringRequest solActCte = new StringRequest(Request.Method.PUT, URL, this, this) {
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
        qSolicitudes.add(solActCte);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        etNombre.setText(error.toString());
    }

    @Override
    public void onResponse(String response) {

        try {
            JSONObject objResponse = new JSONObject(response);
            String status = objResponse.getString("status");

            if(status.equals("GET")) {
                String nombre = objResponse.getString("nombre");
                String correo = objResponse.getString("correo");

                etNombre.setText(nombre);
                etCorreo.setText(correo);

            } else if(status.equals("PUT")) {
                Toast.makeText(UpdateActivity.this, "Actualizado", Toast.LENGTH_LONG).show();
            }

            //para pruebas
            //String jsonResponse = "";
            //jsonResponse += "Token: " + token + "\n\n";
            //edtUsuario.setText(jsonResponse);

        } catch (Exception e) {
            etNombre.setText(e.toString());
        }
    }
}
