package fr.univ_tlse3.taxisid.model;

/**
 * Created by Bastien on 06/01/2016.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdresseModel {

    private List<Adresse> data = new ArrayList<Adresse>();

    @SerializedName("statut")
    @Expose
    private String status;

    /**
     *
     * @return data
     * data
     */
     public List<Adresse> getData() {
        return data;
    }

    /**
     *
     * @param data
     * data
     */
    public void setData(List<Adresse> data) {
        this.data = data;
    }

    /**
     *
     * @return
     * status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * status
     */
    public void setStatus(String status) {
        this.status = status;
    }

}