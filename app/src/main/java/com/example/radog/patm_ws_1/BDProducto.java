package com.example.radog.patm_ws_1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by radog on 16/03/2017.
 */

public class BDProducto extends SQLiteOpenHelper {

    String bd1 = "CREATE TABLE categoria(idCant INTEGER PRIMARY KEY, nomCant varchar(50) )";
    String bd2 = "CREATE TABLE producto( idProd INTEGER PRIMARY KEY, nomProd varchar(50), cantProd INTEGER,  idCant INTEGER, " +
            "CONSTRAINT fk_cant FOREIGN KEY(idCant) REFERENCES categoria(idCant) )";

    //name = nombre base de datos
    public BDProducto(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(bd1);
        db.execSQL(bd2);


        db.execSQL("INSERT INTO categoria(nomCant) VALUES('BEBIDAS')");
        db.execSQL("INSERT INTO categoria(nomCant) VALUES('LIMPIEZA')");
        db.execSQL("INSERT INTO categoria(nomCant) VALUES('SALUD Y BELLEZA')");
        db.execSQL("INSERT INTO categoria(nomCant) VALUES('CARNES FRÍAS')");
        db.execSQL("INSERT INTO categoria(nomCant) VALUES('PANADERÍA')");
        db.execSQL("INSERT INTO categoria(nomCant) VALUES('LÁCTEOS')");
        db.execSQL("INSERT INTO categoria(nomCant) VALUES('ELECTRODOMÉSTICOS')");
        db.execSQL("INSERT INTO categoria(nomCant) VALUES('ELECTRÓNICA')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
