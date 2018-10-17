package fr.univ_tlse3.taxisid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import fr.univ_tlse3.taxisid.API.taxiapi;
import fr.univ_tlse3.taxisid.model.Proposition;
import fr.univ_tlse3.taxisid.model.PropositionModel;
import fr.univ_tlse3.taxisid.model.ResponseModel;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class accepter_course extends Activity {

    private Global global;
    private Proposition propo;
    private CountDownTimer cdt;

    /**
     * Méthode appelée lors du lancement de l'application
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepter_course);

        ((Vibrator) getSystemService(this.VIBRATOR_SERVICE)).vibrate(1000);
        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrer 500 millisecondes
        v.vibrate(500);

        global = (Global) getApplicationContext();
        propo = global.getCourseCourante();

        TextView station = (TextView) findViewById(R.id.station_nouvelle_course);
        station.setText("" + station.getText() + propo.getDepart());

        Button boutonAccepter = (Button) findViewById(R.id.accepter_nouvelle_course);
        final Button boutonRefuser = (Button) findViewById(R.id.refuser_nouvvelle_course);

        // Bouton accepter
        boutonAccepter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdt.cancel();
                Log.i("Propo", global.getCourseCourante().getConducteur());
                startActivity(new Intent(accepter_course.this, info_course.class));
            }
        });

        // Bouton refuser
        boutonRefuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdt.onFinish();

            }
        });
        cdt = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // NE RIEN METTRE ICI!!!
            }

            @Override
            public void onFinish() {
                global.setCourseCourante(null);

                RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Global.API).build();
                // On créé un adapter pour retrofit avec l'url de base
                taxiapi api = restAdapter.create(taxiapi.class);

                SharedPreferences settings = getSharedPreferences("config", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("statut", getResources().getString(R.string.statut_occupe));
                editor.commit();

                api.getRefuser1(global.getTel(), propo.getNumero(), new Callback<ResponseModel>() {
                    // Méthode executée en cas de succès de la requète
                    @Override
                    public void success(ResponseModel model, Response response) {
                        if (model.getStatus().equals("succes")) {
                            Log.i("Success", "Success Refuser1");
                        } else {
                            Log.e("FAILURE", "Sucess = FAILURE : Refuser1");
                        }
                    }

                    // Méthode executée en cas d'erreur de la requète
                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("failure", "FAILURE : Refuser1 " + error.getMessage());
                    }
                });
                Log.i("Refuser1", "Success Refuser1");
                startActivity(new Intent(accepter_course.this, MainActivity.class));
            }
        }.start();
    }
}
