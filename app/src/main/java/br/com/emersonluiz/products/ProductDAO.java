package br.com.emersonluiz.products;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by emersonc on 11/21/16.
 */

public class ProductDAO extends SQLiteOpenHelper {

    private static final String DB = "ProductDB";
    private static int VERSION = 1;

    public ProductDAO(Context context) {
        super(context, DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE product (_id INTEGER PRIMARY KEY, name TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
