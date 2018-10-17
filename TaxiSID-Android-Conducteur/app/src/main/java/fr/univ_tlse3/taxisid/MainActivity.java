package fr.univ_tlse3.taxisid;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.List;

import fr.univ_tlse3.taxisid.API.taxiapi;
import fr.univ_tlse3.taxisid.BDD.LiaisonDataBase;
import fr.univ_tlse3.taxisid.BDD.Message;
import fr.univ_tlse3.taxisid.Helpers.PropositionRunnable;
import fr.univ_tlse3.taxisid.model.JSONMessage;
import fr.univ_tlse3.taxisid.model.MessageModel;
import fr.univ_tlse3.taxisid.model.Proposition;
import fr.univ_tlse3.taxisid.model.PropositionModel;
import fr.univ_tlse3.taxisid.model.ResponseModel;
import fr.univ_tlse3.taxisid.model.TelImeiModel;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {


    private LiaisonDataBase bdd;
    private Button statut;
    private String item;

    private SharedPreferences settings;

    // Elements Retrofit
    private RestAdapter restAdapter;
    private taxiapi api;

    private Location mLastLocation;

    private LocationRequest locationRequest;
    private FusedLocationProviderApi fusedLocationProviderApi;
    private GoogleApiClient googleApiClient;

    AlertDialog alertDialog;
    CountDownTimer cdt;
    Global global;

    /**
     * Méthode appelée lors du lancement de l'application
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        TextView tv = (TextView) findViewById(R.id.titre);
        tv.setText(getResources().getString(R.string.title_activity_MainActivity));

        global = ((Global) getApplicationContext());

        this.restAdapter = new RestAdapter.Builder().setEndpoint(Global.API).build();
        // On créé un adapter pour retrofit avec l'url de base
        this.api = restAdapter.create(taxiapi.class);

        // ouverture bdd
        bdd = new LiaisonDataBase(this);
        bdd.open();

        int permissionCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        // Vérifie la permission d'accéder au GPS
        if (permissionCheck2 != PackageManager.PERMISSION_GRANTED) {
            // Demande d'acceder au GPS si non accordée
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {
                // On met du code ici si on a besoin de bloquer le thread le temps de
                // demander la permission pour acceder au GPS
            } else {
                // On demande la permission sans bloquer le thread
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        2);
            }
        }
        if (permissionCheck2 == PackageManager.PERMISSION_GRANTED) {
            //Execute l'appel au service de localisation si l'utilisateur a donnée l'authorisation ACCESS_FINE_LOCATION
            if(global.getTel() == null){
                String imei = emei();

                api.getTelImei(imei, new Callback<TelImeiModel>() {
                    @Override
                    public void success(TelImeiModel model, Response response) {
                        if(model.getStatut().equals("succes")){
                            Log.i("IMEI", "SUCCES");
                            if(model.getData().size() == 0){
                                AlertDialog alertImeiDialog = new AlertDialog.Builder(MainActivity.this).setTitle("Téléphone Inconnu")
                                        .setMessage("Veuillez contacter la centrale")
                                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                MainActivity.this.finish();
                                                System.exit(0);
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setCancelable(false)
                                        .show();
                            }else{
                                TextView nom_taxi = (TextView) findViewById(R.id.nom_taxi);
                                String tel = model.getData().get(0).getTelephone();
                                nom_taxi.setText(nom_taxi.getText() + " " + tel);
                                global.setTel(tel);
                            }
                        }else {
                            Log.e("IMEI", "Staut = echec");
                            AlertDialog alertImeiDialog = new AlertDialog.Builder(MainActivity.this).setTitle("Téléphone Inconnu")
                                    .setMessage("Veuillez contacter la centrale")
                                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            MainActivity.this.finish();
                                            System.exit(0);
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setCancelable(false)
                                    .show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.i("IMEI", "FAILURE");
                        AlertDialog alertImeiDialog = new AlertDialog.Builder(MainActivity.this).setTitle("Erreur")
                                .setMessage("Impossible de contacter le serveur distant.\nVeuillez vérifier votre connexion internet.")
                                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        MainActivity.this.finish();
                                        System.exit(0);
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setCancelable(false)
                                .show();
                    }
                });
            }else{
                TextView nom_taxi = (TextView) findViewById(R.id.nom_taxi);
                nom_taxi.setText(nom_taxi.getText() + " " + global.getTel());
            }
        }

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        // Vérifie la permission d'accéder au GPS
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // Demande d'acceder au GPS si non accordée
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // On met du code ici si on a besoin de bloquer le thread le temps de
                // demander la permission pour acceder au GPS
            } else {
                // On demande la permission sans bloquer le thread
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            }
        }
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            //Execute l'appel au service de localisation si l'utilisateur a donnée l'authorisation ACCESS_FINE_LOCATION
            getLocation();
        }

        // Bouton Modifier Statut et Choisir Course

        Button besoin = (Button) findViewById(R.id.besoins);
        besoin.setOnClickListener(this);

        ImageButton buttonmenu1 = (ImageButton) findViewById(R.id.imagebouton1);
        buttonmenu1.getBackground().setColorFilter(0xFFFEC418, PorterDuff.Mode.SCREEN);
        ImageButton buttonmenu2 = (ImageButton) findViewById(R.id.imagebouton2);
        buttonmenu2.getBackground().setColorFilter(0x000, PorterDuff.Mode.SCREEN);
        ImageButton buttonmenu3 = (ImageButton) findViewById(R.id.imagebouton3);
        buttonmenu3.getBackground().setColorFilter(0x000, PorterDuff.Mode.SCREEN);
        ImageButton buttonmenu4 = (ImageButton) findViewById(R.id.imagebouton4);
        buttonmenu4.getBackground().setColorFilter(0x000, PorterDuff.Mode.SCREEN);
        buttonmenu2.setOnClickListener(this);
        buttonmenu3.setOnClickListener(this);
        buttonmenu4.setOnClickListener(this);


        // Récupération du statut dans les paramètres de l'application
         settings = getSharedPreferences("config", 0);
         item = settings.getString("statut", getResources().getString(R.string.statut_libre));
         statut = (Button) findViewById(R.id.statut);
        statut.setText(item);

        settings = getSharedPreferences("config", 0);
        item = settings.getString("statut", getResources().getString(R.string.statut_libre));
        statut = (Button) findViewById(R.id.statut);
        if (statut.getText().equals(getResources().getString(R.string.statut_occupe))) {

            statut.setTextColor(ContextCompat.getColor(this, R.color.occupe));

        } else if (statut.getText().equals(getResources().getString(R.string.statut_libre))) {


            statut.setTextColor(ContextCompat.getColor(this, R.color.libre));

        }
        statut.setOnClickListener(this);

        // Affichage du statut et sa couleur associée dans le textView
        statut.setText(item);

if(statut.getText().equals(getResources().getString(R.string.statut_charge)) || statut.getText().equals(getResources().getString(R.string.statut_destination)) ) {
    statut.setEnabled(false);
}else if(statut.getText().equals(getResources().getString(R.string.statut_libre)) || statut.getText().equals(getResources().getString(R.string.statut_occupe)) ) {
    statut.setEnabled(true);
}
     // fermeture bdd
        bdd.close();

    }
//stocke en config le numero imei
    public String emei() {
        String ts = Context.TELEPHONY_SERVICE;
        TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(ts);
        String imei = mTelephonyMgr.getDeviceId();

        Log.e("imei", imei);
        // TODO : Choisir si on met dans config ou var globale
        global.setImei(imei);

        new BackgroundTask().execute("");
        SharedPreferences settings1 = getSharedPreferences("config", 0);
        SharedPreferences.Editor editor = settings1.edit();
        editor.putString("imei", imei);
        editor.commit();

        return imei;
    }

    /**
     * onClickListener des boutons Modifier Statut et Choisir Course
     *
     * @param v Element cliqué
     */

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.statut:
                if(global.getTel() != null){
                    Log.i("Message", "changer texte statut");
                    if (statut.getText().equals(getResources().getString(R.string.statut_occupe))) {
                        Log.i("Message", "libre");
                        statut.setText(getResources().getString(R.string.statut_libre));
                        statut.setTextColor(ContextCompat.getColor(this, R.color.libre));
                        item = getResources().getString(R.string.statut_libre);

                    } else if (statut.getText().equals(getResources().getString(R.string.statut_libre))) {

                        statut.setText(getResources().getString(R.string.statut_occupe));
                        statut.setTextColor(ContextCompat.getColor(this, R.color.occupe));
                        item = getResources().getString(R.string.statut_occupe);
                        Log.i("Message", item);
                    }
                    if (item != null) {
                        SharedPreferences settings = getSharedPreferences("config", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("statut", item);
                        editor.commit();


                        // Requète de récupération des adresses
                        api.postMajStatut(global.getTel(), item, new Callback<ResponseModel>() {
                            // Méthode executée en cas de succès de la requète
                            @Override
                            public void success(ResponseModel model, Response response) {
                                if (model.getStatus().equals("succes")) {
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
                    }
                }
                break;

            case R.id.imagebouton4:
                startActivity(new Intent(MainActivity.this, info_station.class));
                break;

            case R.id.imagebouton3:
                startActivity(new Intent(MainActivity.this, message_centrale.class));
                break;
            // TODO : Page Historique
            case R.id.imagebouton2:
                item = settings.getString("statut", getResources().getString(R.string.statut_libre));
                switch(item){
                    case "Charge" :
                        startActivity(new Intent(MainActivity.this, course_actuelle.class));
                        break;
                    case "Destination" :
                        startActivity(new Intent(MainActivity.this, course_actuelle_depot.class));
                        break;
                    default :
                        Toast.makeText(this, "Pas de course actuellement", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
        }
    }

    /**
     * Effectue une requète de localisation
     */
    private void getLocation() {
        locationRequest = LocationRequest.create();

        // Prioriété sur HIGH_ACCURACY : Consomation de batterie importante.
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // Définition des demandes de localisation
        // TODO : Changer les valeurs
        locationRequest.setInterval(20000);
        locationRequest.setFastestInterval(15000);
        fusedLocationProviderApi = LocationServices.FusedLocationApi;

        // Connexion à l'API
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    /**
     * Méthode effectuée après avoir demandé la permission d'utiliser le GPS
     *
     * @param requestCode  Code de la requète, par exemple, il sera égal à 1 si c'est la requète de permission pour le GPS
     * @param permissions  Non utilisé ici
     * @param grantResults Contient si la permission accordée ou non
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            // 1 : Code pour la requète de permission GPS
            case 1: {
                // Si la demande est annulée, le tableau de résultat est vide
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Code éxécuté si la permission est accordée
                } else {
                    // Code éxécuté si la permission est refusé
                    // Message d'alerte pour dire à l'utilisateur qu'on en a besoin obligatoirement
                    new AlertDialog.Builder(this).setTitle("Accès gps refusé")
                            .setMessage("Veuillez activer les données de localisation dans les paramètres de l'application")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Quitte l'application si il clique sur ok
                                    System.exit(0);
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                return;
            }
            case 2: {
                // Si la demande est annulée, le tableau de résultat est vide
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Code éxécuté si la permission est accordée
                } else {
                    // Code éxécuté si la permission est refusé
                    // Message d'alerte pour dire à l'utilisateur qu'on en a besoin obligatoirement
                    new AlertDialog.Builder(this).setTitle("Accès phone state refusé")
                            .setMessage("Veuillez l'activer dans les paramètres de l'application")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Quitte l'application si il clique sur ok
                                    System.exit(0);
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    /**
     * Méthode qui s'execute si l'application est connectée au service de localisation
     *
     * @param bundle
     * @throws SecurityException Occure si la permission de localisation est refusée
     */
    @Override
    public void onConnected(Bundle bundle) throws SecurityException {
        // Envoie une demande de localisation
        fusedLocationProviderApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    /**
     * Méthode qui s'execute lorsque la localisation change
     *
     * @param location
     * @throws SecurityException Occure si la permission de localisation est refusée
     */
    @Override
    public void onLocationChanged(Location location) throws SecurityException {
        // TODO : Envoyer localisation à l'API
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                googleApiClient);

        // Requète de récupération des adresses
        if(global.getTel() != null){
            api.postMajPosition(global.getTel(), mLastLocation.getLatitude(), mLastLocation.getLongitude(), new Callback<ResponseModel>() {
                // Méthode executée en cas de succès de la requète
                @Override
                public void success(ResponseModel model, Response response) {
                    if (model.getStatus().equals("succes")) {
                        Log.i("success", "envoi localisation ok!");
                    } else {
                        Log.e("FAILURE", "FAILURE LOCATION SQL");
                    }
                }

                // Méthode executée en cas d'erreur de la requète
                @Override
                public void failure(RetrofitError error) {
                    Log.e("CONNECTION ECHOUEE", "Connection Echouée ! : " + error.getMessage());
                }
            });
        }


        if (mLastLocation != null) {
              }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    /**
     * Méthode qui s'execute si l'application est n'a pas réussi à se connecter au service de localisation
     *
     * @param connectionResult
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
       }

    /**
     * Tâches en arrière plan!
     */
    private class BackgroundTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            while (true) {
                final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                while (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    buildAlertMessageNoGps();
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        Thread.interrupted();
                    }
                }
                try {
                    if(global.getTel() != null){
                        checkPropositions();
                        api.getFeedMessages(new Callback<MessageModel>() {
                            @Override
                            public void success(MessageModel messageModel, Response response) {
                                Log.i("Message : ", "BDD ok");
                                if (messageModel.getStatut().equals("succes")) {
                                    Log.i("Message : ", "succes");
                                    List<JSONMessage> listeMessage = messageModel.getData();
                                    bdd = new LiaisonDataBase(MainActivity.this);
                                    bdd.open();
                                    String date = null;
                                    if (!bdd.getHistorique().isEmpty()) {
                                        Log.i("Message : ", "BDD non vide");
                                        date = bdd.getMessageFromId(bdd.getMaxMessage()).getHeure();
                                        bdd.suppressionHistorique();
                                        for (JSONMessage m : listeMessage) {
                                            int nbegaux = 0;
                                            for (Message mes : bdd.getHistorique()) {
                                                if (m.getSujet().equals(mes.getMessage()) && m.getMoment().equals(mes.getDate() + "T" + mes.getHeure())) {
                                                    nbegaux += 1;
                                                }
                                            }
                                            if (nbegaux == 0) {
                                                bdd.creationMessage(m.getSujet(), m.getMoment());
                                                Log.i("Message :", "MEssage créer !");
                                            }
                                        }
                                    } else {
                                        Log.i("Message : ", "BDD vide");
                                        for (JSONMessage m : listeMessage) {
                                            bdd.creationMessage(m.getSujet(), m.getMoment());
                                            date = bdd.getMessageFromId(bdd.getMaxMessage()).getHeure();
                                            Log.i("Message :", "MEssage créer " + m.getMoment());
                                        }
                                    }
                                    if (date != null) {
                                        if (!date.equals(bdd.getMessageFromId(bdd.getMaxMessage()).getHeure())) {
                                            Log.i("Message :", "run nouvelle activité");
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Vibrator v = (Vibrator) MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
                                                    v.vibrate(500);
                                                    Intent myIntent = new Intent(MainActivity.this, message_centrale.class);
                                                    MainActivity.this.startActivity(myIntent);
                                                }
                                            });
                                        }
                                    }
                                    bdd.close();
                                } else {
                                    if (messageModel.getStatut().equals("Pas de messages")) {
                                        Log.i("Message", "Pas de messages");
                                    } else {
                                        Log.e("FAILURE", "FAILURE MESSAGE");
                                    }
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Log.e("CONNECTION ECHOUEE", "Connection Echouée ! : " + error.getMessage());
                            }
                        });
                        Thread.sleep(30000);
                    }
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }
        }

        @Override
        protected void onPostExecute(String result) {

            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }

        protected void checkPropositions() {
            Log.e("GET PROPO", "HAHAHAHA");
            api.getFeedProposition(global.getTel(), new Callback<PropositionModel>() {
                @Override
                public void success(PropositionModel model, Response response) {
                    Log.e("GET PROPO", "REQUETE REUSSIE");
                    if (model.getStatut().equals("succes")) {
                        final Proposition prop = model.getData().get(0);
                        Log.e("GET PROPO", "STATUS = SUCCES");
                        Log.i("success", "proposition course ok! " + model.getData().get(0).getNumero());
                        runOnUiThread(new PropositionRunnable(model.getData().get(0)) {
                            @Override
                            public void run() {
                                Log.i("Runnable", "Haha : " + getProposition().getNumero());
                                global.setCourseCourante(prop);
                                startActivity(new Intent(MainActivity.this, accepter_course.class));


                            }
                        });
                    } else {
                        Log.e("FAILURE", "FAILURE PROPOSITION COURSE");
                    }
                }


                // Méthode executée en cas d'erreur de la requète
                @Override
                public void failure(RetrofitError error) {
                    Log.e("CONNECTION ECHOUEE", "Connection Echouée ! : " + error.getMessage());
                }
            });
        }

        protected void buildAlertMessageNoGps() {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    alertDialog = new AlertDialog.Builder(MainActivity.this).setTitle("GPS Désactivé")
                            .setMessage("Votre GPS est désactivé")
                            .setNeutralButton("Aller dans les paramètres", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setCancelable(false)
                            .show();
                    cdt = new CountDownTimer(5000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            // NE RIEN METTRE ICI!!!
                        }

                        @Override
                        public void onFinish() {
                            alertDialog.cancel();
                        }
                    }.start();
                }

            });

        }
    }
}



