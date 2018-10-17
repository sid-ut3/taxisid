package fr.univ_tlse3.taxisid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import fr.univ_tlse3.taxisid.API.taxiapi;
import fr.univ_tlse3.taxisid.model.Station;
import fr.univ_tlse3.taxisid.model.StationModel;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class info_station extends Activity implements View.OnClickListener{

    private String API = Global.API;
    private List<Station> data;
    private boolean ok = false;
    private Global global;

    /**
     * Méthode lancée à la création de l'activité info_station
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_station);

        global = (Global) getApplicationContext();



        // Utilisation de Retrofit
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(API).build();
        // On créé un adapter pour retrofit avec l'url de base
        taxiapi api = restAdapter.create(taxiapi.class);

        // Requète de récupération des stations
        api.getFeedStations(new Callback<StationModel>() {
            // Méthode executée en cas de succès de la requète
            @Override
            public void success(StationModel model, Response response) {
                if (model.getStatut().equals("succes")) {
                    data = model.getData();
                    Log.i("SUCCESS", "Size : " + data.size());
                    TableLayout table = (TableLayout) findViewById(R.id.station_table_content);
                    // Insertion des infos dans le tableau
                    for (final Station s : data) {
                        // Nouvelle ligne dans le tableau
                        TableRow tr = new TableRow(info_station.this);
                        tr.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                global.setCurrentStation(s);
                                startActivity(new Intent(info_station.this, population_station.class));
                            }
                        });
                        tr.setOnLongClickListener(new View.OnLongClickListener() {
                            public boolean onLongClick(View v) {
                                global.setCurrentStation(s);
                                startActivity(new Intent(info_station.this, reserv_station.class));
                                return false;
                            }
                        });
                        //Ajout du numéro de la station dans la ligne
                        TextView t1 = new TextView(info_station.this);
                        t1.setText("      " + s.getNom());
                        t1.setTextSize(26);

                        TextView t2 = new TextView(info_station.this);
                        t2.setText("             " + s.getConducteurs().size());
                        t2.setTextSize(26);


                        TextView t3 = new TextView(info_station.this);
                        t3.setText("           1-1 (0)");
                        t3.setTextSize(26);

                        tr.addView(t1);
                        tr.addView(t2);
                        tr.addView(t3);
                        table.addView(tr);

                    }
                } else {
                    Log.i("FAILURE", "status =" + model.getStatut());
                }
            }

            // Méthode executée en cas d'erreur de la requète
            @Override
            public void failure(RetrofitError error) {
                Log.i("FAILURE", error.getMessage());
            }
        });
        
        TextView tv2 = (TextView) findViewById(R.id.titre);
        tv2.setText(getResources().getString(R.string.title_activity_info_station));

        // Menu
        ImageButton buttonmenu1 = (ImageButton) findViewById(R.id.imagebouton1);
        ImageButton buttonmenu2 = (ImageButton) findViewById(R.id.imagebouton2);
        ImageButton buttonmenu3 = (ImageButton) findViewById(R.id.imagebouton3);
        Button buttonrserv = (Button) findViewById(R.id.reserv);
        buttonmenu1.getBackground().setColorFilter(0x000, PorterDuff.Mode.SCREEN);
        buttonmenu2.getBackground().setColorFilter(0x000, PorterDuff.Mode.SCREEN);
        buttonmenu3.getBackground().setColorFilter(0x000, PorterDuff.Mode.SCREEN);
        ImageButton buttonmenu4 = (ImageButton) findViewById(R.id.imagebouton4);
        buttonmenu4.getBackground().setColorFilter(0xFFFEC418, PorterDuff.Mode.SCREEN);
        buttonmenu1.setOnClickListener(this);
        buttonmenu2.setOnClickListener(this);
        buttonmenu3.setOnClickListener(this);
        buttonrserv.setOnClickListener(this);

    }

    // Actions du menu
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imagebouton3:
                startActivity(new Intent(info_station.this, message_centrale.class));
                break;
            case R.id.reserv:
                startActivity(new Intent(info_station.this, info_reservation.class));
                break;
            case R.id.imagebouton1:
                startActivity(new Intent(info_station.this, MainActivity.class));
                break;
            case R.id.imagebouton2:
                SharedPreferences settings = getSharedPreferences("config", 0);
                String item = settings.getString("statut", getResources().getString(R.string.statut_libre));
                switch(item){
                    case "Charge" :
                        startActivity(new Intent(info_station.this, course_actuelle.class));
                        break;
                    case "Destination" :
                        startActivity(new Intent(info_station.this, course_actuelle_depot.class));
                        break;
                    default :
                        Toast.makeText(this, "Pas de course actuellement", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
        }
    }

}
