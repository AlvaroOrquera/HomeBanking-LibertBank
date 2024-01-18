package com.mindhub.homebanking.services.Implement;

import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.DTO.TransactionDTO;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.services.PdfService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Service
public class PdfServiceImpl implements PdfService {

    @Override
    public ByteArrayInputStream generatePdf(List<Transaction> transactions, AccountDTO accountDTO) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        //a partir de aca ya tengo el documento en blanco
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        //seteamos el tipo de fuente que va a tener nuestro pdf y al lado el tamaño de la misma
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 16);
        //                         ^^^^^^^^^ seteo de fuente            ||||||||      ^^^^^^ seteo del tamaño de fuente
        contentStream.setLeading(16.5f);
        // seteo interlineado

        contentStream.beginText();
        //esto sirve para iniciar el texto y empezar a escribir

        contentStream.newLineAtOffset(220, 550);
        // esto seria para indicarle la posicion del texto


        contentStream.showText("Account Information: " + accountDTO.getNumber());
        contentStream.newLine();
        contentStream.showText("Type: " + accountDTO.getAccountType());
        contentStream.newLine();
        contentStream.showText("---------------------");
        contentStream.newLine();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        for (Transaction transaction : transactions) {
            contentStream.showText("Transaction ID: " + transaction.getId());
            contentStream.newLine();
            contentStream.showText("Transaction type: " + transaction.getType());
            contentStream.newLine();
            contentStream.showText("Amount: " + transaction.getAmount());
            contentStream.newLine();
            contentStream.showText("Description: " + transaction.getDescription());
            contentStream.newLine();
            contentStream.showText("Date: " + transaction.getDateTime().format(dateTimeFormatter));
            contentStream.newLine();
            contentStream.showText("Previous Balance: " + transaction.getPreviousBalance());
            contentStream.newLine();
            contentStream.showText("Current Balance: " + transaction.getCurrentBalance());
            contentStream.newLine();
            contentStream.showText("---------------------");
            contentStream.newLine();
        }
        contentStream.endText();
        contentStream.close();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        document.save(byteArrayOutputStream);
        document.close();
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }
}

