package fr.univ_tlse3.taxisid.model;

/**
 * Created by Insa on 11/01/2016.
 */

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageModel {

    @SerializedName("data")
    @Expose
    private List<JSONMessage> data = new ArrayList<JSONMessage>();
    @SerializedName("statut")
    @Expose
    private String statut;

    /**
     *
     * @return
     * The data
     */
    public List<JSONMessage> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<JSONMessage> data) {
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