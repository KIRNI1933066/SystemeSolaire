package com.example.systemesolaire;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main extends Application {


    public static final double ECHELLE = 400000;
    public static final Vecteur2 POS_SOLEIL = new Vecteur2(0, 0);
    private static final int LARGEUR_SCENE = 1000;
    private static final int HAUTEUR_SCENE = 1000;
    private static double temps = 0;
    //0.Mercure 1.Venus 2.Terre 3.Mars 4.Jupiter 5.Saturn 6.Uranus 7.Neptune
    private static final double[] TEMPS_PLANETES = {0,0,0,0,0,0,0,0};
    private static final double[] FACTEURS_VITESSE = {1.6075,1.176,1,0.8085,0.4389,0.3254,0.2287,0.1823};
    private static final double V_BASE_TERRE = 0.0001;
    public static Group systeme = new Group();
    public static Group principal = new Group();
    @Override
    public void start(Stage stage) throws FileNotFoundException {

        Image vide = new Image(new FileInputStream("Blank.jpg"));
        Image menuImage = new Image(new FileInputStream("menuimage.jpg"));
        Image back = new Image(new FileInputStream("bkg1_back.jpg"));
        Image bot = new Image(new FileInputStream("bkg1_bot.jpg"));
        Image front = new Image(new FileInputStream("bkg1_front.jpg"));
        Image left = new Image(new FileInputStream("bkg1_left.jpg"));
        Image right = new Image(new FileInputStream("bkg1_right.jpg"));
        Image top = new Image(new FileInputStream("bkg1_top.jpg"));
        ImageView ivMenu = new ImageView(menuImage);

        Sphere soleil = new Sphere(10);
        PhongMaterial matSoleil = new PhongMaterial();
        matSoleil.setDiffuseColor(Color.ORANGE);
        matSoleil.setSelfIlluminationMap(vide);
        soleil.setMaterial(matSoleil);
        systeme.getChildren().addAll(soleil);

        Slider sliderVitesseTemps = new Slider(0.05,500,5);
        sliderVitesseTemps.setTranslateY(30);
        sliderVitesseTemps.setTranslateX(20);


        Planete[] planetes = new Planete[8];
        Constantes.InfoPlanetes[] infoPlanetes = Constantes.InfoPlanetes.values();
        for (int i = 0; i < planetes.length; i++) {
            planetes[i] = new Planete(infoPlanetes[i].radius, infoPlanetes[i].color, infoPlanetes[i].periapsis, infoPlanetes[i].apoapsis, infoPlanetes[i].name, infoPlanetes[i].texture, i, infoPlanetes[i].masse);
            Group planeteSeule = new Group(planetes[i]);
            planeteSeule.getTransforms().addAll(
                    new Rotate(infoPlanetes[i].inclination, Rotate.X_AXIS),
                    new Rotate(infoPlanetes[i].inclination, Rotate.Y_AXIS));
            systeme.getChildren().add(planeteSeule);
        }
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                double vitesseBase = V_BASE_TERRE * sliderVitesseTemps.valueProperty().get();
                temps = temps + sliderVitesseTemps.valueProperty().get();
                for (int i = 0; i < TEMPS_PLANETES.length; i++) {
                    TEMPS_PLANETES[i] = TEMPS_PLANETES[i] + vitesseBase * FACTEURS_VITESSE[i];
                    if (TEMPS_PLANETES[i] > 1)
                        TEMPS_PLANETES[i] = 0;
                }
                if (temps > 1)
                    temps = 0;
                for (Planete planet : planetes) {
                    switch (planet.name) {
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

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(1);
        camera.setFarClip(1000000);
        Skybox skybox = new Skybox(top,bot,left,right,front,back,80000,camera);
        systeme.getChildren().addAll(skybox);

        final PointLight pointLight = new PointLight();
        pointLight.setColor(Color.ORANGE);
        systeme.getChildren().add(pointLight);
        systeme.getChildren().add(new AmbientLight());

        Group racine3D = new Group();
        SubScene scene3D = new SubScene(racine3D, LARGEUR_SCENE,HAUTEUR_SCENE,true, SceneAntialiasing.BALANCED);
        scene3D.setFill(Color.BLACK);
        scene3D.setCamera(camera);


        Group menu = new Group();
        VBox vb = new VBox();
        Font font = Font.font("Courier New", FontWeight.BOLD, 20);
        Button exit = new Button("X");
        exit.setTranslateX(1050);
        exit.setStyle("-fx-background-color: #8A2BE2;");
        exit.setFont(font);
        exit.setOnAction(ev -> {
            racine3D.getChildren().remove(systeme);
            principal.getChildren().removeAll(sliderVitesseTemps,exit,scene3D);
            principal.getChildren().addAll(menu);
        });

        Button bouttonSysteme = new Button("Système Solaire");
        bouttonSysteme.setStyle("-fx-background-color: #8A2BE2;");
        bouttonSysteme.setFont(font);
        bouttonSysteme.setOnAction(ev -> {
            principal.getChildren().remove(menu);
            principal.getChildren().addAll(scene3D,sliderVitesseTemps,exit);
            racine3D.getChildren().addAll(systeme);
        });

        Button bouttonVaisseau = new Button("Envoyer vaisseau");
        bouttonVaisseau.setStyle("-fx-background-color: #8A2BE2;");
        bouttonVaisseau.setFont(font);
        bouttonVaisseau.setOnAction(ev -> {
            principal.getChildren().remove(menu);
            principal.getChildren().addAll(scene3D,sliderVitesseTemps,exit);
            racine3D.getChildren().addAll(systeme);
        });
        vb.getChildren().addAll(bouttonSysteme,bouttonVaisseau);
        vb.setTranslateX(600);
        vb.setTranslateY(450);
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(20);
        menu.getChildren().addAll(ivMenu,vb);
        principal.getChildren().addAll(menu);

        Scene scene2D = new Scene(principal, LARGEUR_SCENE, HAUTEUR_SCENE);
        scene2D.getStylesheets().add("file:src/main/java/com/example/systemesolaire/css/infoplanete.css");
        new Controlleur(stage,scene2D,camera);
        systeme.getChildren().addAll(new Vaisseau(0,0,0,planetes,scene2D));
        //mouseControl(stage, scene2D, camera);

        stage.setScene(scene2D);
        stage.setFullScreen(true);
        stage.widthProperty().addListener((observable -> scene3D.setWidth(stage.getWidth())));
        stage.heightProperty().addListener((observable -> scene3D.setHeight(stage.getHeight())));
        stage.show();
    }



    /*private void mouseControl(Stage stage, Scene scene, Camera camera)
    {
        pivot = new Translate(0, 0, 0);
        zoom = new Translate(0, 0, -3000);
        Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
        Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);

        camera.getTransforms().addAll(
                pivot,
                rotateY,
                rotateX,
                zoom
        );

        Vecteur2 basePos = new Vecteur2();
        Vecteur2 basePivot = new Vecteur2(pivot.getX(), pivot.getY());
        Vecteur2 baseRotate = new Vecteur2();
        scene.setOnMousePressed((mouseEvent -> {
            basePos.setX(mouseEvent.getSceneX());
            basePos.setY(mouseEvent.getSceneY());

            if (mouseEvent.isPrimaryButtonDown())
            {
                basePivot.setX(zoom.getX());
                basePivot.setY(zoom.getY());
            }
            if (mouseEvent.isSecondaryButtonDown())
            {
                baseRotate.setX(rotateY.angleProperty().get());
                baseRotate.setY(rotateX.angleProperty().get());
            }
        }));
        scene.setOnMouseDragged((mouseEvent ->
        {
            if (mouseEvent.isPrimaryButtonDown())
            {
                if (pivot.xProperty().isBound())
                {
                    pivot.xProperty().unbind();
                    pivot.xProperty().set(0);
                }
                if (pivot.yProperty().isBound()) {
                    pivot.yProperty().unbind();
                    pivot.yProperty().set(0);
                }

                double moveX = basePivot.getX() + (basePos.getX() - mouseEvent.getSceneX());
                double moveY = basePivot.getY() + (basePos.getY() - mouseEvent.getSceneY());

                zoom.setX(moveX);
                zoom.setY(moveY);
            }
            if (mouseEvent.isSecondaryButtonDown())
            {
                double rotateByX = baseRotate.getX() + (basePos.getX() - mouseEvent.getSceneX());
                double rotateByY = baseRotate.getY() + (basePos.getY() - mouseEvent.getSceneY());
                rotateY.angleProperty().set(rotateByX);
                rotateX.angleProperty().set(rotateByY);
            }
        }));

        stage.addEventHandler(ScrollEvent.SCROLL, mouseEvent -> zoom.setZ(zoom.getZ() + mouseEvent.getDeltaY()*3));
    }*/

    public static void main(String[] args) {
        launch();
    }
}