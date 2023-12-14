package com.example.demo.service;

import com.example.demo.model.PdfEcriture;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import technology.tabula.*;
import technology.tabula.Table;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;

@Service
public class PdfExtraction implements IpdfExtraction{
    @Override
    public void pdfDataTabula(MultipartFile file) throws Exception{
        InputStream in = file.getInputStream();
        try (PDDocument document = PDDocument.load(in)) {
            SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
            PageIterator pi = new ObjectExtractor(document).extract();
            while (pi.hasNext()) {
                // iterate over the pages of the document
                Page page = pi.next();
                List<Table> table = sea.extract(page);
                // iterate over the tables of the page
                for(Table tables: table) {
                    List<List<RectangularTextContainer>> rows = tables.getRows();
                    // iterate over the rows of the table
                    for (List<RectangularTextContainer> cells : rows) {
                        // print all column-cells of the row plus linefeed
                        for (RectangularTextContainer content : cells) {
                            // Note: Cell.getText() uses \r to concat text chunks
                            String text = content.getText().replace("\r", " ");
                            System.out.print(text + "|");
                        }
                        System.out.println();
                    }
                }
            }
        }
    }

    @Override
    public void pdfToExcel(MultipartFile file) throws Exception {
        if(file.isEmpty()){
            throw new  RuntimeException("Vous devez choisir un fichier pdf ");
        }
        List<PdfEcriture> pdfEcritureList=new ArrayList<>();
        XSSFWorkbook xssfWorkbook=new XSSFWorkbook();
        try {

            // Charger le fichier PDF
            PDDocument document = PDDocument.load(file.getInputStream());

            // Initialiser le stripper PDF
            PDFTextStripper pdfTextStripper = new PDFTextStripper();

            // Extraire le texte du PDF
            String pdfText = pdfTextStripper.getText(document);

            // Fermer le document PDF
            document.close();





            // Diviser le texte en lignes
            String[] lines = pdfText.split("\\r?\\n");
            List<String> listLines = Arrays.asList(lines);
            List<String> linesFilted=new ArrayList<>();
            List<List<String>> listOfListesWords = new ArrayList<>();
            listLines.stream().forEach(line -> {
                if(!line.trim().isEmpty() && line.length()>22 && line.contains(" ")){
                    String lineCheck= checkLine(line);
                    if(!lineCheck.equals("")){
                        linesFilted.add(line);
                    }
                }
            });

            linesFilted.stream().forEach(line -> {
                String[] parties = line.split("\\s+");
                listOfListesWords.add(Arrays.asList(parties));

                // Afficher chaque partie

            });
            listOfListesWords.stream().forEach(data -> {
                PdfEcriture pdfEcriture = new PdfEcriture();
                String date = data.get(0).matches("^\\d{2}/\\d{2}/\\d{4}$") ?data.get(0) :data.get(0)+" "+data.get(1) ;
                String sold =data.get(data.size() - 1);
                String dateValue = data.get(data.size() - 2);


                // Récupérer la chaîne résultante
                String line = String.join(" ",data);
                String libelle = line.replace(date," ");
                 libelle = libelle.replace(sold," ");
                 libelle = libelle.replace(dateValue," ");
                pdfEcriture.setDate(date);
                pdfEcriture.setDateValue(dateValue);

                    if(sold.startsWith("-")){
                        pdfEcriture.setDebit(sold);
                    }else{
                        pdfEcriture.setCredit(sold);
                    }



                pdfEcriture.setLibelle(libelle);
                pdfEcritureList.add(pdfEcriture);
            });
             createExcel(pdfEcritureList);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public String checkLine(String line){
        String[] words =line.split(" ");
        String lineFiltred="";
        String patternDate = "\\d{2}/\\d{2}/\\d{4}";

        if( words[0].matches("-?\\d+") && words[1].matches("-?\\d+")|| words[0].matches(patternDate)){
            lineFiltred= line;
        }
        return lineFiltred;
    }
    public CellStyle coloreLineHeader(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        return style;
    }
    public void createExcel(List<PdfEcriture> pdfEcritureList) throws IOException {
        int  colNum = 0;
        // Créer un nouveau classeur Excel
        Workbook workbook = new XSSFWorkbook();
        // header excel
        // Créer une feuille de calcul Excel
        Sheet sheet = workbook.createSheet("Données extraites");
        CellStyle style = coloreLineHeader(workbook);

        String[] entetes ={"Date","Libéllé","Date value","Débit","Crédit"};

        // Créer la première ligne pour les en-têtes de colonne
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < entetes.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(entetes[i]);
            cell.setCellStyle(style);
        }

        // Boucler à travers les lignes et les colonnes pour remplir le classeur Excel
        for (int i = 0; i < pdfEcritureList.size(); i++) {
            PdfEcriture pdfEcriture=pdfEcritureList.get(i);


            Row row = sheet.createRow(i+1);
            //Cell cellDate = row.createCell(i);
            row.createCell(0).setCellValue(pdfEcriture.getDate());
            row.createCell(1).setCellValue(pdfEcriture.getLibelle());
            row.createCell(2).setCellValue(pdfEcriture.getDateValue());
            row.createCell(3).setCellValue(pdfEcriture.getDebit() != null ? pdfEcriture.getDebit():"");
            row.createCell(4).setCellValue(pdfEcriture.getCredit() != null ? pdfEcriture.getCredit():"");


        }

        File file=new File(this.getClass().getClassLoader().getResource("fichier_excel.xlsx").getPath());
        if(file.exists()){
            file.delete();
        }

        // Sauvegarder le classeur Excel
        try (FileOutputStream fileOut = new FileOutputStream("src/main/resources/fichier_excel.xlsx")) {
            workbook.write(fileOut);
        }

        // Fermer le classeur Excel
        workbook.close();

        System.out.println("Extraction réussie vers Excel.");

    }



}
