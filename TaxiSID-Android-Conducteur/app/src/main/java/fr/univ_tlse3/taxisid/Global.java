package fr.univ_tlse3.taxisid;

import android.app.Activity;
import android.app.Application;

import fr.univ_tlse3.taxisid.model.Proposition;
import fr.univ_tlse3.taxisid.model.PropositionModel;
import fr.univ_tlse3.taxisid.model.Station;

/**
 * Created by Insa on 07/01/2016.
 * classe pour faire des variables globales
 */
public class Global extends Application {


    private String imei;
    private Proposition courseCourante;
    private Station currentStation;
    private boolean clicAccepter;

    // TODO : Mettre lien API final
    protected static final String API = "http://e6010bdd.ngrok.io/api/";

    private String tel;

    protected Station getCurrentStation(){ return this.currentStation; }

    protected void setCurrentStation(Station currentStation){ this.currentStation = currentStation; }

    protected String getTel(){ return this.tel; }

    protected void setTel(String tel){ this.tel = tel; }

    protected Proposition getCourseCourante() { return courseCourante; }

    protected void setCourseCourante(Proposition courseCourante){ this.courseCourante = courseCourante; }

    public String getImei(){
        return imei;
    }
    public void setImei(String imei){
        this.imei=imei;
    }

    private Activity mCurrentActivity = null;
    public Activity getCurrentActivity(){
        return mCurrentActivity;
    }

    public boolean getClicAccpeter(){ return clicAccepter; }
    public void setClicAccepter(boolean clicAccepter){ this.clicAccepter = clicAccepter; }

}
