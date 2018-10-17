package fr.univ_tlse3.taxisid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JB on 07/01/2016.
 */
public class Station {

    @SerializedName("conducteurs")
    @Expose
    private List<Conducteur> conducteurs = new ArrayList<Conducteur>();
    @SerializedName("adresse")
    @Expose
    private String nom;

    /**
     *
     * @return
     * The conducteurs
     */
    public List<Conducteur> getConducteurs() {
        return conducteurs;
    }

    /**
     *
     * @param conducteurs
     * The conducteurs
     */
    public void setConducteurs(List<Conducteur> conducteurs) {
        this.conducteurs = conducteurs;
    }

    /**
     *
     * @return
     * The nom
     */
    public String getNom() {
        return nom;
    }

    /**
     *
     * @param nom
     * The nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

}

