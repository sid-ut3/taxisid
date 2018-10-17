package fr.univ_tlse3.taxisid.BDD;

/**
 * Created by Insa on 07/01/2016.
 */
public class Message {

    public static int id;
    private String message;
    private String date;
    private String heure;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }
    @Override
    public String toString() {
        return "Succes [id = " + id + ", Message = " + message + ", Date = " + date + ", Heure = " + heure +" ]";
    }

    public Message(int id, String message, String date) {
        String[] separated = date.split(" ");
        String d = separated[0];
        String h = separated[1];
        this.id = id;
        this.message = message;
        this.date = d;
        this.heure = h;
    }

    public Message() {
        super();
        // TODO Auto-generated constructor stub
    }
}
