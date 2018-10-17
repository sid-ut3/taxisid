package fr.univ_tlse3.taxisid.BDD;

/**
 * Created by Insa on 07/01/2016.
 */
public class Code {
    public static int id;
    private String nom;
    private String utilisation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getUtilisation() {
        return utilisation;
    }

    public void setUtilisation(String utilisation) {
        this.utilisation = utilisation;
    }

    @Override
    public String toString() {
        return "Code [id = " + id + ", Nom = " + nom + ", Utilisation = " +utilisation + " ]";
    }

    public Code(int id, String nom, String utilisation) {
        this.id = id;
        this.nom = nom;
        this.utilisation =utilisation;
    }

    public Code() {
        super();
        // TODO Auto-generated constructor stub
    }
}
