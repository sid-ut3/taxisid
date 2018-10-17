package fr.univ_tlse3.taxisid.model;

/**
 * Created by JB on 12/01/2016.
 */
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ReservStationModel {

    @SerializedName("data")
    @Expose
    private List<ReservStation> data = new ArrayList<ReservStation>();
    @SerializedName("statut")
    @Expose
    private String statut;

    /**
     *
     * @return
     * The data
     */
    public List<ReservStation> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<ReservStation> data) {
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