package es.jordan.sistemaalarma;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemAlarmaAdapter extends BaseAdapter {
     Activity activity;
     ArrayList<itemAlarma> items;


    public ItemAlarmaAdapter(Activity activity, ArrayList<itemAlarma> items) {
        this.activity = activity;
        this.items = items;
    }

    public ItemAlarmaAdapter() {
    }

    @Override
    public int getCount() {
        return items.size();
    }


    @Override
    public Object getItem(int position) {
        return items.get(position);
    }


    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.list_item_layout, null);
        }

        itemAlarma item = items.get(position);

        ImageView image = vi.findViewById(R.id.imagen);
        int imageResource = activity.getResources().getIdentifier(item.getRutaImagen(), null, activity.getPackageName());
        image.setImageDrawable(activity.getResources().getDrawable(imageResource));

        TextView nombre = vi.findViewById(R.id.nombre);
        nombre.setText(item.getNombre());

        TextView tipo = vi.findViewById(R.id.tipo);
        tipo.setText(item.getTipo());

        return vi;
    }
}
