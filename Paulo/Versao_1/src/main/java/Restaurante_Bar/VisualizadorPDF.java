package Restaurante_Bar;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

public class VisualizadorPDF extends JFrame {

    public VisualizadorPDF(String caminhoPdf) {
        setTitle("Pré-visualização do Recibo");
        setSize(700, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        try (PDDocument document = Loader.loadPDF(new File(caminhoPdf))) {
            PDFRenderer renderer = new PDFRenderer(document);

            BufferedImage imagem = renderer.renderImageWithDPI(0, 150);
            Image imagemEscalada = imagem.getScaledInstance(650, -1, Image.SCALE_SMOOTH);

            JLabel label = new JLabel(new ImageIcon(imagemEscalada));
            label.setHorizontalAlignment(SwingConstants.CENTER);

            JScrollPane scroll = new JScrollPane(label);
            add(scroll, BorderLayout.CENTER);

        } catch (Exception e) {
            JLabel erro = new JLabel("Não foi possível carregar a pré-visualização do PDF.");
            erro.setHorizontalAlignment(SwingConstants.CENTER);
            add(erro, BorderLayout.CENTER);
            e.printStackTrace();
        }
    }
}