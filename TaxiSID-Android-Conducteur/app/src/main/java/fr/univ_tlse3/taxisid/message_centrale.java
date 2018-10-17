package fr.univ_tlse3.taxisid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import fr.univ_tlse3.taxisid.BDD.LiaisonDataBase;
import fr.univ_tlse3.taxisid.BDD.Message;

public class message_centrale extends Activity implements View.OnClickListener {
    LiaisonDataBase bdd;
    TextView tvDate;
    TextView tvMessage;
    Message m;
    Button btn_avant;
    Button btn_suivant;
    Integer nbRestant;
    private Global global;


    private float x1, x2;
    static final int MIN_DISTANCE = 150;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_centrale);
        //initialisation texte
        TextView tv = (TextView) findViewById(R.id.titre);
        tv.setText(getResources().getString(R.string.title_activity_message_centrale));


        global = (Global) getApplicationContext();

        bdd = new LiaisonDataBase(this);
        bdd.open();
        //initialisation bouton

        btn_avant = (Button) findViewById(R.id.btn_avant);
        btn_avant.setOnClickListener(this);
        btn_suivant = (Button) findViewById(R.id.btn_apres);
        btn_suivant.setOnClickListener(this);

        Button buttonAvant = (Button) findViewById(R.id.btn_avant);
        Button buttonmess = (Button) findViewById(R.id.mess);
        buttonAvant.setOnClickListener(this);
        Button buttonApres = (Button) findViewById(R.id.btn_apres);
        buttonApres.setOnClickListener(this);
        buttonmess.setOnClickListener(this);
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
        buttonmenu4.setOnClickListener(this);
        tvDate = (TextView) findViewById(R.id.date_mess);

        nbRestant = bdd.getMaxMessage();
        Log.i("Nb :", "" + bdd.getMaxMessage());
        tvMessage = (TextView) findViewById(R.id.message_central);

        if (nbRestant != 0) {

            m = bdd.getMessageFromId(bdd.getMaxMessage());
            tvDate.setText(m.getDate() + " à " + m.getHeure());

            tvMessage.setText("" + m.getMessage());

            if (nbRestant == 1 || nbRestant == 0) {
                btn_suivant.setEnabled(false);
                btn_avant.setEnabled(false);
            } else {
                btn_suivant.setEnabled(false);
                btn_avant.setEnabled(true);
            }
        } else {
            tvMessage.setText(getResources().getString(R.string.aucun_message));
            btn_suivant.setEnabled(false);
            btn_avant.setEnabled(false);
        }
        // fermeture bdd
        bdd.close();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;

                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    // Left to Right swipe action
                    if (x2 > x1) {
                        if (nbRestant > 1) {
                            nbRestant--;
                            m = bdd.getMessageFromId(nbRestant);
                            tvDate.setText(m.getDate() + " à " + m.getHeure());
                            tvMessage.setText("" + m.getMessage());
                            Log.i("Historique", "" + bdd.getHistorique().toString());
                            if (nbRestant == 1) {
                                btn_avant.setEnabled(false);
                                btn_suivant.setEnabled(true);
                            } else {
                                btn_avant.setEnabled(true);
                                btn_suivant.setEnabled(true);
                            }
                        }
                    }
                    // Right to left swipe action
                    else {
                        if (nbRestant < bdd.getMaxMessage()) {
                            nbRestant++;
                            m = bdd.getMessageFromId(nbRestant);
                            tvDate.setText(m.getDate() + " à " + m.getHeure());
                            tvMessage.setText("" + m.getMessage());
                            Log.i("Historique", "" + bdd.getHistorique().toString());
                            if (nbRestant == bdd.getMaxMessage()) {
                                btn_avant.setEnabled(true);
                                btn_suivant.setEnabled(false);
                            } else {
                                btn_avant.setEnabled(true);
                                btn_suivant.setEnabled(true);
                            }
                        }
                    }
                } else {
                    // consider as something else - a screen tap for example
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * onClickListener des boutons Modifier Statut et Choisir Course
     *
     * @param v
     */
    public void onClick(View v) {
        switch (v.getId()) {
            // Actions effectuées en cas de clic sur modifier_statut
            case R.id.btn_avant:
                if (nbRestant > 1) {
                    nbRestant--;
                    m = bdd.getMessageFromId(nbRestant);
                    tvDate.setText(m.getDate() + " à " + m.getHeure());
                    tvMessage.setText("" + m.getMessage());
                    Log.i("Historique", "" + bdd.getHistorique().toString());
                    if (nbRestant == 1) {
                        btn_avant.setEnabled(false);
                        btn_suivant.setEnabled(true);
                    } else {
                        btn_avant.setEnabled(true);
                        btn_suivant.setEnabled(true);
                    }
                }
                break;
            // Actions effectuées en cas de clic sur choisir_course
            case R.id.btn_apres:
                if (nbRestant < bdd.getMaxMessage()) {
                    nbRestant++;
                    m = bdd.getMessageFromId(nbRestant);
                    tvDate.setText(m.getDate() + " à " + m.getHeure());
                    tvMessage.setText("" + m.getMessage());
                    Log.i("Historique", "" + bdd.getHistorique().toString());
                    if (nbRestant == bdd.getMaxMessage()) {
                        btn_avant.setEnabled(true);
                        btn_suivant.setEnabled(false);
                    } else {
                        btn_avant.setEnabled(true);
                        btn_suivant.setEnabled(true);
                    }
                }
                break;
            case R.id.mess:
                startActivity(new Intent(message_centrale.this, liste_code.class));
                break;
            case R.id.imagebouton4:
                startActivity(new Intent(message_centrale.this, info_station.class));
                break;
            case R.id.imagebouton1:
                startActivity(new Intent(message_centrale.this, MainActivity.class));
                break;
            case R.id.imagebouton2:
                SharedPreferences settings = getSharedPreferences("config", 0);
                String item = settings.getString("statut", getResources().getString(R.string.statut_libre));
                switch (item) {
                    case "Charge":
                        startActivity(new Intent(message_centrale.this, course_actuelle.class));
                        break;
                    case "Destination":
                        startActivity(new Intent(message_centrale.this, course_actuelle_depot.class));
                        break;
                    default:
                        Toast.makeText(this, "Pas de course actuellement", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
        }
    }

}
