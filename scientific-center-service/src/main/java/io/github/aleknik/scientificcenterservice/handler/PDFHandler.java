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
            return textStripper.getText(doc);
        } catch (IOException ignored) {
        }
        return null;
    }
}
