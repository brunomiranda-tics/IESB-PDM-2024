package com.example.appemptyviewsactivity1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "LOJA_DB";
    private static final String TB_CLIENTES = "clientes";
    private static final String KEY_ID = "id";
    private static final String NOME = "nome";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TB_CLIENTES = "CREATE TABLE " + TB_CLIENTES + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + NOME + " TEXT )";
        db.execSQL(CREATE_TB_CLIENTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_CLIENTES);
        onCreate(db);
    }

    public void addCliente(Cliente cliente){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(NOME, cliente.getNome());

        db.insert(TB_CLIENTES, null,contentValues);
        db.close();
    }

    public Cliente getCliente(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TB_CLIENTES, new String[] { KEY_ID,
                        NOME}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Cliente cliente = new Cliente(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));

        return cliente;
    }

    public List<Cliente> getAllClientes(){
        List<Cliente> ltClientes = new ArrayList<Cliente>();

        String selectQuery = "SELECT  * FROM " + TB_CLIENTES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Cliente cliente = new Cliente();
                cliente.setId(Integer.parseInt(cursor.getString(0)));
                cliente.setNome(cursor.getString(1));
                ltClientes.add(cliente);
            } while (cursor.moveToNext());
        }

        // return contact list
        return ltClientes;
    }

    public int getCountClientes() {
        String countQuery = "SELECT  * FROM " + TB_CLIENTES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public int updateCliente(Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NOME, cliente.getNome());

        // updating row
        return db.update(TB_CLIENTES, values, KEY_ID + " = ?",
                new String[] { String.valueOf(cliente.getId())});
    }
    public void deleteContact(Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TB_CLIENTES, KEY_ID + " = ?",
                new String[] { String.valueOf(cliente.getId())});
        db.close();
    }
}
