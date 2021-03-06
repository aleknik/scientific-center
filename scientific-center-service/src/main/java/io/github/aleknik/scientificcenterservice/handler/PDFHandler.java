package io.github.aleknik.scientificcenterservice.handler;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PDFHandler implements DocumentHandler {

    @Override
    public String getText(byte[] data) {
        try {
            final PDDocument doc = PDDocument.load(data);

            PDFTextStripper textStripper = new PDFTextStripper();
            final String text = textStripper.getText(doc);
            doc.close();
            return text;
        } catch (IOException ignored) {
        }
        return null;
    }
}
