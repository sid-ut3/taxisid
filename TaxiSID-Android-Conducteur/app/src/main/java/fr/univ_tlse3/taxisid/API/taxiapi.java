package fr.univ_tlse3.taxisid.API;

import fr.univ_tlse3.taxisid.model.AdresseModel;
import fr.univ_tlse3.taxisid.model.MessageModel;
import fr.univ_tlse3.taxisid.model.PropositionModel;
import fr.univ_tlse3.taxisid.model.ReservStationModel;
import fr.univ_tlse3.taxisid.model.ResponseModel;
import fr.univ_tlse3.taxisid.model.StationModel;
import fr.univ_tlse3.taxisid.model.TelImeiModel;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Bastien on 06/01/2016.
 */

public interface taxiapi {

    @GET("/messages/nouveau_message/telephone={telephone}&message={message}")
    public void getEnvoyerMessage(@Path("telephone") String telephone, @Path("message") String message, Callback<ResponseModel> response);

    @GET("/messages/derniers_messages")
    public void getFeedMessages( Callback<MessageModel> response);

    @GET("/conducteurs/imei={imei}")
    public void getTelImei(@Path("imei") String imei, Callback<TelImeiModel> response);

    /**
     * Récupérer les adresses dans l'API
     * @param response Modèle de la réponse
     */
    @GET("/adresses")
    public void getFeedAdresses( Callback<AdresseModel> response);

    /**
     * Récupérer les utilisateurs dans l'API
     * @param response Modèle de la réponse
     */
    @GET("/utilisateurs")
    public void getFeedUtilisateurs( Callback<AdresseModel> response);

    /**
     * Récupérer les stations dans l'API
     * @param response Modèle de la réponse
     */
    @GET("/stations/getall/")
    public void getFeedStations( Callback<StationModel> response);

    @GET("/conducteurs/recuperation_course/numero={numero}")
    public void getRecupererCourse(@Path("numero") int numeroCourse, Callback<PropositionModel> response);

    @GET("/stations/reservations/num_station={num_station}")
    public void getFeedReservStations(@Path("num_station") String num_station, Callback<ReservStationModel> response);

    @GET("/conducteurs/propositions/{numero}")
    public void getFeedProposition(@Path("numero") String numero, Callback<PropositionModel> response);

    @GET("/conducteurs/refuser1/numero={numero}&course={course}")
    public void getRefuser1(@Path("numero") String numero, @Path("course") int course,Callback<ResponseModel> response);

    @GET("/conducteurs/accepter/numero={numero}&course={course}")
    public void getAccepter(@Path("numero") String numero, @Path("course") int course,Callback<ResponseModel> response);

    @GET("/conducteurs/refuser2/numero={numero}&course={course}")
    public void getRefuser2(@Path("numero") String numero, @Path("course") int course,Callback<ResponseModel> response);

    @POST("/conducteurs/maj_statut/telephone={telephone}&statut={statut}")
    public void postMajStatut(@Path("telephone") String numero, @Path("statut") String statut,Callback<ResponseModel> response);

    @POST("/conducteurs/maj_position/telephone={telephone}&lat={lat}&lon={lon}")
    public void postMajPosition(@Path("telephone") String numero, @Path("lat") double latitude, @Path("lon") double longitude, Callback<ResponseModel> response);

    // Exemple avec paramètres
    //@GET("/users/{user}")
    //public void getFeed(@Path("user") String user,Callback<AdresseModel> response);
}
