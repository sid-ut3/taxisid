package fr.univ_tlse3.taxisid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by JB on 11/01/2016.
 */
public class Proposition {

    @SerializedName("aeroport")
    @Expose
    private String aeroport;
    @SerializedName("animaux")
    @Expose
    private String animaux;
    @SerializedName("animaux_grands")
    @Expose
    private String animauxGrands;
    @SerializedName("arrivee")
    @Expose
    private Integer arrivee;
    @SerializedName("bagages")
    @Expose
    private String bagages;
    @SerializedName("commentaire")
    @Expose
    private String commentaire;
    @SerializedName("conducteur")
    @Expose
    private String conducteur;
    @SerializedName("debut")
    @Expose
    private String debut;
    @SerializedName("depart")
    @Expose
    private Integer depart;
    @SerializedName("fin")
    @Expose
    private String fin;
    @SerializedName("finie")
    @Expose
    private String finie;
    @SerializedName("gare")
    @Expose
    private String gare;
    @SerializedName("numero")
    @Expose
    private Integer numero;
    @SerializedName("places")
    @Expose
    private Integer places;
    @SerializedName("priorite")
    @Expose
    private String priorite;
    @SerializedName("trouvee")
    @Expose
    private String trouvee;
    @SerializedName("utilisateur")
    @Expose
    private String utilisateur;

    /**
     *
     * @return
     * The aeroport
     */
    public String getAeroport() {
        return aeroport;
    }

    /**
     *
     * @param aeroport
     * The aeroport
     */
    public void setAeroport(String aeroport) {
        this.aeroport = aeroport;
    }

    /**
     *
     * @return
     * The animaux
     */
    public String getAnimaux() {
        return animaux;
    }

    /**
     *
     * @param animaux
     * The animaux
     */
    public void setAnimaux(String animaux) {
        this.animaux = animaux;
    }

    /**
     *
     * @return
     * The animauxGrands
     */
    public String getAnimauxGrands() {
        return animauxGrands;
    }

    /**
     *
     * @param animauxGrands
     * The animaux_grands
     */
    public void setAnimauxGrands(String animauxGrands) {
        this.animauxGrands = animauxGrands;
    }

    /**
     *
     * @return
     * The arrivee
     */
    public Integer getArrivee() {
        return arrivee;
    }

    /**
     *
     * @param arrivee
     * The arrivee
     */
    public void setArrivee(Integer arrivee) {
        this.arrivee = arrivee;
    }

    /**
     *
     * @return
     * The bagages
     */
    public String getBagages() {
        return bagages;
    }

    /**
     *
     * @param bagages
     * The bagages
     */
    public void setBagages(String bagages) {
        this.bagages = bagages;
    }

    /**
     *
     * @return
     * The commentaire
     */
    public String getCommentaire() {
        return commentaire;
    }

    /**
     *
     * @param commentaire
     * The commentaire
     */
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
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

    /**
     *
     * @return
     * The debut
     */
    public String getDebut() {
        return debut;
    }

    /**
     *
     * @param debut
     * The debut
     */
    public void setDebut(String debut) {
        this.debut = debut;
    }

    /**
     *
     * @return
     * The depart
     */
    public Integer getDepart() {
        return depart;
    }

    /**
     *
     * @param depart
     * The depart
     */
    public void setDepart(Integer depart) {
        this.depart = depart;
    }

    /**
     *
     * @return
     * The fin
     */
    public String getFin() {
        return fin;
    }

    /**
     *
     * @param fin
     * The fin
     */
    public void setFin(String fin) {
        this.fin = fin;
    }

    /**
     *
     * @return
     * The finie
     */
    public String getFinie() {
        return finie;
    }

    /**
     *
     * @param finie
     * The finie
     */
    public void setFinie(String finie) {
        this.finie = finie;
    }

    /**
     *
     * @return
     * The gare
     */
    public String getGare() {
        return gare;
    }

    /**
     *
     * @param gare
     * The gare
     */
    public void setGare(String gare) {
        this.gare = gare;
    }

    /**
     *
     * @return
     * The numero
     */
    public Integer getNumero() {
        return numero;
    }

    /**
     *
     * @param numero
     * The numero
     */
    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    /**
     *
     * @return
     * The places
     */
    public Integer getPlaces() {
        return places;
    }

    /**
     *
     * @param places
     * The places
     */
    public void setPlaces(Integer places) {
        this.places = places;
    }

    /**
     *
     * @return
     * The priorite
     */
    public String getPriorite() {
        return priorite;
    }

    /**
     *
     * @param priorite
     * The priorite
     */
    public void setPriorite(String priorite) {
        this.priorite = priorite;
    }

    /**
     *
     * @return
     * The trouvee
     */
    public String getTrouvee() {
        return trouvee;
    }

    /**
     *
     * @param trouvee
     * The trouvee
     */
    public void setTrouvee(String trouvee) {
        this.trouvee = trouvee;
    }

    /**
     *
     * @return
     * The utilisateur
     */
    public String getUtilisateur() {
        return utilisateur;
    }

    /**
     *
     * @param utilisateur
     * The utilisateur
     */
    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }


}
