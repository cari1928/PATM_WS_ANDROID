package com.example.radog.patm_ws_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    @BindView(R.id.etUser) EditText edtUsuario;
    @BindView(R.id.etPass) EditText edtpassword;

    private RequestQueue qSolicitudes;
    private String usuario;
    private String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        qSolicitudes = Volley.newRequestQueue(this);
    }

    @OnClick(R.id.btnLogin)
    public void btnLogin() {
        valUsuarioRequest();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        edtUsuario.setText(error.toString());
    }

    private void valUsuarioRequest()
    {
        usuario = edtUsuario.getText().toString();
        pass = edtpassword.getText().toString();

        String URL = "http://172.20.108.24:8082/centro_comercial/apirest/usuario/validar/"+ usuario+"/" + pass;

        JsonObjectRequest solValCte = new JsonObjectRequest(Request.Method.GET,URL,this,this){
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
        qSolicitudes.add(solValCte);
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            String jsonResponse = "";
            String token = response.getString("token");

            //para pruebas
            //jsonResponse += "Token: " + token + "\n\n";
            //edtUsuario.setText(jsonResponse);

            if(!token.equals("no_valido")) {
                //insertar-funciona
                //Intent intDash = new Intent(this, Dashboard.class);

                //actualizar-pruebas
                Intent intDash = new Intent(this, UpdateActivity.class);

                intDash.putExtra("usuario", usuario);
                intDash.putExtra("pass", pass);
                intDash.putExtra("token", token);
                startActivity(intDash);
            }

        } catch (Exception e) {
            edtUsuario.setText(e.toString());
        }
    }
}

