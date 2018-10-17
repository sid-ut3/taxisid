package fr.univ_tlse3.taxisid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class horaire_reservation extends Activity implements View.OnClickListener{

    Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horaire_reservation);
        //initialisation du textview et ecriture
        TextView tv = (TextView) findViewById(R.id.titre);
        tv.setText(getResources().getString(R.string.title_activity_horaire_reservation));
        //initialisation boutons
        global = (Global) getApplicationContext();
        ImageButton buttonmenu1 = (ImageButton) findViewById(R.id.imagebouton1);
        buttonmenu1.getBackground().setColorFilter(0x000, PorterDuff.Mode.SCREEN);
        ImageButton buttonmenu2 = (ImageButton) findViewById(R.id.imagebouton2);
        buttonmenu2.getBackground().setColorFilter(0x000, PorterDuff.Mode.SCREEN);
        ImageButton buttonmenu3 = (ImageButton) findViewById(R.id.imagebouton3);
        buttonmenu3.getBackground().setColorFilter(0x000, PorterDuff.Mode.SCREEN);
        ImageButton buttonmenu4 = (ImageButton) findViewById(R.id.imagebouton4);
        buttonmenu4.getBackground().setColorFilter(0xFFFEC418, PorterDuff.Mode.SCREEN);
        buttonmenu1.setOnClickListener(this);
        buttonmenu2.setOnClickListener(this);
        buttonmenu3.setOnClickListener(this);
        buttonmenu4.setOnClickListener(this);

    }// methode pour l'action des boutons
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imagebouton4:
                startActivity(new Intent(horaire_reservation.this, info_station.class));
                break;
            case R.id.imagebouton3:
                startActivity(new Intent(horaire_reservation.this, message_centrale.class));
                break;
            case R.id.imagebouton1:
                startActivity(new Intent(horaire_reservation.this, MainActivity.class));
                break;
            case R.id.imagebouton2:
                SharedPreferences settings = getSharedPreferences("config", 0);
                String item = settings.getString("statut", getResources().getString(R.string.statut_libre));
                switch(item){
                    case "Charge" :
                        startActivity(new Intent(horaire_reservation.this, course_actuelle.class));
                        break;
                    case "Destination" :
                        startActivity(new Intent(horaire_reservation.this, course_actuelle_depot.class));
                        break;
                    default :
                        Toast.makeText(this, "Pas de course actuellement", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
        }
    }
}
