package com.example.demo.controller;

import com.example.demo.model.PdfEcriture;
import com.example.demo.service.IpdfExtraction;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping
public class simplTest {
    @Autowired
    private IpdfExtraction ipdfExtraction;

    @GetMapping
    public ResponseEntity<String> getMessage(){
        return new ResponseEntity<>("hello Axeane team ", HttpStatus.OK);
    }

   // github code exemple
    @GetMapping("/pdfGitCode")
    public void pdfData(MultipartFile file) throws Exception{
        PDFTableStripper.pdfData(file);
    }
    @GetMapping("/pdfTabula")
    public void pdfDataTabula(MultipartFile file) throws Exception{
        ipdfExtraction.pdfDataTabula(file);
    }
    @GetMapping("/pdfToExcel")
    public void getPdfTable(HttpServletResponse response,MultipartFile file) throws Exception{
       ipdfExtraction.pdfToExcel(file);
     //   ipdfExtraction.writeAnyExcelStreamToHttpServletResponse(response, xssfWorkbook);
    }



}
