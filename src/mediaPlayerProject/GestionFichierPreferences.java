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
public class GestionFichierPreferences {
    
    static File dossier = new File("Data");
    static File appPref = new File(dossier + "/App Preferences.txt");
    
    static Boolean ecrirePrefApp(Preferences pref)
    {
        Boolean wroteInFile = false;
        if(!dossier.exists())
            dossier.mkdir();        
        try
        {
            FileWriter fw = new FileWriter(appPref, false);
            
            fw.write(pref.getTableViewPosition() + '\t');
            fw.write(pref.getWindowDefaultSize() + '\t');
            fw.write(Integer.toString(pref.getDefaultVolume()) + '\n');
            
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
    
    static ObservableList<Preferences> lirePrefApp()
    {
        ObservableList<Preferences> contenuFichier = FXCollections.observableArrayList();
        List<String> lineList;        
        
        try
        {
            lineList = Files.readAllLines(appPref.toPath());
            
            for (String line: lineList)
            {
               Preferences pref = new Preferences();    
               
               String[] attributes = line.split("\t");                
               pref.setTableViewPosition(attributes[0]);
               pref.setWindowDefaultSize(attributes[1]);
               pref.setDefaultVolume(Integer.parseInt(attributes[2]));
               
               contenuFichier.add(pref);
            }
        }
        catch(IOException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }
        
        return contenuFichier;
    }
}
