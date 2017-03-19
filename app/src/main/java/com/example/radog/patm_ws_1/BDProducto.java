package com.example.radog.patm_ws_1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by radog on 16/03/2017.
 */

public class BDProducto extends SQLiteOpenHelper {

    SQLiteDatabase myDB;

    private static final String DB_NAME = "productos.db";
    private static final int VERSION = 1;

    private static final String TABLE_CATEGORIA = "categoria";
    public static final String ID_CATEGORIA = "idCat";
    public static final String NOMBRE_CATEGORIA = "nomCat";

    private static final String TABLE_PRODUCTO = "producto";
    public static final String ID_PRODUCTO = "idProd";
    public static final String NOMBRE_PRODUCTO = "nomProd";
    public static final String CANTIDAD_PRODUCTO = "cantProd";

    private static final String CREATE_TABLE_CATEGORIA = "CREATE TABLE " + TABLE_CATEGORIA + "("
            + ID_CATEGORIA + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NOMBRE_CATEGORIA + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_PRODUCTO = "CREATE TABLE " + TABLE_PRODUCTO + "("
            + ID_PRODUCTO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NOMBRE_PRODUCTO + " TEXT NOT NULL, "
            + CANTIDAD_PRODUCTO + " INTEGER NOT NULL, "
            + ID_CATEGORIA + " INTEGER NOT NULL, " +
            "CONSTRAINT fk_cant FOREIGN KEY(" + ID_CATEGORIA + ") REFERENCES " + TABLE_CATEGORIA + "(" + ID_CATEGORIA + ") );";

//    String bd1 = "CREATE TABLE categoria(idCant INTEGER PRIMARY KEY, nomCant TEXT )";
//    String bd2 = "CREATE TABLE producto( idProd INTEGER PRIMARY KEY, nomProd TEXT, cantProd INTEGER,  idCant INTEGER, " +
//            "CONSTRAINT fk_cant FOREIGN KEY(idCant) REFERENCES categoria(idCant) )";

    //name = nombre base de datos
    public BDProducto(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CATEGORIA);
        db.execSQL(CREATE_TABLE_PRODUCTO);

        /*db.execSQL("INSERT INTO categoria(nomCant) VALUES('BEBIDAS')");
        db.execSQL("INSERT INTO categoria(nomCant) VALUES('LIMPIEZA')");
        db.execSQL("INSERT INTO categoria(nomCant) VALUES('SALUD Y BELLEZA')");
        db.execSQL("INSERT INTO categoria(nomCant) VALUES('CARNES FRÍAS')");
        db.execSQL("INSERT INTO categoria(nomCant) VALUES('PANADERÍA')");
        db.execSQL("INSERT INTO categoria(nomCant) VALUES('LÁCTEOS')");
        db.execSQL("INSERT INTO categoria(nomCant) VALUES('ELECTRODOMÉSTICOS')");
        db.execSQL("INSERT INTO categoria(nomCant) VALUES('ELECTRÓNICA')");*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDB() {
        myDB = getWritableDatabase();
    }

    public void closeDB() {
        if (myDB != null && myDB.isOpen()) {
            myDB.close();
        }
    }

    public long insertCategorias(String nomCat) {
        ContentValues objCV = new ContentValues();

        objCV.put(NOMBRE_CATEGORIA, nomCat);

        return myDB.insert(TABLE_CATEGORIA, null, objCV);
    }

    public long insertProducto(String nomProd, int cantProd, int idCategoria) {
        ContentValues objCV = new ContentValues();

        objCV.put(NOMBRE_PRODUCTO, nomProd);
        objCV.put(CANTIDAD_PRODUCTO, cantProd);
        objCV.put(ID_CATEGORIA, idCategoria);

        return myDB.insert(TABLE_PRODUCTO, null, objCV);
    }

    public Cursor getAll(String table_name) {
        String query = "SELECT * FROM " + table_name;
        return myDB.rawQuery(query, null);
    }
}
