package br.com.emersonluiz.products;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ProductDAO dao;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dao = new ProductDAO(this);
        listView = (ListView) findViewById(R.id.listView);
        load();
    }

    @Override
    protected void onDestroy() {
        dao.close();
        super.onDestroy();
    }

    @Override
    protected  void onResume() {
        super.onResume();
        load();
    }

    public void add(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

    public void load() {
        List<Map<String, Object>> list = new ArrayList<>();

        SQLiteDatabase db = dao.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id, name FROM product", null);
        cursor.moveToFirst();

        for(int i=0; i<cursor.getCount(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", cursor.getInt(0));
            map.put("name", cursor.getString(1));
            list.add(map);
            cursor.moveToNext();
        }

        cursor.close();

        listView.setAdapter(new ProductAdapter(list, this));
    }
}
