/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mediaPlayerProject;

import java.io.File;
import java.util.Random;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.Line.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;

/**
 *
 * @author Test
 */
public class MediaPlayerProject extends Application{

    /**
     * @param args the command line arguments
     */
    Scene sc;
    File selectedFile = null;
    Stage stage = null;
    BorderPane bdp = new BorderPane();
    FileChooser fileChooser = null;
    Media media = null;
    MediaPlayer mediaPlayer = null;
    MediaView mediaView = null;
    MediaList selectedMedia = null;
    TableView<MediaList> tableView = new TableView();
    // buttons    
    Button btPrevious = new Button();
    Button btNext = new Button();    
    Button btPause = new Button();
    Button btFastForward = new Button();
    Button btRewind = new Button();
    Button btStop = new Button();
    Button btMute = new Button();
    Button btAddMedia = new Button("Ajouter media");
    Button btRemoveMedia = new Button("Enlever media");    
    // icons 
    ImageView playIcon = new ImageView("images/icons/play icon.png");
    ImageView pauseIcon = new ImageView("images/icons/pause icon.png");
    ImageView nextTrackIcon = new ImageView("images/icons/next track icon.png");
    ImageView previousTrackIcon = new ImageView("images/icons/previous track icon.png");    
    ImageView fastForwardIcon = new ImageView("images/icons/fast forward icon.png");    
    ImageView rewindIcon = new ImageView("images/icons/rewind icon.png");    
    ImageView muteIcon = new ImageView("images/icons/mute icon (1).png");
    ImageView unmuteIcon = new ImageView("images/icons/unmute icon (1).png");
    // background images
    ImageView backgroundImage_1 = new ImageView("images/backgrounds/background 1.jpg");
    ImageView backgroundImage_2 = new ImageView("images/backgrounds/background 2.jpg");
    ImageView backgroundImage_3 = new ImageView("images/backgrounds/background 3.jpg");
    ImageView backgroundImage_4 = new ImageView("images/backgrounds/background 4.jpg");
    ImageView backgroundImage_5 = new ImageView("images/backgrounds/background 5.jpg");
    ImageView backgroundImage_6 = new ImageView("images/backgrounds/background 6.jpg");
    ImageView backgroundImage_7 = new ImageView("images/backgrounds/background 7.jpg");
    ImageView backgroundImage_8 = new ImageView("images/backgrounds/background 8.jpg");    
    ImageView[] backgroundImages = new ImageView[8];
    // sliders 
    Slider mediaProgress;
    Slider volumeControl = new Slider();
    // texts
    Text dureeActuelle;
    Text dureeFin;
    // layouts
    HBox muteAndVolume = new HBox(10, btMute, volumeControl);
    HBox hbButtons = new HBox(10, btPrevious, btRewind, btPause, btFastForward, btNext, muteAndVolume);
    VBox buttonAndMediaProgressBar = new VBox(hbButtons);
    StackPane centerPane_Audio = new StackPane();
    StackPane centerPane_Video = new StackPane();
    // preferences 
    double defaultVolume;
    String windowDefaultSize;
    String posTbView;    
    // utilitaires 
    double volumeBeforeMute;
    String removedTitles = "";
    String mediaType;
        
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }

    @Override
    public void start(Stage pstage) throws Exception {
        stage = pstage;
        fenetre();
    }
    
    public void initialisation()
    {
        bdp.widthProperty().addListener(bdpSizeChangeListener);
        mediaProgress = new Slider();
//        volumeControl = new Slider();
        
        dureeActuelle = new Text();
        dureeFin = new Text();
        
        dureeActuelle.setText("00:00:00");
        dureeFin.setText("00:00:00");
        
        stage.setTitle("Media Player");
        stage.getIcons().add(new ImageView("images/logo media player.png").getImage());
        stage.setMinWidth(780);
        stage.setMinHeight(371);
        
        try
        {
            if(windowDefaultSize.equalsIgnoreCase("Maximized"))
                stage.setMaximized(true);            
        }
        catch (NullPointerException e) {    
            System.err.println("windowDefaultSize is null, unable to set window size.");
            // You can also log the exception or take other appropriate actions
        }
        
        sc = new Scene(bdp);
        sc.getStylesheets().add(getClass().getResource("table-styles.css").toExternalForm());                      
        sc.getStylesheets().add(getClass().getResource("sliders-styles.css").toExternalForm());                      
        stage.setWidth(1012.4);
        stage.setHeight(400);
        stage.setScene(sc);                
    }
    
    private void initBackgroundImages()
    {
        backgroundImages[0] = backgroundImage_1;
        backgroundImages[1] = backgroundImage_2;
        backgroundImages[2] = backgroundImage_3;
        backgroundImages[3] = backgroundImage_4;
        backgroundImages[4] = backgroundImage_5;
        backgroundImages[5] = backgroundImage_6;
        backgroundImages[6] = backgroundImage_7;
        backgroundImages[7] = backgroundImage_8;       
    }
    
    private void initMediaProgress()
    {
        mediaProgress.setMin(1);                
        mediaProgress.valueProperty().addListener((ObservableValue<? extends Number> observable,
                Number ancienneValeur, Number nouvelleValeur) -> {
            int diff = nouvelleValeur.intValue() - ancienneValeur.intValue();
            //savoir si l'utilisateur veut avancer ou reculer le media en cours 
            if((mediaPlayer!=null)&&((diff>1)||(diff<0)))             
            {
                double nouvelleDuree = nouvelleValeur.intValue() * 1000;
                mediaPlayer.seek(new Duration(nouvelleDuree));
            }            
        });        
    }
    private void initVolumeControl()
    {                
        volumeControl.setValue(defaultVolume); 
        if (volumeControl.getValue() == defaultVolume)            
        volumeControl.valueProperty().addListener((ObservableValue<? extends Number> observable, Number ancienneValeur, Number nouvelleValeur) -> {            
            if(mediaPlayer != null)
            {
                mediaPlayer.setVolume(nouvelleValeur.doubleValue()/100);
            }
            if(nouvelleValeur.doubleValue() == 0)
            {
                btMute.setGraphic(muteIcon);                
            }
            else if (nouvelleValeur.doubleValue() >= 1)
            {
                btMute.setGraphic(unmuteIcon);
                if((mediaPlayer != null) && mediaPlayer.isMute())
                    mediaPlayer.setMute(false);                    
            }
                
            if(mediaPlayer != null)
                    mediaPlayer.setVolume(nouvelleValeur.doubleValue()/100);            
        });
    }
    
    void fenetre()
    {        
        getPref();
        initialisation();
        initVolumeControl();
        initBackgroundImages();
        tableView();
        button(); 
        creerMenu();
                
        stage.show();        
    }
        
    void creerMenu()
    {
        MenuBar menuBar = new MenuBar();
//        Menu mnMediaPlayer = new Menu();
//        Label lbMediaPlayer = new Label("Media Player");
//        mnMediaPlayer.setGraphic(lbMediaPlayer);
        Menu mnPreferences = new Menu();
        Label lbPreferences = new Label("Preferences");
        mnPreferences.setGraphic(lbPreferences);
        
        lbPreferences.setOnMouseClicked(e->{
            preferencesWindow();
        });        
        menuBar.getMenus().addAll(mnPreferences);
        bdp.setTop(menuBar);
    }
    private void preferencesWindow()
    {
        Preferences pref = new Preferences();
        pref.preferencesWindow(stage);
    }
    
    File getFile()
    {
        File file = null;
        // creer une instance de FileChooser pour pouvoir selectionner les media
        fileChooser = new FileChooser(); 
        fileChooser.setTitle("Select Media File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("mp4", "*.mp4"),
            new FileChooser.ExtensionFilter("mp3", "*.mp3"),            
            new FileChooser.ExtensionFilter("flv", "*.flv"),
            new FileChooser.ExtensionFilter("3gp", "*.3gp"),
            new FileChooser.ExtensionFilter("wma", "*.wma"),
            new FileChooser.ExtensionFilter("wav", "*.wav"),
            new FileChooser.ExtensionFilter("ogg", "*.ogg"),
            new FileChooser.ExtensionFilter("wmv", "*.wmv")            
        );
        file = fileChooser.showOpenDialog(stage);                           
        
       return file;
    }
    
    Media getMedia(File selectedFile)
    {
        Media currentMedia = null;        
        if (selectedFile != null)
        {               
            currentMedia = new Media(selectedFile.getAbsoluteFile().toURI().toString());
            
            //getMediaPlayer(media);
            return currentMedia;
        }
        else
            return null;        
    }
    
    MediaPlayer getMediaPlayer(Media media)
    {    
       // creer une intance de MediaPlayerProject
       MediaPlayer currentMediaPlayer = new MediaPlayer(media);

       currentMediaPlayer.setOnReady(()->{
           mediaProgress.setValue(1);
           mediaProgress.setMax(currentMediaPlayer.getMedia().getDuration().toMillis()/1000);
           dureeFin.setText(durationToString(currentMediaPlayer.getMedia().getDuration()));           
           //mediaPlayer.setAutoPlay(true);
           currentMediaPlayer.play();
       });
       dureeActuelle.setText("00:00:00");
       dureeFin.setText("00:00:00");
       
       currentMediaPlayer.currentTimeProperty().addListener((ObservableValue<? extends Duration> observable,
            Duration ancienneValeur, Duration nouvelleValeur)-> {
            mediaProgress.setValue(nouvelleValeur.toSeconds());
            dureeActuelle.setText(durationToString(nouvelleValeur));
       });
        
        // creer une instance de MediaView
        mediaView = new MediaView(currentMediaPlayer);                                                                                                                                 

        // ajuster le size du media en cours par rapport a la taille de la fenetre 
        mediaView.fitWidthProperty().bind(bdp.widthProperty().subtract(tableView.widthProperty()));
        mediaView.fitHeightProperty().bind(bdp.heightProperty().subtract(buttonAndMediaProgressBar.heightProperty().add(25)));                            
        
        mediaType = getMediaType(currentMediaPlayer);
        if(mediaType.equalsIgnoreCase("video"))
        {
            // ajouter un fond noir a l'epace media         
            centerPane_Video.getChildren().add(mediaView);
            centerPane_Video.setStyle("-fx-background-color: black;"); 
            // ajouter le MediaView dans le BorderPane         
            bdp.setCenter(centerPane_Video);
        }
        else
        {
//            centerPane.setStyle("-fx-background-color: yellow;");            
              // Create a background image with desired properties
            centerPane_Audio.getChildren().add(mediaView);
            Random randomBackground = new Random();
            BackgroundImage backgroundImg = new BackgroundImage(
                    backgroundImages[randomBackground.nextInt(8)].getImage(),
                    BackgroundRepeat.NO_REPEAT, // Set repeat behavior
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER, // Position the image in the center
                    new BackgroundSize(
                            BackgroundSize.AUTO, // Width
                            BackgroundSize.AUTO, // Height
                            false, // Contain (preserve aspect ratio)
                            false, // Cover (resize to fill)
                            true, // Width proportional
                            true // Height proportional
                    )
            );

            // Set the background image for the StackPane                          
            centerPane_Audio.setBackground(new Background(backgroundImg));
            
            // ajouter le MediaView dans le BorderPane         
            bdp.setCenter(centerPane_Audio);
        }
        
        initMediaProgress();
//        initVolumeControl();        
        mediaProgress.setPrefWidth(bdp.getWidth() - (dureeFin.getWrappingWidth() + 10));
        HBox hbMediaProgress = new HBox(dureeActuelle, mediaProgress, dureeFin);                 
        buttonAndMediaProgressBar.getChildren().add(0, hbMediaProgress);                 
        
        currentMediaPlayer.setVolume(volumeControl.getValue()/100);
        
        currentMediaPlayer.setOnEndOfMedia(() ->{
            nextMedia();
        });                
        
        return currentMediaPlayer;
    }
    
    //---------------------------------------------------------------------------------
    // Fonctions utilitaires 
    public String generateID(String nom)
    {
        Random rand = new Random();
        if(nom.length() > 4)
            nom = nom.substring(0, 4);
        
        String code = nom + (rand.nextInt(900) + 100);
        
        return code;
    }
    
    public void refreshTbView()
    {
        ObservableList<MediaList> initialList = GestionFichier.lireDonnesApp();        
        ObservableList<MediaList> verifiedList = checkMediaExistence(initialList);
               
        tableView.setItems(verifiedList);
        // effacer les elements introuvables du fichier
        if(!GestionFichier.effacerMedia(verifiedList))        
            System.err.println("Les fichiers introuvables n'ont pas pu etre supprimes du fichier"); //debug            
    }
    
    public boolean checkExistence(String emplacement)
    {
        boolean exists = false;
        ObservableList<MediaList> list = GestionFichier.lireDonnesApp();
        
        for(MediaList md: list)
        {
            if(md.getEmplacement().equalsIgnoreCase(emplacement))
            {
                exists = true;
                break;
            }
        }
        return exists;
    }
    
    private String durationToString(Duration duration) {
        StringBuilder sb=new StringBuilder();
        int hours, minutes, seconds;
        String _hours, _minutes, _seconds;
        hours = (int) duration.toHours();
        minutes = (int) (duration.toMinutes() - hours*60);
        seconds = (int) (duration.toSeconds() - hours*3600 - minutes*60);
        
        if(hours < 10)
            _hours = "0" + Integer.toString(hours);
        else
            _hours = Integer.toString(hours);
        
        if(minutes < 10)
            _minutes = "0" + Integer.toString(minutes);
        else
            _minutes = Integer.toString(minutes);
        
        if(seconds < 10)
            _seconds = "0" + Integer.toString(seconds);
        else
            _seconds = Integer.toString(seconds);
  
        sb.append(_hours).append(":").append(_minutes).append(":").append(_seconds);
        return sb.toString();
    }
    
    private String formatNumber(int nb) {
        if(nb<10)
            return "0"+nb;
        else
            return Integer.toString(nb);
    }
    private boolean isCurrentlyPlayingMedia(MediaList md)
    {        
        if(selectedFile.getAbsolutePath().equalsIgnoreCase(md.getEmplacement()))
        {
            System.out.println("Le media enleve etait en cours ");
            return true;
        }
        else
        {            
            System.out.println("Le media enleve n'etait pas en cours ");
            return false;
        }
    }
    ChangeListener<Number> bdpSizeChangeListener = (ObservableValue<? extends Number> observable, 
            Number ancienneValeur, Number nouvelleValeur) -> { 
        if(bdp.getWidth() > 1358)
        {
            muteAndVolume.translateXProperty().bind(
                    //Bindings.subtract(bdp.widthProperty(), volumeControl.widthProperty()).subtract(10)
                    bdp.widthProperty().divide(4)
            );              
        }
        else if(bdp.getWidth() > 933)
        {
            muteAndVolume.translateXProperty().bind(
                    //Bindings.subtract(bdp.widthProperty(), volumeControl.widthProperty()).subtract(10)
                    bdp.widthProperty().divide(7)
            );              
        }
        else
        {
            muteAndVolume.translateXProperty().bind(
                    //Bindings.subtract(bdp.widthProperty(), volumeControl.widthProperty()).subtract(10)
                    bdp.widthProperty().divide(15)
            ); 
        }
    };
    
    private ObservableList<MediaList> checkMediaExistence(ObservableList<MediaList> initialList)
    {        
        File file;
        Media chkMedia;
        boolean breaked = true;
        
        while (breaked)
        {
            breaked = false;
            for (MediaList md: initialList )
            {
                file = new File(md.getEmplacement());
                try
                {
                    chkMedia = new Media(file.getAbsoluteFile().toURI().toString());                                
                }
                catch(MediaException e)
                {
    //                if(media.getSource() == null)
                    System.out.println("Fichier non trouve: " + md.getNom());
                    removedTitles += (md.getNom() + "\n");  
                    initialList.remove(md);                
                    breaked = true;
                    break;
                }
            }            
        }                        
        return initialList;
    }
    
    private String getMediaType(MediaPlayer mp)
    {
        if(
                mp.getMedia().getSource().endsWith(".mp4") ||
                mp.getMedia().getSource().endsWith(".flv") ||
                mp.getMedia().getSource().endsWith(".3gp") ||
                mp.getMedia().getSource().endsWith(".wmv")
          )
            return "video";
        else
            return "audio";
    }
    
    // Fin fonctions utilitaires
   //-------------------------------------------------------------------------------------------------
    
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
            defaultVolume = p.getDefaultVolume();            
            posTbView = p.getTableViewPosition();
            windowDefaultSize = p.getWindowDefaultSize();
        } 
        else
            System.out.println("Les parametres n'ont pas pu etre recuperes"); // debug
            
    }
    void tableView()
    {
        refreshTbView();
        if (removedTitles.length() > 0)
        {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Elements introuvable");
            a.setContentText("Ces elements ont ete deplaces ou supprimes: \n" + removedTitles);
            a.showAndWait();
        }
        TableColumn<MediaList, String> colID = new TableColumn<>("ID");
        TableColumn<MediaList, String> colNom = new TableColumn<>("Nom");
        TableColumn<MediaList, String> colEmplacement = new TableColumn<>("Emplacement");        
        
        colID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        colEmplacement.setCellValueFactory(new PropertyValueFactory<>("Emplacement"));        
        
        colID.setPrefWidth(62);
        colEmplacement.setPrefWidth(190);
        
        tableView.getColumns().addAll(colNom, colEmplacement);
        // ajuster le size du tableView par rapport a la taille de la fenetre 
        tableView.prefHeightProperty().bind(bdp.heightProperty().subtract(buttonAndMediaProgressBar.heightProperty().add(10)));                
        tableView.setMinWidth(400);                

        tableView.setOnMouseClicked(e->{
        
        });
        HBox hBoxButton = new HBox(10, btAddMedia, btRemoveMedia);        
        hBoxButton.setPadding(new Insets(5, 0, 10, 0));
        hBoxButton.setAlignment(Pos.CENTER_RIGHT);
        VBox vBox = new VBox(10, tableView, hBoxButton);
        
        tableView.setOnMouseClicked(event->{
            tableEvent();
            if(event.getClickCount() == 2 && !tableView.getSelectionModel().isEmpty())
            {
                if(mediaPlayer != null)
                {
                    stopMedia();
                }
                MediaList md = tableView.getSelectionModel().getSelectedItem();                
                  selectedFile = new File(md.getEmplacement());                  
                  media = getMedia(selectedFile);
                  mediaPlayer = getMediaPlayer(media);      
                  btPause.setGraphic(pauseIcon);
            }
        });    
        if(posTbView.equalsIgnoreCase("droite"))
            bdp.setRight(vBox);
        else
            bdp.setLeft(vBox);
    }
    
    void tableEvent()
    {
        selectedMedia = tableView.getSelectionModel().getSelectedItem();        
    }
    
    void button()
    {         
//        volumeControl.setTranslateX(150);        
                       
//        volumeControl.snapPositionX(bdp.widthProperty().doubleValue() - volumeControl.widthProperty().doubleValue());        
        btPause.setPrefHeight(22);
        btPause.setPrefWidth(47);
        btStop.setPrefHeight(22);
        btStop.setPrefWidth(47);
        btMute.setPrefHeight(22);
        btMute.setPrefWidth(47);
        btAddMedia.setPrefWidth(100);
        btAddMedia.setPrefHeight(22); 
        
        btPrevious.setGraphic(previousTrackIcon);
        btPause.setGraphic(pauseIcon);        
        btNext.setGraphic(nextTrackIcon);
        btMute.setGraphic(unmuteIcon);
        btFastForward.setGraphic(fastForwardIcon);
        btRewind.setGraphic(rewindIcon);
        
        btPrevious.setStyle("-fx-background-color: transparent;");
        btPause.setStyle("-fx-background-color: transparent;");
        btNext.setStyle("-fx-background-color: transparent;");
        btMute.setStyle("-fx-background-color: transparent;");  
        btFastForward.setStyle("-fx-background-color: transparent;");
        btRewind.setStyle("-fx-background-color: transparent;");      
       
        //HBox hbBtMute = new HBox(btMute);
        //hbBtMute.setAlignment(Pos.CENTER_RIGHT);
                
        hbButtons.setPadding(new Insets(12));
        //hbButtons.setPrefWidth(bdp.getWidth() - tableView.getWidth());
        hbButtons.setAlignment(Pos.CENTER);        
        hbButtons.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-border-style: solid; -fx-background-color: #f2e5d9;");    
        muteAndVolume.setAlignment(Pos.CENTER);
        muteAndVolume.setPadding(new Insets(5));
        //HBox bottomContent = new HBox(hbButtons, btMute);                        
        buttonAndMediaProgressBar.setPrefWidth(bdp.getWidth() - tableView.getWidth());
        buttonAndMediaProgressBar.setPrefHeight(98);
        buttonAndMediaProgressBar.setMinHeight(98);
        bdp.setBottom(buttonAndMediaProgressBar);
                
        btPause.setOnAction(e-> {
            playOrPauseMedia();
        });
        btStop.setOnAction(e->{
            stopMedia();
        });
        btMute.setOnAction(e-> {
            muteMedia();
        });
        btFastForward.setOnAction(e->{
            fastForwardMedia();
        });
        btRewind.setOnAction(e->{
            rewindMedia();
        });
        btNext.setOnAction(e->{
            nextMedia();
        });
        btPrevious.setOnAction(e->{
            previousMedia();
        });        
        btAddMedia.setOnAction(e-> {
            selectedFile = getFile();            
            if((mediaPlayer == null) || (mediaPlayer.getStatus() == mediaPlayer.getStatus().STOPPED))
            {
                if(selectedFile != null)
                {
                    media = getMedia(selectedFile);
                    if (media != null)
                        mediaPlayer = getMediaPlayer(media);                    
                }
            }
            saveMedia(selectedFile);
        });
        btRemoveMedia.setOnAction(e->{            
            if (!tableView.getSelectionModel().isEmpty())
            {                
                boolean suppressionConfirmee = showConfirmationDialog("Suppression", "Veuillez confirmer la suppression", "Etes-vous sur de vouloir supprimer l'element selectionne?");
                if ((selectedMedia != null) & suppressionConfirmee)
                {                
                    ObservableList<MediaList> List = GestionFichier.lireDonnesApp();
                    for (MediaList md: List)
                    {
                        if(md.getEmplacement().equalsIgnoreCase(selectedMedia.getEmplacement()))
                        {
                            if(isCurrentlyPlayingMedia(md))
                                stopMedia();
                            List.remove(md);
                            if(GestionFichier.effacerMedia(List))
                                refreshTbView();
                            else 
                            {
                                Alert a = new Alert(Alert.AlertType.ERROR);
                                a.setTitle("Operation echouee");
                                a.setContentText("Erreur survenue lors de la suppresion");
                                a.show();
                            }
                        }
                    }
                }
            }
        });                
    }  
    
    public boolean showConfirmationDialog(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }        
    
    private void saveMedia(File file)
    {                
        if ((file != null))
        {
            String filePath = file.getAbsolutePath();
            if (!checkExistence(filePath))
            {                
                String fileName = file.getName();
                String ID;
                ID = generateID(fileName);
                MediaList md = new MediaList(ID, fileName, filePath);

                if(GestionFichier.ecrireDonneesApp(md))
                    refreshTbView();
                else
                {
                    System.out.println("Erreur survenue lors de l'ecriture des donnees"); //debug
                    Alert echec = new Alert(Alert.AlertType.ERROR);
                    echec.setTitle("Echec de l'ajout");
                    echec.setContentText("Le media n'a pas pu etre ajoute");
                    echec.show();
                }
            }
        }
        if(selectedFile == null)
                System.out.println("Fichier selectionne null"); //debug
    }   
    
    private void nextMedia() 
    {
        if (mediaPlayer != null)
        {            
            
            int currentIndex = -1;
            String currentMediaSource = mediaPlayer.getMedia().getSource();      
            ObservableList<MediaList> mediaList = GestionFichier.lireDonnesApp();

            // Recherche de l'index de la piste actuelle dans la liste de MediaFile
            for (int i = 0; i < mediaList.size(); i++) {
                Media mediaFiles = new Media((new File(mediaList.get(i).getEmplacement())).getAbsoluteFile().toURI().toString());
                if (mediaFiles.getSource().equals(currentMediaSource)) {
                    currentIndex = i;
                    break;
                }
            }
            if (currentIndex >= 0 && currentIndex < mediaList.size() - 1) {
                // Il y a une piste suivante dans la liste
                String nextTrack = mediaList.get(currentIndex + 1).getEmplacement(); // Obtenir le nom de la piste suivante
                stopMedia();
                mediaPlayer = getMediaPlayer(new Media(new File(nextTrack).getAbsoluteFile().toURI().toString()));
                tableView.getSelectionModel().select(currentIndex+1);
            } else {
                // Il n'y a pas de piste suivante dans la liste, recommencez la piste actuelle
                mediaPlayer.seek(javafx.util.Duration.ZERO);
            }
        }
    }
    private void previousMedia()
    {
        if (mediaPlayer != null)
        {                        
            int currentIndex = -1;
            String currentMediaSource = mediaPlayer.getMedia().getSource();        
            ObservableList<MediaList> mediaList = GestionFichier.lireDonnesApp();

            // Recherche de l'index de la piste actuelle dans la liste de MediaFile
            for (int i = 0; i < mediaList.size(); i++) {
                Media mediaFiles = new Media((new File(mediaList.get(i).getEmplacement())).getAbsoluteFile().toURI().toString());
                if (mediaFiles.getSource().equals(currentMediaSource)) {
                    currentIndex = i;
                    break;
                }
            }
            if (currentIndex > 0) {
                // Piste précédente existe
                String previousTrack = mediaList.get(currentIndex - 1).getEmplacement();
                stopMedia();
                mediaPlayer = getMediaPlayer(new Media(new File(previousTrack).getAbsoluteFile().toURI().toString()));
                tableView.getSelectionModel().select(currentIndex-1);
            } else {
                // Pas de piste précédente, recommencez la piste actuelle
                mediaPlayer.seek(javafx.util.Duration.ZERO);
            }
        }
    }
    
    private void fastForwardMedia()
    {
        if(mediaPlayer != null)
        {
            double time = mediaProgress.getValue();
            time += 10;
            mediaProgress.setValue(time);
            System.out.println("Video avancee"); // debug
        }
    }
    private void rewindMedia()
    {
        if(mediaPlayer != null)
        {
            double time = mediaProgress.getValue();
            time -= 10;
            mediaProgress.setValue(time);
            System.out.println("Video reculee"); // debug
        }
    }
    
    private void playOrPauseMedia()
    {
        if (mediaPlayer != null)
        {            
            if (mediaPlayer.getStatus().equals(mediaPlayer.getStatus().PAUSED))
            {
                mediaPlayer.play();
                //btPause.setText("Pause");
                btPause.setGraphic(pauseIcon);
            }
            if (mediaPlayer.getStatus().equals(mediaPlayer.getStatus().PLAYING))
            {
                mediaPlayer.pause();
                //btPause.setText("Play");
                btPause.setGraphic(playIcon);
            }
        }
    }
    
    private void stopMedia()
    {
        if (mediaPlayer != null)
        {            
            if(mediaPlayer.getStatus().equals(mediaPlayer.getStatus().PLAYING))        
            {
                mediaPlayer.stop();
                mediaView.setMediaPlayer(null);
    //            btStop.setText("Play");
            }
            if (mediaPlayer.getStatus().equals(mediaPlayer.getStatus().STOPPED))
            {
                mediaPlayer.play();
    //            btStop.setText("Stop");
            }
        }
    }
    
    private void muteMedia()
    {
        if (mediaPlayer != null)
        {            
            if(mediaPlayer.isMute())        
            {
                mediaPlayer.setMute(false);
                mediaPlayer.setVolume(volumeBeforeMute);
                volumeControl.setValue(volumeBeforeMute*100);
                //btMute.setText("Mute");            
                btMute.setGraphic(unmuteIcon);            
            }
            else
            {
                volumeBeforeMute = mediaPlayer.getVolume();
                mediaPlayer.setMute(true);
                volumeControl.setValue(0);
                //btMute.setText("Unmute");            
                btMute.setGraphic(muteIcon);
            }
        }
    }
    // fonctions volume
    private void augmenterVolume()
    {
        if(volumeControl.getValue() < 1)
            volumeControl.setValue(volumeControl.getValue() + 0.1);
    }
    private void diminuerVolume()
    {
        if(volumeControl.getValue() > 0)
            volumeControl.setValue(volumeControl.getValue() - 0.1);
    }
}
