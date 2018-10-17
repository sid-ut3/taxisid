package fr.univ_tlse3.taxisid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by JB on 08/01/2016.
 */
public class ResponseModel {

    @SerializedName("statut")
    @Expose
    private String status;

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
