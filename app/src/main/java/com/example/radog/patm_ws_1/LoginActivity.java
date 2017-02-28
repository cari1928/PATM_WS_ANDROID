package com.example.radog.patm_ws_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
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

    @BindView(R.id.etUser) TextView edtUsuario;
    @BindView(R.id.etPass) TextView edtpassword;
    //@BindView(R.id.btnValidar) Button btnValidar;
    private RequestQueue qSolicitudes;

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
        //edtUsuario.setText("Welcome");
        valUsuarioRequest();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        edtUsuario.setText(error.toString());
    }

    private void valUsuarioRequest()
    {
        //String URL = "http://192.168.100.6:8080/PATM17A/apirest/usuario/validar/rubensin/hola";
        //String URL = "http://10.152.194.248:8082/centro_comercial/apirest/usuario/validar/rubensin/hola";
        String URL = "http://192.168.1.67:8082/centro_comercial/apirest/usuario/validar/rubensin/hola";

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
            jsonResponse += "Token: " + token + "\n\n";
            edtUsuario.setText(jsonResponse);

            if(!token.equals("no_valido")) {
                //Dashboard aparte
                //Intent intDash = new Intent(this, DashBoard.class);
                Intent intDash = new Intent(this, Dashboard.class);
                startActivity(intDash);
            }

        } catch (Exception e) {
            edtUsuario.setText(e.toString());
        }
    }
}

