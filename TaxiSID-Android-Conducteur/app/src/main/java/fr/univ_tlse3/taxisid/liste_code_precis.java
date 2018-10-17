package fr.univ_tlse3.taxisid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fr.univ_tlse3.taxisid.API.taxiapi;
import fr.univ_tlse3.taxisid.BDD.AdapterMessage;
import fr.univ_tlse3.taxisid.BDD.Code;
import fr.univ_tlse3.taxisid.BDD.LiaisonDataBase;
import fr.univ_tlse3.taxisid.model.ResponseModel;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class liste_code_precis extends Activity implements View.OnClickListener{

    LiaisonDataBase bdd;
    EditText yourEditText;
    Button btn;

    String newString;
    String messageCode = "";

    private Global global;
    private String item;

    RestAdapter restAdapter;
    taxiapi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_liste_code_precis);
        //initialisation texte et écriture
        TextView tv2 = (TextView) findViewById(R.id.titre);
        tv2.setText(getResources().getString(R.string.title_activity_liste_code));

        this.restAdapter = new RestAdapter.Builder().setEndpoint(Global.API).build();
        this.api = restAdapter.create(taxiapi.class);

        global = (Global) getApplicationContext();
        ImageButton buttonmenu1 = (ImageButton) findViewById(R.id.imagebouton1);
        buttonmenu1.getBackground().setColorFilter(0x000, PorterDuff.Mode.SCREEN);
        ImageButton buttonmenu2 = (ImageButton) findViewById(R.id.imagebouton2);
        buttonmenu2.getBackground().setColorFilter(0x000, PorterDuff.Mode.SCREEN);
        ImageButton buttonmenu3 = (ImageButton) findViewById(R.id.imagebouton3);
        buttonmenu3.getBackground().setColorFilter(0xFFFEC418, PorterDuff.Mode.SCREEN);
        ImageButton buttonmenu4 = (ImageButton) findViewById(R.id.imagebouton4);
        buttonmenu4.getBackground().setColorFilter(0x000, PorterDuff.Mode.SCREEN);
        buttonmenu1.setOnClickListener(this);
        buttonmenu2.setOnClickListener(this);
        buttonmenu3.setOnClickListener(this);
        buttonmenu4.setOnClickListener(this);

        bdd = new LiaisonDataBase(this);
        bdd.open();

        if(bdd.getCode("A").isEmpty()) {
            bdd.creationCode("OUI", "A");
            bdd.creationCode("NON", "A");
            bdd.creationCode("Ok merci", "A");
            bdd.creationCode("Relance course", "A");
            bdd.creationCode("Pas d'adresse", "A");
            bdd.creationCode("Pas de numéro de téléphone", "A");
            bdd.creationCode("Adresse érronnée", "A");
            bdd.creationCode("Le client doit-il payer", "A");
            bdd.creationCode("Trop de passagers", "A");
            bdd.creationCode("Course trop longue", "A");
        }
        if(bdd.getCode("C").isEmpty()) {
            bdd.creationCode("à la crabe", "C");
            bdd.creationCode("Wilson", "C");
            bdd.creationCode("Capitole", "C");
            bdd.creationCode("Esquirol", "C");
            bdd.creationCode("St Cyprien", "C");
            bdd.creationCode("Carmes", "C");
            bdd.creationCode("Gare", "C");
            bdd.creationCode("Aéro", "C");
            bdd.creationCode("Compans", "C");
        }
        if(bdd.getCode("R").isEmpty()) {
            bdd.creationCode("Descente Aéro", "R");
            bdd.creationCode("Arc-en-Ciel", "R");
        }

        btn = (Button) findViewById(R.id.btnCodeNom);
        ListView liste1 = (ListView) findViewById(R.id.testcode);
        yourEditText = (EditText) findViewById(R.id.champCode);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("UTILISATION");
                Log.i("Utilisation ", newString);
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("UTILISATION");
        }

