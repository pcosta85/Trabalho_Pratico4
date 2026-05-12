package Restaurante_Bar;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class ReciboVenda {

    private static final String PASTA_RECIBOS = "recibos";

    public static String gerar(String usuario, String formaPagamento, Double recebido, Double troco, List<Object[]> carrinho) {
        try {
            File pasta = new File(PASTA_RECIBOS);
            if (!pasta.exists()) {
                pasta.mkdirs();
            }

            String nomeArquivo = "Recibo_" + System.currentTimeMillis() + ".pdf";
            String caminho = PASTA_RECIBOS + File.separator + nomeArquivo;

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(caminho));
            document.open();

            Font titulo = new Font(Font.HELVETICA, 16, Font.BOLD);
            Font normal = new Font(Font.HELVETICA, 11);
            Font negrito = new Font(Font.HELVETICA, 11, Font.BOLD);

            DecimalFormat df = new DecimalFormat("#,##0.00");

            Paragraph cab = new Paragraph("RESTAURANTE / BAR CENTRAL", titulo);
            cab.setAlignment(Element.ALIGN_CENTER);
            document.add(cab);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Utilizador: " + usuario, normal));
            document.add(new Paragraph("Forma de Pagamento: " + formaPagamento, normal));
            document.add(new Paragraph("--------------------------------------------------", normal));

            PdfPTable tabela = new PdfPTable(4);
            tabela.setWidthPercentage(100);
            tabela.setWidths(new float[]{45f, 10f, 20f, 20f});

            tabela.addCell(new Phrase("Produto", negrito));
            tabela.addCell(new Phrase("Qtd", negrito));
            tabela.addCell(new Phrase("Preço", negrito));
            tabela.addCell(new Phrase("Total", negrito));

            double totalGeral = 0.0;

            for (Object[] item : carrinho) {
                String produto = item[0].toString();
                int qtd = (Integer) item[1];
                double preco = (Double) item[2];
                double total = (Double) item[3];

                totalGeral += total;

                tabela.addCell(new Phrase(produto, normal));
                tabela.addCell(new Phrase(String.valueOf(qtd), normal));
                tabela.addCell(new Phrase(df.format(preco), normal));
                tabela.addCell(new Phrase(df.format(total), normal));
            }

            document.add(tabela);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("TOTAL: " + df.format(totalGeral), negrito));

            if (recebido != null) {
                document.add(new Paragraph("Recebido: " + df.format(recebido), normal));
            }

            if (troco != null) {
                document.add(new Paragraph("Troco: " + df.format(troco), normal));
            }

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Obrigado pela preferência!", normal));

            document.close();

            System.out.println("Recibo gerado em: " + caminho);
            return caminho;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}