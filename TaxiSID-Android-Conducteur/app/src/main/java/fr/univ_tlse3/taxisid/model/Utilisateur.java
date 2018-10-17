package fr.univ_tlse3.taxisid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Bastien on 06/01/2016.
 */

public class Utilisateur {


    @SerializedName("_mdp")
    @Expose
    private String Mdp;
    @SerializedName("adresse")
    @Expose
    private Integer adresse;
    @SerializedName("categorie")
    @Expose
    private String categorie;
    @SerializedName("civilite")
    @Expose
    private String civilite;
    @SerializedName("confirmation")
    @Expose
    private String confirmation;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("fax")
    @Expose
    private String fax;
    @SerializedName("inscription")
    @Expose
    private String inscription;
    @SerializedName("nom")
    @Expose
    private String nom;
    @SerializedName("notification_email")
    @Expose
    private String notificationEmail;
    @SerializedName("notification_sms")
    @Expose
    private String notificationSms;
    @SerializedName("prenom")
    @Expose
    private String prenom;
    @SerializedName("telephone")
    @Expose
    private String telephone;


    /**
     *
     * @return
     * The Mdp
     */
    public String getMdp() {
        return Mdp;
    }

    /**
     *
     * @param Mdp
     * The _mdp
     */
    public void setMdp(String Mdp) {
        this.Mdp = Mdp;
    }

    /**
     *
     * @return
     * The adresse
     */
    public Integer getAdresse() {
        return adresse;
    }

    /**
     *
     * @param adresse
     * The adresse
     */
    public void setAdresse(Integer adresse) {
        this.adresse = adresse;
    }

    /**
     *
     * @return
     * The categorie
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     *
     * @param categorie
     * The categorie
     */
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    /**
     *
     * @return
     * The civilite
     */
    public String getCivilite() {
        return civilite;
    }

    /**
     *
     * @param civilite
     * The civilite
     */
    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

    /**
     *
     * @return
     * The confirmation
     */
    public String getConfirmation() {
        return confirmation;
    }

    /**
     *
     * @param confirmation
     * The confirmation
     */
    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The fax
     */
    public String getFax() {
        return fax;
    }

    /**
     *
     * @param fax
     * The fax
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     *
     * @return
     * The inscription
     */
    public String getInscription() {
        return inscription;
    }

    /**
     *
     * @param inscription
     * The inscription
     */
    public void setInscription(String inscription) {
        this.inscription = inscription;
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

    /**
     *
     * @return
     * The notificationEmail
     */
    public String getNotificationEmail() {
        return notificationEmail;
    }

    /**
     *
     * @param notificationEmail
     * The notification_email
     */
    public void setNotificationEmail(String notificationEmail) {
        this.notificationEmail = notificationEmail;
    }

    /**
     *
     * @return
     * The notificationSms
     */
    public String getNotificationSms() {
        return notificationSms;
    }

    /**
     *
     * @param notificationSms
     * The notification_sms
     */
    public void setNotificationSms(String notificationSms) {
        this.notificationSms = notificationSms;
    }

    /**
     *
     * @return
     * The prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     *
     * @param prenom
     * The prenom
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     *
     * @return
     * The telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     *
     * @param telephone
     * The telephone
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}







