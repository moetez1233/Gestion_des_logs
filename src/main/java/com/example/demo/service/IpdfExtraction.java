package com.example.demo.service;

import com.example.demo.model.PdfEcriture;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IpdfExtraction {
    void pdfDataTabula(MultipartFile file) throws Exception;
    void pdfToExcel(MultipartFile file) throws Exception;

}
