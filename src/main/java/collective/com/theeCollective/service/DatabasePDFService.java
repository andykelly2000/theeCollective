package collective.com.theeCollective.service;

import collective.com.theeCollective.dto.ArticleDto;
import collective.com.theeCollective.model.Article;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

@Service
public class DatabasePDFService {
    public static ByteArrayInputStream articlePDFReport(List<ArticleDto> articles){
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try{
            PdfWriter.getInstance(document, out);
            document.open();

            Font fontHeader = FontFactory.getFont(FontFactory.TIMES_BOLD, 22);
            Paragraph para = new Paragraph("Articles Report", fontHeader);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(5);

            Stream.of("Article name", "author names", "category", "views", "UpdatedOn").forEach(headerTitle -> {
                PdfPCell header = new PdfPCell();
                Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD);
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setBorderWidth(2);
                header.setPhrase(new Phrase(headerTitle, headFont));
                table.addCell(header);
            });

            for (ArticleDto article : articles){
                PdfPCell idCell = new PdfPCell(new Phrase(article.getArticleId()));
                idCell.setPaddingLeft(4);
                idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(idCell);

                PdfPCell articleNameCell = new PdfPCell(new Phrase(article.getTitle()));
                articleNameCell.setPaddingLeft(4);
                articleNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                articleNameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(articleNameCell);

                PdfPCell authorCell = new PdfPCell(new Phrase(article.getAuthorName()));
                authorCell.setPaddingLeft(4);
                authorCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                authorCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(authorCell);

                PdfPCell categoryCell = new PdfPCell(new Phrase(article.getCategory()));
                categoryCell.setPaddingLeft(4);
                categoryCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                categoryCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(categoryCell);

                PdfPCell viewsCell = new PdfPCell(new Phrase(article.getViews()));
                viewsCell.setPaddingLeft(4);
                viewsCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                viewsCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(viewsCell);

                PdfPCell updatedOnCell = new PdfPCell(new Phrase(article.getUploadedon().toString()));
                updatedOnCell.setPaddingLeft(4);
                updatedOnCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                updatedOnCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(updatedOnCell);
            }

            document.add(table);
            document.close();
        } catch(DocumentException e){
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

}
