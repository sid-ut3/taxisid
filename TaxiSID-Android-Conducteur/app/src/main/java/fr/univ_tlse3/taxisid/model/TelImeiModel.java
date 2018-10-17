package fr.univ_tlse3.taxisid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JB on 14/01/2016.
 */
public class TelImeiModel {
    @SerializedName("data")
    @Expose
    private List<Telephone> data = new ArrayList<Telephone>();
    @SerializedName("statut")
    @Expose
    private String statut;

    /**
     *
     * @return
     * The data
     */
    public List<Telephone> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<Telephone> data) {
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
