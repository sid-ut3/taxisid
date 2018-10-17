package fr.univ_tlse3.taxisid.model;

/**
 * Created by JB on 12/01/2016.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReservStation {

    @SerializedName("debut")
    @Expose
    private String debut;
    @SerializedName("numero")
    @Expose
    private Integer numero;

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

}
