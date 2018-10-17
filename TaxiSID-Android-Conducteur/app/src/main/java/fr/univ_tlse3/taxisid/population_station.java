package fr.univ_tlse3.taxisid;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TextView;

import java.util.List;

import fr.univ_tlse3.taxisid.API.taxiapi;
import fr.univ_tlse3.taxisid.model.AdresseModel;
import fr.univ_tlse3.taxisid.model.Conducteur;
import fr.univ_tlse3.taxisid.model.StationModel;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class population_station extends Activity {

    /**
     * activité pour fare le tableau conducteur et la durée
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_population_station);
        TableLayout table = (TableLayout) findViewById(R.id.pop_station_table_content);
        Global global = (Global) getApplicationContext();
        List<Conducteur> conducteurs = global.getCurrentStation().getConducteurs();
        for(Conducteur c : conducteurs){
            TableRow tr = new TableRow(this);
        
            TextView t1 = new TextView(this);
            TextView t2 = new TextView(this);

            t1.setText("    "+c.getConducteur());
            t1.setTextSize(26);

            t2.setText("       "+c.getAttente()+" min");
            t2.setTextSize(26);

            tr.addView(t1);
            tr.addView(t2);

            table.addView(tr);
        }
    }

}
