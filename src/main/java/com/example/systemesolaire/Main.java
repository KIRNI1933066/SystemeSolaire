package com.example.systemesolaire;

import com.example.systemesolaire.controllers.Controlleur;
import com.example.systemesolaire.corpscelestes.Planete;
import com.example.systemesolaire.corpscelestes.Vaisseau;
import com.example.systemesolaire.utilitaires.Constantes;
import com.example.systemesolaire.utilitaires.Skybox;
import com.example.systemesolaire.utilitaires.Vecteur3;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;

public class Main extends Application {


    public static final double ECHELLE = 400000;
    public static final Vecteur3 POS_SOLEIL = new Vecteur3();
    private static final int LARGEUR_SCENE = 1000;
    private static final int HAUTEUR_SCENE = 1000;
    private static double temps = 0;
    //0.Mercure 1.Venus 2.Terre 3.Mars 4.Jupiter 5.Saturn 6.Uranus 7.Neptune
    private static final double[] TEMPS_PLANETES = {0.5,0.5,0.75,0.8,0.4,0.3,0,0.1};
    private static final double[] FACTEURS_VITESSE = {1.6075,1.176,1,0.8085,0.4389,0.3254,0.2287,0.1823};
    private static final double V_BASE_TERRE = 0.0001;
    public static Group GROUP_SYSTEME_SOLAIRE = new Group();
    public static Group principal = new Group();
    private static final Planete[] PLANETES = new Planete[8];
    @Override
    public void start(Stage stage) {

        Image vide = new Image(Constantes.IMAGES_PATH + "Blank.jpg");
        Image menuImage = new Image(Constantes.IMAGES_PATH + "menuimage.jpg");
        Image back = new Image(Constantes.IMAGES_PATH + "bkg1_back.jpg");
        Image bot = new Image(Constantes.IMAGES_PATH + "bkg1_bot.jpg");
        Image front = new Image(Constantes.IMAGES_PATH + "bkg1_front.jpg");
        Image left = new Image(Constantes.IMAGES_PATH + "bkg1_left.jpg");
        Image right = new Image(Constantes.IMAGES_PATH + "bkg1_right.jpg");
        Image top = new Image(Constantes.IMAGES_PATH + "bkg1_top.jpg");

        Scene scene2D = new Scene(principal, LARGEUR_SCENE, HAUTEUR_SCENE);
        scene2D.getStylesheets().add("file:src/main/java/com/example/systemesolaire/css/infoplanete.css");

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(1);
        camera.setFarClip(1000000);

        final LocalDateTime[] ld = {LocalDateTime.of(0, Month.JANUARY, 1, 0, 0)};
        Text tempsReelText = new Text(ld[0].toString());
        PointLight pointLight = new PointLight();
        pointLight.setColor(Color.ORANGE);
        GROUP_SYSTEME_SOLAIRE.getChildren().add(pointLight);
        GROUP_SYSTEME_SOLAIRE.getChildren().add(new AmbientLight());

        Skybox skybox = new Skybox(top,bot,left,right,front,back,1000000,camera);
        GROUP_SYSTEME_SOLAIRE.getChildren().addAll(skybox);

        Sphere soleil = new Sphere(10);
        PhongMaterial matSoleil = new PhongMaterial();
        matSoleil.setDiffuseColor(Color.ORANGE);
        matSoleil.setSelfIlluminationMap(vide);
        soleil.setMaterial(matSoleil);
        GROUP_SYSTEME_SOLAIRE.getChildren().addAll(soleil);

        Slider sliderVitesseTemps = new Slider(0.05,500,5);
        sliderVitesseTemps.setTranslateY(30);
        sliderVitesseTemps.setTranslateX(20);

        Vaisseau vaisseau = new Vaisseau(PLANETES,new Vecteur3(0,0,0));

        new Controlleur(stage,scene2D,camera,vaisseau);


        Constantes.InfoPlanetes[] infoPlanetes = Constantes.InfoPlanetes.values();
        for (int i = 0; i < PLANETES.length; i++) {
            PLANETES[i] = new Planete(infoPlanetes[i].radius, infoPlanetes[i].periapsis, infoPlanetes[i].apoapsis, infoPlanetes[i].name, infoPlanetes[i].texture, i, infoPlanetes[i].masse);
            Group planeteSeule = new Group(PLANETES[i]);
            planeteSeule.getTransforms().addAll(
                    new Rotate(infoPlanetes[i].inclination, Rotate.X_AXIS),
                    new Rotate(infoPlanetes[i].inclination, Rotate.Y_AXIS));
            GROUP_SYSTEME_SOLAIRE.getChildren().add(planeteSeule);
        }

        long startNanoTime = System.nanoTime();
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                vaisseau.updatePosition();
                double vitesseBase = V_BASE_TERRE * sliderVitesseTemps.valueProperty().get();

                ld[0] = ld[0].plusSeconds((long)(vitesseBase * 3.156E7));
                System.out.println(vitesseBase);
                tempsReelText.setText(ld[0].toString());

                temps = temps + sliderVitesseTemps.valueProperty().get();
                for (int i = 0; i < TEMPS_PLANETES.length; i++) {
                    TEMPS_PLANETES[i] = TEMPS_PLANETES[i] + vitesseBase * FACTEURS_VITESSE[i];
                    if (TEMPS_PLANETES[i] > 1)
                        TEMPS_PLANETES[i] = 0;
                }
                if (temps > 1)
                    temps = 0;
                for (Planete planet : PLANETES) {
                    switch (planet.nom) {
                        case "Mercure" -> planet.updatePosition(POS_SOLEIL, TEMPS_PLANETES[0],0);
                        case "Venus" -> planet.updatePosition(POS_SOLEIL, TEMPS_PLANETES[1],1);
                        case "Terre" -> planet.updatePosition(POS_SOLEIL, TEMPS_PLANETES[2],2);
                        case "Mars" -> planet.updatePosition(POS_SOLEIL, TEMPS_PLANETES[3],3);
                        case "Jupiter" -> planet.updatePosition(POS_SOLEIL, TEMPS_PLANETES[4],4);
                        case "Saturne" -> planet.updatePosition(POS_SOLEIL, TEMPS_PLANETES[5],5);
                        case "Uranus" -> planet.updatePosition(POS_SOLEIL, TEMPS_PLANETES[6],6);
                        case "Neptune" -> planet.updatePosition(POS_SOLEIL, TEMPS_PLANETES[7],7);
                    }
                }
            }
        }.start();

        Group racine3D = new Group();
        SubScene sceneSystemeSolaire = new SubScene(racine3D, LARGEUR_SCENE,HAUTEUR_SCENE,true, SceneAntialiasing.BALANCED);
        sceneSystemeSolaire.setFill(Color.BLACK);
        sceneSystemeSolaire.setCamera(camera);

        BorderPane menu = new BorderPane();
        menu.setPrefSize(LARGEUR_SCENE, HAUTEUR_SCENE);
        VBox vb = new VBox();
        Font font = Font.font("Courier New", FontWeight.BOLD, 20);
        tempsReelText.setFont(new Font(20));
        tempsReelText.setFill(Color.WHITE);
        tempsReelText.setTranslateX(stage.getWidth()/2);
        tempsReelText.setTranslateY(50);
        Button exit = new Button("X");
        exit.setTranslateX(stage.getWidth() - 50);
        exit.setTranslateY(20);
        exit.setStyle("-fx-background-color: #8A2BE2;");
        exit.setFont(font);
        exit.setOnAction(ev -> {
            racine3D.getChildren().remove(GROUP_SYSTEME_SOLAIRE);
            principal.getChildren().removeAll(sliderVitesseTemps, exit, tempsReelText, sceneSystemeSolaire);
            principal.getChildren().addAll(menu);
        });

        Button bouttonSysteme = new Button("Système Solaire");
        bouttonSysteme.setStyle("-fx-background-color: #8A2BE2;");
        bouttonSysteme.setFont(font);
        bouttonSysteme.setOnAction(ev -> {
            principal.getChildren().remove(menu);
            principal.getChildren().addAll(sceneSystemeSolaire, sliderVitesseTemps, exit, tempsReelText);
            racine3D.getChildren().addAll(GROUP_SYSTEME_SOLAIRE);
            GROUP_SYSTEME_SOLAIRE.getChildren().remove(vaisseau);
        });

        Button bouttonVaisseau = new Button("Envoyer vaisseau");
        bouttonVaisseau.setStyle("-fx-background-color: #8A2BE2;");
        bouttonVaisseau.setFont(font);
        bouttonVaisseau.setOnAction(ev -> {
            principal.getChildren().remove(menu);
            principal.getChildren().addAll(sceneSystemeSolaire, sliderVitesseTemps, exit, tempsReelText);
            racine3D.getChildren().addAll(GROUP_SYSTEME_SOLAIRE);
            vaisseau.setBouger(true);
            GROUP_SYSTEME_SOLAIRE.getChildren().addAll(vaisseau);
        });
        vb.getChildren().addAll(bouttonSysteme,bouttonVaisseau);
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(20);
        menu.setBackground(new Background(new BackgroundImage(menuImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,false,false,true,true))));
        menu.setCenter(vb);




        principal.getChildren().addAll(menu);

        stage.setScene(scene2D);
        stage.setFullScreen(true);
        stage.widthProperty().addListener((observable -> {
            sceneSystemeSolaire.setWidth(stage.getWidth());
            menu.setPrefWidth(stage.getWidth());
            exit.setTranslateX(stage.getWidth() - 75);
            tempsReelText.setTranslateX(stage.getWidth()/2);
        }));
        stage.heightProperty().addListener((observable -> {
            sceneSystemeSolaire.setHeight(stage.getHeight());
            menu.setPrefHeight(stage.getHeight());

        }));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}