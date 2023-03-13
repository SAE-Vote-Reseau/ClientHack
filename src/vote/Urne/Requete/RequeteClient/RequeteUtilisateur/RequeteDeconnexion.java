package vote.Urne.Requete.RequeteClient.RequeteUtilisateur;


import vote.Urne.Requete.RequeteClient.Requete;

public class RequeteDeconnexion extends Requete {
    private static final long serialVersionUID = 1248828963788962307L;
    private String ssid;

    public RequeteDeconnexion(String ssid){
        super("deconect");
        this.ssid = ssid;
    }


}