/**
 * création des radars, clients et autres
 */
        if(newString.equals("R")){
            messageCode = getResources().getString(R.string.radar)+" ";
            ArrayList<Code> codes = new ArrayList<>(bdd.getCode("R"));
            AdapterMessage listAdapter1 = new AdapterMessage(getApplicationContext(), (ArrayList<Code>) codes);
            liste1.setAdapter(listAdapter1);

            yourEditText.setEnabled(false);
            yourEditText.setVisibility(View.GONE);
            btn.setVisibility(View.GONE);

        }
        else if(newString.equals("C")){
            messageCode = getResources().getString(R.string.clients)+" ";
            ArrayList<Code> codes = new ArrayList<>(bdd.getCode("C"));
            AdapterMessage listAdapter1 = new AdapterMessage(getApplicationContext(), (ArrayList<Code>) codes);
            liste1.setAdapter(listAdapter1);

            btn.setVisibility(View.GONE);
            yourEditText.setVisibility(View.GONE);
            yourEditText.setEnabled(false);
        }
        else{
            ArrayList<Code> codes = new ArrayList<>(bdd.getCode("A"));
            AdapterMessage listAdapter1 = new AdapterMessage(getApplicationContext(), (ArrayList<Code>) codes);
            liste1.setAdapter(listAdapter1);

            yourEditText.setVisibility(View.VISIBLE);
            yourEditText.setEnabled(true);
            btn.setVisibility(View.VISIBLE);

            yourEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
            yourEditText.setRawInputType(Configuration.KEYBOARD_QWERTY);
            yourEditText.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    int i;
                    if (!yourEditText.getText().toString().equals("")) {
                        i = Integer.parseInt(yourEditText.getText().toString());
                        if (bdd.getCodeFromId(i).toString() != null) {
                            btn.setText(bdd.getCodeFromId(i).toString());
                            // you can call or do what you want with your EditText here
                        }
                    }
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            });
            liste1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                /**
                 * Lorsqu'on clique sur un item, on colorise le fond de l'item selectionner sinon on décolorise
                 * @param parent
                 * @param view
                 * @param position
                 * @param id
                 */
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    //item = "11#" + ((TextView) view).getText().toString();
                    item =((TextView) view).getText().toString();
                    btn.setText(item);
                }
            });

            btn.setOnClickListener(this);
        }

        liste1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            /**
             * Lorsqu'on clique sur un item, on colorise le fond de l'item selectionner sinon on décolorise
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
                                           long id) {
                String av = "";
                /*if (newString.equals("R") || newString.equals("C")) {
                    av = "99#";
                } else if (newString.equals("A")) {
                    av = "11#";
                }*/
                item = av + messageCode + ((TextView) view).getText().toString();
                Log.i("Message à envoyer :", item);
                api.getEnvoyerMessage(global.getTel(), item, new Callback<ResponseModel>() {
                    @Override
                    public void success(ResponseModel responseModel, Response response) {
                        if (responseModel.getStatus().equals("succes")) {
                            Toast.makeText(liste_code_precis.this, "Message Envoyé", Toast.LENGTH_SHORT).show();
                            Log.i("Envoie", "message envoyer à la centrale");
                            startActivity(new Intent(liste_code_precis.this, MainActivity.class));
                        } else {
                            Log.i("Envoie", "message pas envoyer à la centrale");
                        }
                    }
                    @Override
                    public void failure(RetrofitError error) {
                        Log.i("Message à envoyer :", "FAILURE "+error.getMessage());
                    }
                });
                return false;
            }
        });

        // fermeture bdd
        bdd.close();
    }

    /**
     * onClickListener des boutons Modifier Statut et Choisir Course
     * @param v
     */
    public void onClick(View v) {
        switch (v.getId()) {
            // Actions effectuées en cas de clic sur modifier_statut
            case R.id.btnCodeNom:
                api.getEnvoyerMessage(global.getTel(), item, new Callback<ResponseModel>() {
                    @Override
                    public void success(ResponseModel responseModel, Response response) {
                        if (responseModel.getStatus().equals("succes")) {
                            Toast.makeText(liste_code_precis.this, "Message Envoyé", Toast.LENGTH_SHORT).show();
                            Log.i("Envoie", "message envoyer à la centrale");
                            startActivity(new Intent(liste_code_precis.this, MainActivity.class));
                        } else {
                            Log.i("Envoie", "message pas envoyer à la centrale");
                        }
                    }
                    @Override
                    public void failure(RetrofitError error) {
                        Log.i("Message à envoyer :", "FAILURE "+error.getMessage());
                    }
                });
                break;
            case R.id.imagebouton4:
                startActivity(new Intent(liste_code_precis.this, info_station.class));
                break;
            case R.id.imagebouton3:
                startActivity(new Intent(liste_code_precis.this, message_centrale.class));
                break;
            case R.id.imagebouton1:
                startActivity(new Intent(liste_code_precis.this, MainActivity.class));
                break;
            case R.id.imagebouton2:
                SharedPreferences settings = getSharedPreferences("config", 0);
                String item = settings.getString("statut", getResources().getString(R.string.statut_libre));
                switch(item){
                    case "Charge" :
                        startActivity(new Intent(liste_code_precis.this, course_actuelle.class));
                        break;
                    case "Destination" :
                        startActivity(new Intent(liste_code_precis.this, course_actuelle_depot.class));
                        break;
                    default :
                        Toast.makeText(this, "Pas de course actuellement", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
        }
    }
}
