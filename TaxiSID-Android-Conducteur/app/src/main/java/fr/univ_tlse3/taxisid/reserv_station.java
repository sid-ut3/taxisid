package fr.univ_tlse3.taxisid;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import fr.univ_tlse3.taxisid.API.taxiapi;
import fr.univ_tlse3.taxisid.model.ReservStation;
import fr.univ_tlse3.taxisid.model.ReservStationModel;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class reserv_station extends Activity {
    /**
     * activité pour avoir un tableau des reservations par heure
     */
    Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserv_station);
        global = (Global) getApplicationContext();

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Global.API).build();
        // On créé un adapter pour retrofit avec l'url de base
        taxiapi api = restAdapter.create(taxiapi.class);

        api.getFeedReservStations(global.getCurrentStation().getNom(), new Callback<ReservStationModel>() {
            @Override
            public void success(ReservStationModel model, Response response) {
                if(model.getStatut().equals("succes")){
                    TableLayout table = (TableLayout) findViewById(R.id.reserv_station_table_content);
                    List<ReservStation> data = model.getData();
                    TextView tv_nb_reserv = (TextView) findViewById(R.id.reserv_station_table_nb_reserv);
                    tv_nb_reserv.setText(tv_nb_reserv.getText() + "" + data.size());
                    if(data.size() != 0){
                        for (ReservStation rs : data) {
                            TableRow tr = new TableRow(reserv_station.this);

                            TextView t1 = new TextView(reserv_station.this);
                            t1.setText("" + rs.getDebut());
                            t1.setTextSize(20);

                            tr.addView(t1);

                            table.addView(tr);
                        }
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

}
