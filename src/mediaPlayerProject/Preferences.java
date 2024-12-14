/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mediaPlayerProject;

import java.time.LocalDate;
import java.time.Month;
import java.util.Random;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Test
 */
public class Preferences extends Stage {
    BorderPane bdp = new BorderPane();
    
    RadioButton rdLeft = new RadioButton("Gauche");
    RadioButton rdRight = new RadioButton("Droite");
    
    RadioButton rdMaximizedWindow = new RadioButton("Maximise");
    RadioButton rdMinimizedWindow = new RadioButton("Minimise");        

    TextField integerField = new TextField();  
    Button btSave = new Button("Save");
    
//    public void integerFieldControl()
//    {
//        integerField.textProperty().addListener((observable, ancienneValeur, nouvelleValeur) -> {
//            if (!nouvelleValeur.matches("\\d*"))
//            {
//                integerField.setText(nouvelleValeur.replaceAll("\\D", ""));
//            }
//        });
//    }
    
    public boolean isValidVolume(String volume)
    {        
        int integerVolume = -1;
        
        try
        {
            integerVolume = Integer.parseUnsignedInt(volume);            
        }
        catch (NumberFormatException e)
        {
            System.out.println("Invalid integer string"); //string
        }
        
        return integerVolume != -1;
            
    }
    
    public void getPref()
    {
        ObservableList<Preferences> preferences = GestionFichierPreferences.lirePrefApp();
        Preferences p = null;
        for(Preferences pref: preferences)
        {
            p = pref;
            break;
        }
        if (p != null)
        {
            if(p.getTableViewPosition().equalsIgnoreCase("droite"))
                rdRight.setSelected(true);
            else
                rdLeft.setSelected(true);   
            
            if(p.getWindowDefaultSize().equalsIgnoreCase("maximized"))
                rdMaximizedWindow.setSelected(true);
            else
                rdMinimizedWindow.setSelected(true);            
            
            integerField.setText(Integer.toString(p.getDefaultVolume()));
        } 
        else
            System.out.println("Les parametres n'ont pas pu etre recuperes"); // debug
            
    }
    
    public void fonctionnalites() 
    {
        GridPane gp = new GridPane();
        HBox hbPosTbView;
        HBox hbWindowDefaultSize;
        ToggleGroup tgPosTbView = new ToggleGroup();
        ToggleGroup tgWindowDefaultSize = new ToggleGroup();
        gp.setHgap(8);
        gp.setVgap(15);
//        gp.setPadding(new Insets(0, 50, 0, 0));
        gp.setPadding(new Insets(10));
        
        Label lbPosTbView = new Label("Position de la playlist");
        Label lbWindowDefaultSize = new Label("Taille fenetre au demarrage");
        Label lbDefaultVolume = new Label("Volume par defaut");        
               
        hbPosTbView = new HBox(rdLeft, rdRight);
        hbWindowDefaultSize = new HBox(rdMaximizedWindow, rdMinimizedWindow);
        hbPosTbView.setSpacing(5);
        hbWindowDefaultSize.setSpacing(5);
        integerField.setPromptText("Entre 0 et 100");  
        integerField.prefWidth(100);
        btSave.setPrefWidth(100);                                                    
        rdLeft.setToggleGroup(tgPosTbView);
        rdRight.setToggleGroup(tgPosTbView);
        rdMaximizedWindow.setToggleGroup(tgWindowDefaultSize);
        rdMinimizedWindow.setToggleGroup(tgWindowDefaultSize);
        getPref();
        
        btSave.setOnAction(e->{
            String tableViewPosition = null;
            int defaultVolume = 0;
                        
            if (rdLeft.isSelected())
                tableViewPosition = rdLeft.getText();
            else if (rdRight.isSelected())
                tableViewPosition = rdRight.getText();
            
            if (rdMaximizedWindow.isSelected())
                windowDefaultSize = "Maximized";
            else if (rdMinimizedWindow.isSelected())
                windowDefaultSize = "Minimized";
                
                        
            if (isValidVolume(integerField.getText()))
            {
                defaultVolume = Integer.parseInt(integerField.getText());                
                Preferences pref = new Preferences(tableViewPosition, defaultVolume, windowDefaultSize);                
                if(GestionFichierPreferences.ecrirePrefApp(pref))
                    {
                        Alert succes = new Alert(Alert.AlertType.CONFIRMATION);
                        succes.setTitle("Modification reussie");
                        succes.setContentText("Les parametres ont ete modifies avec succes et predront effet lors du redemarrage de l'application");
                        succes.showAndWait();                    
                        this.close();
                    }                            
            }
            else
            {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Saisie incorrecte");
                a.setContentText("La valeur inseree pour le volume par defaut est incorrecte");
                a.showAndWait();
            }
            
        });
        
        gp.add(lbPosTbView, 0, 0);
        gp.add(hbPosTbView, 1, 0);
        gp.add(lbWindowDefaultSize, 0, 1);
        gp.add(hbWindowDefaultSize, 1, 1);
        gp.add(lbDefaultVolume, 0, 2);
        gp.add(integerField, 1, 2);
        gp.add(btSave, 1, 3);
        
        bdp.setCenter(gp);
        
    }

    public Preferences() {
    }

    public Preferences(String tableViewPosition, int defaultVolume, String windowDefaultSize) {
        this.tableViewPosition = tableViewPosition;
        this.defaultVolume = defaultVolume;
        this.windowDefaultSize = windowDefaultSize;
    }
    
    private String tableViewPosition;
    private int defaultVolume;
    private String windowDefaultSize;

    public String getWindowDefaultSize() {
        return windowDefaultSize;
    }

    public void setWindowDefaultSize(String windowDefaultSize) {
        this.windowDefaultSize = windowDefaultSize;
    }

    public int getDefaultVolume() {
        return defaultVolume;
    }

    public void setDefaultVolume(int defaultVolume) {
        this.defaultVolume = defaultVolume;
    }

    public String getTableViewPosition() {
        return tableViewPosition;
    }

    public void setTableViewPosition(String tableViewPosition) {
        this.tableViewPosition = tableViewPosition;
    }
    
    public void preferencesWindow(Stage stage)
    {
        this.setTitle("Parametres");
        this.setMinHeight(250);
        this.setMinWidth(400);
        this.getIcons().add(new ImageView("/images/settings icon.png").getImage());
        bdp.setPadding(new Insets(15));
        Scene sc = new Scene(bdp);
        sc.getStylesheets().add(getClass().getResource("sliders-styles.css").toExternalForm());  
        this.setScene(sc);
        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(stage);
        this.setResizable(false);
        this.setMaximized(false);
        
        fonctionnalites();
        // Print the scene graph structure to the console
        System.out.println("Scene Graph Structure:");
        dumpTreeVisualPrintln(sc.getRoot());
        
        this.show();
    }
    private void dumpTreeVisualPrintln(Node node) {
        if (node == null) {
            return;
        }
        System.out.println(node);
        if (node instanceof Parent) {
            Parent parent = (Parent) node;
            for (Node child : parent.getChildrenUnmodifiable()) {
                dumpTreeVisualPrintln(child);
            }
        }
    }
}
