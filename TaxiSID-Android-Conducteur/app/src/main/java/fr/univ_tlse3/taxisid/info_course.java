package fr.univ_tlse3.taxisid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fr.univ_tlse3.taxisid.API.taxiapi;
import fr.univ_tlse3.taxisid.model.Proposition;
import fr.univ_tlse3.taxisid.model.PropositionModel;
import fr.univ_tlse3.taxisid.model.ResponseModel;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class info_course extends Activity implements View.OnClickListener {

    //*******************************
    private String API = Global.API;
    private TextView tv;
    private RestAdapter restAdapter;
    private taxiapi api;
    private Global global;
    private Proposition propo;
    //*******************************

    /**
     * A la creation de la page, on fait appel à la méthode onCreate de la classe mère
     * On lui attribue la page xml content_info_course2
     * il y a 2 boutons qui sont crééent sur lequel on peut cliquer
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_info_course);
        //TextView tv2 = (TextView) findViewById(R.id.titre);
        //tv2.setText(getResources().getString(R.string.title_activity_info_course));


        global = (Global)getApplicationContext();
        //initialisation bouton refuser et accepter
        Button buttonRefuser = (Button) findViewById(R.id.refuser);
        Button buttonAccepter = (Button) findViewById(R.id.accepter);
        buttonRefuser.setOnClickListener(this);
        buttonAccepter.setOnClickListener(this);

        // Utilisation de Retrofit
        restAdapter = new RestAdapter.Builder().setEndpoint(API).build();
        // On créé un adapter pour retrofit avec l'url de base
        api = restAdapter.create(taxiapi.class);

        if(global.getCourseCourante() != null){
            propo = global.getCourseCourante();
        }else{
            Log.e("RECUP COURSE", "ON");
            SharedPreferences settings = getSharedPreferences("config", 0);
            String idCourse = settings.getString("idCourse", null);
            api.getRecupererCourse(Integer.parseInt(idCourse), new Callback<PropositionModel>() {
                @Override
                public void success(PropositionModel propositionModel, Response response) {
                    if(propositionModel.getStatut().equals("succes")){
                        propo = propositionModel.getData().get(0);
                        global.setCourseCourante(propo);
                    }else{
                        Log.e("RECUP COURSE","STAUTS = ECHEC");
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("RECUP COURSE","FAILURE");
                }
            });
        }
        
        TextView heure_depart = (TextView) findViewById(R.id.tv_heure_depart);
        heure_depart.setText(""+propo.getDebut());
        TextView adresse_depart = (TextView) findViewById(R.id.tv_adresse_depart);
        adresse_depart.setText(""+propo.getDepart());
        TextView adresse_arrivee = (TextView) findViewById(R.id.tv_adresse_arrivee);
        adresse_arrivee.setText(""+propo.getArrivee());
        TextView nom_client = (TextView) findViewById(R.id.tv_num_client);
        nom_client.setText(""+propo.getUtilisateur());


//*******************************************************************
    }


    /**
     * Méthode qui permet d'être redirigé quand on clique sur un des deux boutons
     *
     * @param v
     */
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.refuser:
                SharedPreferences settings = getSharedPreferences("config", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("statut", getResources().getString(R.string.statut_occupe));
                editor.commit();

                api.getRefuser2(global.getTel(), global.getCourseCourante().getNumero(), new Callback<ResponseModel>() {
                    // Méthode executée en cas de succès de la requète
                    @Override
                    public void success(ResponseModel model, Response response) {
                        if (model.getStatus().equals("succes")) {
                            Log.i("Success", "Success Non2");
                        } else {
                            Log.e("FAILURE", "FAILURE : Success Non2");
                        }
                    }
                    // Méthode executée en cas d'erreur de la requète
                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("FAILURE", "FAILURE : Non2 " + error.getMessage());
                    }
                });
                startActivity(new Intent(info_course.this, MainActivity.class));
                break;
            case R.id.accepter:
                api.getAccepter(global.getTel(), global.getCourseCourante().getNumero(), new Callback<ResponseModel>() {
                    // Méthode executée en cas de succès de la requète
                    @Override
                    public void success(ResponseModel model, Response response) {
                        if (model.getStatus().equals("succes")) {
                            SharedPreferences settings = getSharedPreferences("config", 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("statut", getResources().getString(R.string.statut_charge));
                            editor.putString("idCourse", ""+global.getCourseCourante().getNumero());
                            editor.commit();
                            api.postMajStatut(global.getTel(), getResources().getString(R.string.statut_occupe), new Callback<ResponseModel>() {
                                // Méthode executée en cas de succès de la requète
                                @Override
                                public void success(ResponseModel model, Response response) {
                                    if (model.getStatus().equals("succes")) {
                                        Log.e("success", "envoi status ok!");
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
                            Log.i("Success", "Success Oui");
                        } else {
                            Log.e("FAILURE", "FAILURE : Success Oui");
                        }
                    }
                    // Méthode executée en cas d'erreur de la requète
                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("FAILURE", "FAILURE : Oui " + error.getMessage());
                    }
                });
                startActivity(new Intent(info_course.this, course_actuelle.class));
                break;
        }
    }
}
