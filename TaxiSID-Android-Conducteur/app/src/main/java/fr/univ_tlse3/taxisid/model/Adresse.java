package fr.univ_tlse3.taxisid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Bastien on 06/01/2016.
 */


public class Adresse {

    @SerializedName("cp")
    @Expose
    private String cp;
    @SerializedName("identifiant")
    @Expose
    private Integer identifiant;
    @SerializedName("nom_rue")
    @Expose
    private String nomRue;
    @SerializedName("numero")
    @Expose
    private String numero;
    @SerializedName("position")
    @Expose
    private String position;
    @SerializedName("secteur")
    @Expose
    private String secteur;
    @SerializedName("ville")
    @Expose
    private String ville;

    /**
     *
     * @return
     * Code Postal
     */
    public String getCp() {
        return cp;
    }

    /**
     *
     * @param cp
     * Code Postal
     */
    public void setCp(String cp) {
        this.cp = cp;
    }

    /**
     *
     * @return
     * Identifiant
     */
    public Integer getIdentifiant() {
        return identifiant;
    }

    /**
     *
     * @param identifiant
     * Identifiant
     */
    public void setIdentifiant(Integer identifiant) {
        this.identifiant = identifiant;
    }

    /**
     *
     * @return
     * nom rue
     */
    public String getNomRue() {
        return nomRue;
    }

    /**
     *
     * @param nomRue
     * nom rue
     */
    public void setNomRue(String nomRue) {
        this.nomRue = nomRue;
    }

    /**
     *
     * @return
     * numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     *
     * @param numero
     * numero
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     *
     * @return
     * position
     */
    public String getPosition() {
        return position;
    }

    /**
     *
     * @param position
     * position
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     *
     * @return
     * ville
     */
    public String getVille() {
        return ville;
    }

    /**
     *
     * @param ville
     * ville
     */
    public void setVille(String ville) {
        this.ville = ville;
    }

}
