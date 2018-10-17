package fr.univ_tlse3.taxisid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by JB on 13/01/2016.
 */
public class Conducteur {


    @SerializedName("attente")
    @Expose
    private String attente;
    @SerializedName("conducteur")
    @Expose
    private String conducteur;

    /**
     *
     * @return
     * The attente
     */
    public String getAttente() {
        return attente;
    }

    /**
     *
     * @param attente
     * The attente
     */
    public void setAttente(String attente) {
        this.attente = attente;
    }

    /**
     *
     * @return
     * The conducteur
     */
    public String getConducteur() {
        return conducteur;
    }

    /**
     *
     * @param conducteur
     * The conducteur
     */
    public void setConducteur(String conducteur) {
        this.conducteur = conducteur;
    }

}
