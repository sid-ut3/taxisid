package fr.univ_tlse3.taxisid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class liste_code extends Activity implements View.OnClickListener {

    Button btn;
    Button btn_clients;
    Button btn_radar;

    private Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_liste_code);
        //initialisation du texte
        TextView tv2 = (TextView) findViewById(R.id.titre);
        tv2.setText(getResources().getString(R.string.title_activity_liste_code));
        //bouton du menu

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


        btn = (Button) findViewById(R.id.btn_autres);
        btn.setOnClickListener(this);

        btn_clients = (Button) findViewById(R.id.btn_clients);
        btn_clients.setOnClickListener(this);

        btn_radar = (Button) findViewById(R.id.btn_radar);
        btn_radar.setOnClickListener(this);

    }

    /**
     * onClickListener des boutons Modifier Statut et Choisir Course
     *
     * @param v
     */
    public void onClick(View v) {
        String contenu;
        Intent i;
        switch (v.getId()) {
            // Actions effectuées en cas de clic sur modifier_statut
            case R.id.btn_radar:
                contenu = btn.getText().toString();
                i = new Intent(liste_code.this, liste_code_precis.class);
                i.putExtra("UTILISATION", "R");
                startActivity(i);
                break;
            case R.id.btn_clients:
                contenu = btn.getText().toString();
                i = new Intent(liste_code.this, liste_code_precis.class);
                i.putExtra("UTILISATION", "C");
                startActivity(i);
                break;
            case R.id.btn_autres:
                contenu = btn.getText().toString();
                i = new Intent(liste_code.this, liste_code_precis.class);
                i.putExtra("UTILISATION", "A");
                startActivity(i);
                break;
            case R.id.imagebouton4:
                startActivity(new Intent(liste_code.this, info_station.class));
                break;
            case R.id.imagebouton3:
                startActivity(new Intent(liste_code.this, message_centrale.class));
                break;
            case R.id.imagebouton1:
                startActivity(new Intent(liste_code.this, MainActivity.class));
                break;
            case R.id.imagebouton2:
                SharedPreferences settings = getSharedPreferences("config", 0);
                String item = settings.getString("statut", getResources().getString(R.string.statut_libre));
                switch(item){
                    case "Charge" :
                        startActivity(new Intent(liste_code.this, course_actuelle.class));
                        break;
                    case "Destination" :
                        startActivity(new Intent(liste_code.this, course_actuelle_depot.class));
                        break;
                    default :
                        Toast.makeText(this, "Pas de course actuellement", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
        }
    }


}
