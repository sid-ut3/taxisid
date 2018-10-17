package fr.univ_tlse3.taxisid.BDD;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fr.univ_tlse3.taxisid.R;

/**
 * Created by Insa on 07/01/2016.
 */
public class AdapterMessage<K> extends ArrayAdapter<K>{

    private final Context context;
    private final ArrayList<K> itemsArrayList;

    public AdapterMessage(Context context, ArrayList<K> itemsArrayList) {

        super(context, R.layout.simplerow, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.simplerow, null);
        }
        K s = getItem(position);
        if (s != null) {
            TextView t = (TextView) v.findViewById(R.id.rowTextView);

            if(s instanceof Message){
                t.setText(((Message) s).getMessage());
            }
            else if(s instanceof Code){
                t.setText(((Code) s).getNom());
            }
        }
        return v;
    }
}
