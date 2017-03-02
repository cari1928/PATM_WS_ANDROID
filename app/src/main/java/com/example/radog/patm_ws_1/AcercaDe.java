package com.example.radog.patm_ws_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AcercaDe extends AppCompatActivity {

    @BindView(R.id.txtCorreo) TextView txtCorreo;
    @BindView(R.id.txtTel) TextView txtTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);
        ButterKnife.bind(this);

        Linkify.addLinks(txtCorreo, Linkify.EMAIL_ADDRESSES);
        Linkify.addLinks(txtTel, Linkify.PHONE_NUMBERS);
    }
}
