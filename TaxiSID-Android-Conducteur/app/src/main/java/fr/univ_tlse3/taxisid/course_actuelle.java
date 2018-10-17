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

public class course_actuelle extends Activity implements View.OnClickListener {

    private Global global;
    private Proposition propo;
    private RestAdapter restAdapter;
    private taxiapi api;

    /**
     * A la creation de la page, on fait appel à la méthode onCreate de la classe mère
     * On lui attribue la page xml content_course_actuelle
     * il y a un bouton qui est créé sur lequel on peut cliquer
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_course_actuelle);
        TextView tv = (TextView) findViewById(R.id.titre);
        tv.setText(getResources().getString(R.string.title_activity_course_actuelle));

        global = (Global) getApplicationContext();


        restAdapter = new RestAdapter.Builder().setEndpoint(Global.API).build();
        // On créé un adapter pour retrofit avec l'url de base
        api = restAdapter.create(taxiapi.class);

        if (global.getCourseCourante() != null) {
            propo = global.getCourseCourante();

            //initialisation des boutons et fond de couleur
            TextView heure_depart = (TextView) findViewById(R.id.tv_heure_depart2);
            heure_depart.setText("" + propo.getDebut());
            TextView adresse_depart = (TextView) findViewById(R.id.tv_adresse_depart2);
            adresse_depart.setText("" + propo.getDepart());
            TextView nom_client = (TextView) findViewById(R.id.tv_num_client2);
            nom_client.setText("" + propo.getUtilisateur());
            TextView nb_personnes = (TextView) findViewById(R.id.tv_nb_personnes);
            nb_personnes.setText("" + propo.getPlaces());
            TextView animaux = (TextView) findViewById(R.id.tv_animaux);
            animaux.setText(propo.getAnimaux() + " / " + propo.getAnimauxGrands());
            TextView bagages = (TextView) findViewById(R.id.tv_bagage);
            bagages.setText("" + propo.getBagages());

        } else {
            Log.e("RECUP COURSE", "ON");
            SharedPreferences settings = getSharedPreferences("config", 0);
            String idCourse = settings.getString("idCourse", null);
            api.getRecupererCourse(Integer.parseInt(idCourse), new Callback<PropositionModel>() {
                @Override
                public void success(PropositionModel propositionModel, Response response) {
                    if (propositionModel.getStatut().equals("succes")) {
                        propo = propositionModel.getData().get(0);
                        global.setCourseCourante(propo);
                        //initialisation des boutons et fond de couleur
                        TextView heure_depart = (TextView) findViewById(R.id.tv_heure_depart2);
                        heure_depart.setText("" + propo.getDebut());
                        TextView adresse_depart = (TextView) findViewById(R.id.tv_adresse_depart2);
                        adresse_depart.setText("" + propo.getDepart());
                        TextView nom_client = (TextView) findViewById(R.id.tv_num_client2);
                        nom_client.setText("" + propo.getUtilisateur());
                        TextView nb_personnes = (TextView) findViewById(R.id.tv_nb_personnes);
                        nb_personnes.setText("" + propo.getPlaces());
                        TextView animaux = (TextView) findViewById(R.id.tv_animaux);
                        animaux.setText(propo.getAnimaux() + " / " + propo.getAnimauxGrands());
                        TextView bagages = (TextView) findViewById(R.id.tv_bagage);
                        bagages.setText("" + propo.getBagages());

                    } else {
                        Log.e("RECUP COURSE", "STAUTS = ECHEC");
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("RECUP COURSE", "FAILURE");
                }
            });
        }


        Button buttonOne = (Button) findViewById(R.id.btn_client_recup);
        buttonOne.setOnClickListener(this);
        ImageButton buttonmenu1 = (ImageButton) findViewById(R.id.imagebouton1);
        buttonmenu1.getBackground().setColorFilter(0x000, PorterDuff.Mode.SCREEN);
        ImageButton buttonmenu2 = (ImageButton) findViewById(R.id.imagebouton2);
        buttonmenu2.getBackground().setColorFilter(0xFFFEC418, PorterDuff.Mode.SCREEN);
        ImageButton buttonmenu3 = (ImageButton) findViewById(R.id.imagebouton3);
        buttonmenu3.getBackground().setColorFilter(0x000, PorterDuff.Mode.SCREEN);
        ImageButton buttonmenu4 = (ImageButton) findViewById(R.id.imagebouton4);
        buttonmenu4.getBackground().setColorFilter(0x000, PorterDuff.Mode.SCREEN);
        buttonmenu1.setOnClickListener(this);
        buttonmenu2.setOnClickListener(this);
        buttonmenu3.setOnClickListener(this);
        buttonmenu4.setOnClickListener(this);
    }

    /**
     * cette méthode permet lorsqu'on clique sur le bouton client récupéré, nous sommes redirigés sur la page course_actuelle_depot
     * et il y a un toast qui s'affiche
     *
     * @param v
     */

    public void onClick(View v) {
        SharedPreferences settings = getSharedPreferences("config", 0);
        switch (v.getId()) {
            case R.id.btn_client_recup:
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("idCourse", ""+global.getCourseCourante().getNumero());
                editor.putString("statut", getResources().getString(R.string.statut_destination));
                editor.commit();

                api.postMajStatut(global.getTel(), getResources().getString(R.string.statut_destination), new Callback<ResponseModel>() {
                    // Méthode executée en cas de succès de la requète
                    @Override
                    public void success(ResponseModel model, Response response) {
                        if (model.getStatus().equals("success")) {
                            Log.i("success", "envoi status ok!");
                        } else {
                            Log.e("FAILURE", "FAILURE MAJ STATUT");
                        }
                    }

                    // Méthode executée en cas d'erreur de la requète
                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("CONNECTION ECHOUEE", "Connection Echouée ! : " + error.getMessage());
                    }
                });
                startActivity(new Intent(course_actuelle.this, course_actuelle_depot.class));
                break;
            // action des boutons
            case R.id.imagebouton4:
                startActivity(new Intent(course_actuelle.this, info_station.class));
                break;
            case R.id.imagebouton3:
                startActivity(new Intent(course_actuelle.this, message_centrale.class));
                break;
            case R.id.imagebouton1:
                startActivity(new Intent(course_actuelle.this, MainActivity.class));
                break;
            case R.id.imagebouton2:
                String item = settings.getString("statut", getResources().getString(R.string.statut_libre));
                switch (item) {
                    case "Charge":
                        startActivity(new Intent(course_actuelle.this, course_actuelle.class));
                        break;
                    case "Destination":
                        startActivity(new Intent(course_actuelle.this, course_actuelle_depot.class));
                        break;
                    default:
                        Toast.makeText(this, "Pas de course actuellement", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
        }
    }
}
