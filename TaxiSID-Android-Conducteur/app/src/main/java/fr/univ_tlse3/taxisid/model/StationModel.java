package fr.univ_tlse3.taxisid.model;

/**
 * Created by Bastien on 06/01/2016.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class StationModel {

    @SerializedName("data")
    @Expose
    private List<Station> data = new ArrayList<Station>();
    @SerializedName("statut")
    @Expose
    private String statut;

    /**
     *
     * @return
     * The data
     */
    public List<Station> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<Station> data) {
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
