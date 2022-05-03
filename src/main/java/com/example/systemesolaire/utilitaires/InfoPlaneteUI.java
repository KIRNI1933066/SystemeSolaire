package com.example.systemesolaire.utilitaires;

import com.example.systemesolaire.Main;
import com.example.systemesolaire.corpscelestes.Planete;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.text.NumberFormat;

    public class InfoPlaneteUI extends Group {

        private final Text TEXT_DISTANCE_SOLEIL = new Text();
        private final NumberFormat FORMAT_NOMBRE = NumberFormat.getInstance();

        public InfoPlaneteUI(Planete planete) {
            FORMAT_NOMBRE.setMaximumFractionDigits(2);
            FORMAT_NOMBRE.setMinimumFractionDigits(0);

            BorderPane fond = new BorderPane();
            fond.setPrefSize(300, 600);
            fond.setMinSize(50,100);
            fond.setMaxSize(300, 550);
            fond.getStyleClass().add("fond");

            VBox informationVBox = new VBox();
            try {
                Image image = new Image(Constantes.IMAGES_PATH + planete.nom + ".png");
                ImageView imageView = new ImageView(image);
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(150);
                informationVBox.getChildren().add(imageView);

            } catch (Exception exception)
            {
                System.out.println("Image introuvable! Pour la planete : " + planete.nom);
            }

            informationVBox.setPrefSize(200, 600);

            Text nomPlanete = new Text(planete.nom);
            nomPlanete.getStyleClass().addAll("textInfo", "textInfoGras");

            Text rayonPlanete = new Text("Rayon : " + FORMAT_NOMBRE.format(planete.getRayon()) + " km");
            rayonPlanete.getStyleClass().add("textInfo");

            Text massePlanete = new Text("Masse : \n" + FORMAT_NOMBRE.format(planete.masse) + " x 10^24 kg");
            massePlanete.getStyleClass().add("textInfo");

            TEXT_DISTANCE_SOLEIL.getStyleClass().add("textInfo");

            Text tempsPourOrbitPlanete = new Text("Temps pour une orbite :\n" + Constantes.InfoPlanetes.valueOf(planete.nom.toUpperCase()).tempsOrbite);
            tempsPourOrbitPlanete.getStyleClass().add("textInfo");

            informationVBox.getChildren().addAll(nomPlanete, rayonPlanete, massePlanete, TEXT_DISTANCE_SOLEIL, tempsPourOrbitPlanete);
            informationVBox.setAlignment(Pos.TOP_CENTER);
            informationVBox.setPadding(new Insets(20));
            informationVBox.setSpacing(10);

            fond.setCenter(informationVBox);
            fond.setMouseTransparent(true);
            super.getChildren().add(fond);
        }

        public void setDistanceSoleil(Double distance)
        {
            TEXT_DISTANCE_SOLEIL.setText("Distance du soleil :\n" + FORMAT_NOMBRE.format(distance * Main.ECHELLE) + " km");
        }
    }
