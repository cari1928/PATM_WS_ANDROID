package com.example.radog.patm_ws_1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Producto extends AppCompatActivity {

    BDProducto objBD; //definiendo
    //SQLiteDatabase objSQL;

    @BindView(R.id.edtNomProd) EditText edtNomProd;
    @BindView(R.id.edtCantProd) EditText edtCantProd;
    @BindView(R.id.tvMsg) TextView tvMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        ButterKnife.bind(this);

        objBD = new BDProducto(this);
        //objSQL = objBD.getWritableDatabase();
        //Toast.makeText(this, "Se insertaron categorias", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        objBD.openDB();
    }

    @Override
    protected void onStop() {
        super.onStop();
        objBD.closeDB();
    }

    @OnClick(R.id.btnSave)
    public void btnSave() {
        long resultado = objBD.insertProducto(getValue(edtNomProd), Integer.parseInt(getValue(edtCantProd)), 1);
        if (resultado == -1) {
            Toast.makeText(this, "Some error ocurred while inserting", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data inserted successfully, ID " + resultado, Toast.LENGTH_SHORT).show();
        }

        /*try {
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
        }*/
    }

    @OnClick(R.id.btnShow)
    public void btnShow() {
        StringBuffer finalData = new StringBuffer();
        Cursor objC = objBD.getAll("categoria");
        for (objC.moveToFirst(); !objC.isAfterLast(); objC.moveToNext()) {
            finalData.append(objC.getInt(objC.getColumnIndex(objBD.ID_CATEGORIA)));
            finalData.append(" - ");
            finalData.append(objC.getString(objC.getColumnIndex(objBD.NOMBRE_CATEGORIA)));
            finalData.append("\n");
        }

        tvMsg.setText(finalData);
    }

    private String getValue(EditText et) {
        return et.getText().toString().trim();
    }
}
