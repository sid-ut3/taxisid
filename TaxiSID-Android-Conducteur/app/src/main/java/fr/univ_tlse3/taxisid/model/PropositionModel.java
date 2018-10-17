package fr.univ_tlse3.taxisid.model;

/**
 * Created by JB on 11/01/2016.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PropositionModel {

    @SerializedName("data")
    @Expose
    private List<Proposition> data = new ArrayList<Proposition>();
    @SerializedName("statut")
    @Expose
    private String statut;

    /**
     *
     * @return
     * The data
     */
    public List<Proposition> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<Proposition> data) {
        this.data = data;
    }

    /**
     *
     * @return
     * The statut
     */
    public String getStatut() {
        return statut;
    }

    /**
     *
     * @param statut
     * The statut
     */
    public void setStatut(String statut) {
        this.statut = statut;
    }

}