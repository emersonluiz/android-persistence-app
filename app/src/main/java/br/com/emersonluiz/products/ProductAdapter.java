package br.com.emersonluiz.products;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;

/**
 * Created by emersonc on 11/19/16.
 */

public class ProductAdapter extends BaseAdapter {

    private final List<Map<String, Object>> products;
    private final AppCompatActivity activity;
    private final ProductDAO dao;

    ProductAdapter(List<Map<String, Object>> products, AppCompatActivity activity, ProductDAO dao) {
        this.products = products;
        this.activity = activity;
        this.dao = dao;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        View v = activity.getLayoutInflater().inflate(R.layout.listview_item_row, viewGroup, false);

        Map<String, Object> item = products.get(position);

        TextView name = (TextView) v.findViewById(R.id.name);
        TextView delete = (TextView) v.findViewById(R.id.delete);

        name.setText((String) item.get("name"));

        final int id = (int) item.get("id");

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dao.getWritableDatabase();
                db.delete("product", "_id = ?", new String[] {String.valueOf(id)});
                Log.i("Remove", "Removing: " + String.valueOf(id));

                for (int i=0; i<products.size(); i++) {
                    if (products.get(i).containsValue(id)) {
                        products.remove(i);
                        break;
                    }
                }

                notifyDataSetChanged();
            }
        });

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, AddActivity.class);
                intent.putExtra("id", id);
                activity.startActivity(intent);
            }
        });

        return v;
    }
}
