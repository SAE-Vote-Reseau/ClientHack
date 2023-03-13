package vote.Urne.Requete.RequeteClient.RequeteAdmin;


import vote.Urne.Requete.RequeteClient.Requete;

public class RequeteChangePassword extends Requete {
    private static final long serialVersionUID = -8228560866132091777L;
    private String ssid;
    private String newPassword;
    //cela deconnectera la personne
    public RequeteChangePassword(String newPassword, String ssid) {
        super("op");
        this.ssid = ssid;
        this.newPassword = newPassword;
    }


}
