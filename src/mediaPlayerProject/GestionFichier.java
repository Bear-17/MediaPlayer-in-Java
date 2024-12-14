/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mediaPlayerProject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

/**
 *
 * @author Test
 */
public class GestionFichier {
    static File dossier = new File("Data");
    static File appData = new File(dossier + "/App Data.txt");
    
    static Boolean ecrireDonneesApp(MediaList md)
    {
        Boolean wroteInFile = false;
        if(!dossier.exists())
            dossier.mkdir();
        
        try
        {
            FileWriter fw = new FileWriter(appData, true);
            
            fw.write(md.getID() + '\t');
            fw.write(md.getNom() + '\t');
            fw.write(md.getEmplacement() + '\n');
            
            wroteInFile = true;
            fw.close();
        }
        catch(IOException iOException)
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Erreur");
            a.setContentText("Operation echouee: Erreur lors de la creation du fichier");
            a.showAndWait();
        }
        return wroteInFile;
    }
    
    static ObservableList<MediaList> lireDonnesApp()
    {
        ObservableList<MediaList> contenuFichier = FXCollections.observableArrayList();
        List<String> lineList;        
        
        try
        {
            lineList = Files.readAllLines(appData.toPath());
            
            for (String line: lineList)
            {
               MediaList md = new MediaList();    
               
               String[] attributes = line.split("\t");                
               md.setID(attributes[0]);
               md.setNom(attributes[1]);
               md.setEmplacement(attributes[2]);
               
               contenuFichier.add(md);
            }
        }
        catch(IOException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }
        
        return contenuFichier;
    }

    static Boolean effacerMedia(ObservableList<MediaList> mdList)
    {
        Boolean replacementSuccess = false;
        if(!dossier.exists())
            dossier.mkdir();
        
        File newAppData = new File(dossier + "/New App Data.txt");
        
        try
        {
            FileWriter fw = new FileWriter(newAppData, false);
            
            for (MediaList md: mdList)
            {
                fw.write(md.getID() + '\t');
                fw.write(md.getNom() + '\t');
                fw.write(md.getEmplacement() + '\n');
            }
            fw.close();
            appData.delete();
            if(appData.exists())
                System.err.println("Le fichier n'a pas pu etre supprime pour la modification "); //debug
            
            if(newAppData.renameTo((appData)))
                replacementSuccess = true;
        }
        catch (IOException iOException)
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Erreur");
            a.setContentText("Operation echouee: Erreur lors de la creation du fichier");
            a.showAndWait();
        }
        
        return replacementSuccess;     
    }
        
}
