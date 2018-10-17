package fr.univ_tlse3.taxisid.Helpers;

import android.content.DialogInterface;

import fr.univ_tlse3.taxisid.model.Proposition;
import fr.univ_tlse3.taxisid.model.PropositionModel;

/**
 * Created by JB on 11/01/2016.
 */
public class PropositionListener implements DialogInterface.OnClickListener {

    private Proposition proposition;

    public PropositionListener(Proposition proposition){
        this.proposition = proposition;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }

    public Proposition getProposition(){
        return proposition;
    }
}
