package Modelo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class Transicion {

    private AnchorPane root = new AnchorPane();
    private List<ImageView> slides = new ArrayList<>();

    public Transicion() throws IOException {

        Path directorio = Paths.get("src/imagenes/imagenesPrincipal");

        Stream<Path> datos = Files.list(directorio);

        Iterator<Path> it = datos.iterator();
        while (it.hasNext()) {
            Path fichero = it.next();

            Image image = new Image(fichero.toUri().toString());

            ImageView imageview = new ImageView(image);
            imageview.setFitHeight(800);
            imageview.setFitWidth(1300);

            slides.add(imageview);

        }
    }

    public AnchorPane getRoot() {
        return root;
    }

    public void setRoot(AnchorPane root) {
        this.root = root;
    }
    
    

//         The method I am running in my class
    public void start() {

        SequentialTransition slideshow = new SequentialTransition();

        for (ImageView slide : slides) {

            SequentialTransition sequentialTransition = new SequentialTransition();

            
            FadeTransition fadeIn = getFadeTransition(slide, 0.0, 1.0, 1500);
            PauseTransition stayOn = new PauseTransition(Duration.millis(2500));
            FadeTransition fadeOut = getFadeTransition(slide, 1.0, 0.0, 0);

            sequentialTransition.getChildren().addAll(fadeIn, stayOn, fadeOut);
            slide.setOpacity(0);
            
            this.root.getChildren().add(slide);
            slideshow.getChildren().add(sequentialTransition);

        }

        slideshow.setCycleCount(SequentialTransition.INDEFINITE);
        slideshow.play();
    }

    private FadeTransition getFadeTransition(ImageView imageView, double fromValue, double toValue, double durationInMilliseconds) {

        FadeTransition ft = new FadeTransition(Duration.millis(durationInMilliseconds), imageView);
        ft.setFromValue(fromValue);
        ft.setToValue(toValue);

        return ft;

    }

}
