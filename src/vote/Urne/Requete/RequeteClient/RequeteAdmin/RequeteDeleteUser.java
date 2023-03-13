package vote.Urne.Requete.RequeteClient.RequeteAdmin;


import vote.Urne.Requete.RequeteClient.Requete;

public class RequeteDeleteUser extends Requete {
    private static final long serialVersionUID = -5975297827427502321L;
    private String ssid;
    private String email;

    public RequeteDeleteUser(String email, String ssid) {
        super("delete_user");
        this.ssid = ssid;
        this.email = email;
    }

}
