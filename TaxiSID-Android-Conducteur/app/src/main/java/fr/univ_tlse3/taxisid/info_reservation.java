package fr.univ_tlse3.taxisid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
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

public class info_reservation extends Activity implements View.OnClickListener {

    private String API = Global.API;
    private Global global;
    // TODO : API Résas
    private List<Station> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                if (model.getStatut().equals("success")) {
                    data = model.getData();
                    Log.i("SUCCESS", "Size : " + data.size());
                    TableLayout table = (TableLayout) findViewById(R.id.reservation_table);
                    for (Station s : data) {
                        // Nouvelle ligne dans le tableau
                        TableRow tr = new TableRow(info_reservation.this);
                        tr.setOnClickListener(new View.OnClickListener(){
                            public void onClick(View v) {
                                startActivity(new Intent(info_reservation.this, horaire_reservation.class));

                            }
                        });
                        //Ajout du numéro de la station dans la ligne
                        TextView t1 = new TextView(info_reservation.this);
                        t1.setText("" + s.getNom());

                        TextView t2 = new TextView(info_reservation.this);
                        t2.setText("1");

                        tr.addView(t1);
                        tr.addView(t2);
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

        setContentView(R.layout.activity_info_reservation);
        //initialisation du texte
        TextView tv = (TextView) findViewById(R.id.titre);
        tv.setText(getResources().getString(R.string.title_activity_info_reservation));
        //initialisation buton
        ImageButton buttonmenu1 = (ImageButton) findViewById(R.id.imagebouton1);
        buttonmenu1.getBackground().setColorFilter(0x000, PorterDuff.Mode.SCREEN);
        ImageButton buttonmenu2 = (ImageButton) findViewById(R.id.imagebouton2);
        buttonmenu2.getBackground().setColorFilter(0x000, PorterDuff.Mode.SCREEN);
        ImageButton buttonmenu3 = (ImageButton) findViewById(R.id.imagebouton3);
        buttonmenu3.getBackground().setColorFilter(0x000, PorterDuff.Mode.SCREEN);
        ImageButton buttonmenu4 = (ImageButton) findViewById(R.id.imagebouton4);
        buttonmenu4.getBackground().setColorFilter(0xFFFEC418, PorterDuff.Mode.SCREEN);
        buttonmenu1.setOnClickListener(this);
        buttonmenu3.setOnClickListener(this);
        buttonmenu2.setOnClickListener(this);
        buttonmenu4.setOnClickListener(this);

    }

    /** méthode pour faire l'action des boutons
     *
     * @param v
     */

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imagebouton4:
                startActivity(new Intent(info_reservation.this, info_station.class));
                break;
            case R.id.imagebouton3:
                startActivity(new Intent(info_reservation.this, message_centrale.class));
                break;
            case R.id.imagebouton1:
                startActivity(new Intent(info_reservation.this, MainActivity.class));
                break;
            case R.id.imagebouton2:
                SharedPreferences settings = getSharedPreferences("config", 0);
                String item = settings.getString("statut", getResources().getString(R.string.statut_libre));
                switch(item){
                    case "Charge" :
                        startActivity(new Intent(info_reservation.this, course_actuelle.class));
                        break;
                    case "Destination" :
                        startActivity(new Intent(info_reservation.this, course_actuelle_depot.class));
                        break;
                    default :
                        Toast.makeText(this, "Pas de course actuellement", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
        }
    }
}
