package fr.univ_tlse3.taxisid;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class bouton extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bouton);
        //initialisation des boutons du menu
        ImageButton buttonmenu1 = (ImageButton) findViewById(R.id.imagebouton1);
        ImageButton buttonmenu2 = (ImageButton) findViewById(R.id.imagebouton2);
        ImageButton buttonmenu3 = (ImageButton) findViewById(R.id.imagebouton3);
        ImageButton buttonmenu4 = (ImageButton) findViewById(R.id.imagebouton4);
        buttonmenu1.setOnClickListener(this);
        buttonmenu2.setOnClickListener(this);
        buttonmenu3.setOnClickListener(this);
        buttonmenu4.setOnClickListener(this);

    }
    public void onClick(View v){

    }

}
