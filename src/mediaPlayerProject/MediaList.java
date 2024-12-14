/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mediaPlayerProject;

/**
 *
 * @author Test
 */
public class MediaList {
    private String ID;
    private String Nom;
    private String Emplacement;

    public MediaList() {
    }

    public MediaList(String ID, String Nom, String Emplacement) {
        this.ID = ID;
        this.Nom = Nom;
        this.Emplacement = Emplacement;
    }
    
    

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String Nom) {
        this.Nom = Nom;
    }

    public String getEmplacement() {
        return Emplacement;
    }

    public void setEmplacement(String Emplacement) {
        this.Emplacement = Emplacement;
    }

    
    
    
}
