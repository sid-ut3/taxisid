package fr.univ_tlse3.taxisid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Insa on 11/01/2016.
 */

public class JSONMessage {

    @SerializedName("conducteur")
    @Expose
    private String conducteur;
    @SerializedName("date")
    @Expose
    private String moment;
    @SerializedName("sujet")
    @Expose
    private String sujet;

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

    /**
     *
     * @return
     * The moment
     */
    public String getMoment() {
        return moment;
    }

    /**
     *
     * @param moment
     * The moment
     */
    public void setMoment(String moment) {
        this.moment = moment;
    }

    /**
     *
     * @return
     * The sujet
     */
    public String getSujet() {
        return sujet;
    }

    /**
     *
     * @param sujet
     * The sujet
     */
    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

}