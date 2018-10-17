package fr.univ_tlse3.taxisid.Helpers;

import fr.univ_tlse3.taxisid.model.Proposition;
import fr.univ_tlse3.taxisid.model.PropositionModel;

/**
 * Created by JB on 11/01/2016.
 */
public class PropositionRunnable implements Runnable {

    private Proposition proposition;

    public PropositionRunnable(Proposition proposition){
        this.proposition = proposition;
    }

    @Override
    public void run() {

    }

    public Proposition getProposition(){
        return proposition;
    }
}
