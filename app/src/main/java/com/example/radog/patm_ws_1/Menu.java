package com.example.radog.patm_ws_1;

import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Menu extends AppCompatActivity {


    @BindView(R.id.ivFoto)
    ImageView ivFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnWeb)
    public void btnWeb() {
        Intent objWeb = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.itcelaya.edu.mx"));
        startActivity(objWeb);
    }

    @OnClick(R.id.btnLlamada)
    public void btnLlamada() {
        try {
            Intent objCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:4611714676"));
            startActivity(objCall);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btnSMS)
    public void btnSMS() {
        SmsManager objSMS = SmsManager.getDefault(); //obtener el manejador por default del manejador que trae el telefono;
        objSMS.sendTextMessage("4291096870", null, "HOLA!", null, null);
    }

    @OnClick(R.id.btnEmail)
    public void btnEmail() {
        String[] emails = {"radogan1928@hotmail.com", "13030622@itcelaya.edu.mx"};
        Intent objEmail = new Intent(Intent.ACTION_SEND);
        objEmail.putExtra(Intent.EXTRA_SUBJECT, "HOLA!!!"); //asunto
        objEmail.setType("text/plane");
        objEmail.putExtra(Intent.EXTRA_EMAIL, emails); //CONSTRUIR VARIOS CORREOS ELECTRONICOS
        objEmail.putExtra(Intent.EXTRA_TEXT, "Hola a todos");
        objEmail.putExtra(Intent.EXTRA_BCC, "13030622@itcelaya.edu.mx");
        startActivity(objEmail);
    }

    @OnClick(R.id.btnFoto)
    public void btnFoto() {
        Intent objFoto = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(objFoto, 1234);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //bitmap
        //verificar codigo de respuesta
        if (requestCode == 1234 && resultCode == RESULT_OK) {
            Bitmap foto = (Bitmap) data.getExtras().get("data");
            ivFoto.setImageBitmap(foto);
        } else {
            Toast.makeText(this, "Error en la pagina", Toast.LENGTH_LONG).show();
        }
    }
}
