package br.com.emersonluiz.products;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    private ProductDAO dao;
    private EditText name;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        dao = new ProductDAO(this);
        name = (EditText) findViewById(R.id.name);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("id");
            get(id);
        }

    }

    @Override
    protected void onDestroy() {
        dao.close();
        super.onDestroy();
    }

    public void back(View view) {
        this.finish();
    }

    public void save(View view) {

        SQLiteDatabase db = dao.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name.getText().toString());

        long result;

        if (id != 0) {
            result = db.update("product", values, "_id = ?", new String[]{String.valueOf(id)});
        } else {
            result = db.insert("product", null, values);
        }

        if(result != -1 ) {
            id = 0;
            name.setText("");
            Toast.makeText(this, getString(R.string.msg_save), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, getString(R.string.msg_error), Toast.LENGTH_SHORT).show();
        }

    }

    public void get(int _id) {
        SQLiteDatabase db = dao.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM product WHERE _id = ?", new String[] { String.valueOf(_id) });
        cursor.moveToFirst();

        name.setText(cursor.getString(0));
    }
}
