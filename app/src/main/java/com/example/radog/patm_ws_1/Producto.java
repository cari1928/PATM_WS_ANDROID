package com.example.radog.patm_ws_1;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Producto extends AppCompatActivity {

    BDProducto objBD; //definiendo
    SQLiteDatabase objSQL;

    @BindView(R.id.edtNomProd)
    EditText edtNomProd;
    @BindView(R.id.edtCantProd)
    EditText edtCantProd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);

        ButterKnife.bind(this);

        objBD = new BDProducto(this, "BDPRODUCTOS", null, 1);
        objSQL = objBD.getWritableDatabase();
        Toast.makeText(this, "Se insertaron categorias", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.btnSave)
    public void btnSave() {
        try {
            String nomProd = edtNomProd.getText().toString();
            int cantProd = Integer.parseInt(edtCantProd.getText().toString());

            String insProd = "INSERT INTO producto(nomProd, cantProd, idCant) " +
                    "VALUES('" + nomProd + "'," + cantProd + ", 100000000)";

            //objSQL.execSQL("PRAGMA FOREIGN_KEYS=ON");
            objSQL.execSQL(insProd);
            //objSQL.execSQL("PRAGMA FOREIGN_KEYS=OFF");
            Toast.makeText(this, "Se insertó el producto", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
