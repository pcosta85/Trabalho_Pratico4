package Restaurante_Bar;

import java.awt.image.BufferedImage;
import java.io.File;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class VisualizadorPDFFX {

    public void mostrar(String caminhoPdf) {
        Stage stage = new Stage();
        stage.setTitle("Pré-visualização do Recibo");

        BorderPane root = new BorderPane();

        try (PDDocument document = Loader.loadPDF(new File(caminhoPdf))) {
            PDFRenderer renderer = new PDFRenderer(document);

            BufferedImage bufferedImage = renderer.renderImageWithDPI(0, 150);

            ImageView imageView = new ImageView(SwingFXUtils.toFXImage(bufferedImage, null));
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(650);

            ScrollPane scrollPane = new ScrollPane(imageView);
            scrollPane.setFitToWidth(true);

            root.setCenter(scrollPane);

        } catch (Exception e) {
            root.setCenter(new Label("Não foi possível carregar a pré-visualização do PDF."));
            e.printStackTrace();
        }

        Scene scene = new Scene(root, 700, 900);
        stage.setScene(scene);
        stage.show();
    }
}