package com.casallas.diego.createpdf;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String NAME_FILE_APP = "createpdf";
    private static final String FILE_GENERATED = "MisArchivos";
    private static String sContentHtml;
    private static String sContentHtmlCSS;
    private  static  String sContentHtmlSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sContentHtml="<!DOCTYPE html><html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /><title>Calculator PDF</title>";
        sContentHtmlCSS="<style type=\"text/css\">html, body {text-align: center;}#container{width: auto;height: auto;margin: auto;background: transparent !important;text-align: center;padding: 10px;color:#333;}#container h1 {margin: 0;padding: 0;color:white;}#header {width: auto;height:50px;box-shadow: 0px 0px 10px #8c0000;background: #8c0000;margin: auto;}.infoRight {width: 340px;height:50px;background:#CCC;float: right;border: 5px solid #FFF;font-weight:bold;}.infoLeft {width: 340px;height: 50px;background:#CCC;float: left;border: 5px solid #FFF;}.separator {width: auto;height: 30px;}#observations {width: auto;height: auto;background:#FFF;float: left;border: 3px solid #CCC;}#observations p{text-align:left;padding:5px;}#observations h4{text-align:left;padding:5px;}</style>";


    }

    public void createPDFOnClick(View view) {
        Document document = new Document(PageSize.LETTER);

        String NAME_FILE = "MiArchivoPDF.pdf";
        String SD_Card = Environment.getExternalStorageDirectory().toString();
        File filePDF = new File(SD_Card + File.separator + NAME_FILE_APP);
        if (!filePDF.exists()) {
            filePDF.mkdir();
        }
        File pdfSubDir = new File(filePDF.getPath() + File.separator + FILE_GENERATED);
        if (!pdfSubDir.exists()) {
            pdfSubDir.mkdir();
        }
        String NameFull = Environment.getExternalStorageDirectory() + File.separator + NAME_FILE_APP + File.separator + FILE_GENERATED + File.separator + NAME_FILE;

        File outPutFile = new File(NameFull);
        if (outPutFile.exists()) {
            outPutFile.delete();
        }
        try {
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(NameFull));
            //Create  document
            document.open();

            document.addAuthor("Diego Casallas Vanegas ");
            document.addCreator("KREATOR");
            document.addSubject("Thank you");
            document.addCreationDate();
            document.addTitle("Titule ");
            XMLWorkerHelper xmlWorkerHelper = XMLWorkerHelper.getInstance();
            ArrayList<String>listData=new ArrayList<>();
            listData.add("20");
            listData.add("30");
            listData.add("40");
            listData.add("50");
            sContentHtmlSecond="</head>\n" +
                    "\n" +
                    "<body>\n" +
                    "<div  id=\"container\">\n" +
                    "  <div id=\"header\">\n" +
                    "    <h1>Title Calculator</h1>\n" +
                    "  </div>\n" +
                    "  <div id=\"containerGeneral\">\n" +
                    "  <div class=\"separator\"></div>\n" +
                    "  <div class=\"infoRight\">\n" +
                    "    <p>"+listData.get(0)+"</p>\n" +
                    "  </div>\n" +
                    "  <div class=\"infoLeft\">\n" +
                    "    <p>Riesgo ótimo según edad y sexo :</p>\n" +
                    "  </div>\n" +
                    "  <div class=\"separator\"></div>\n" +
                    "  <div class=\"infoRight\">\n" +
                    "    <p>"+listData.get(1)+"</p>\n" +
                    "  </div>\n" +
                    "  <div class=\"infoLeft\">\n" +
                    "    <p>Riesgo ótimo según edad y sexo :</p>\n" +
                    "  </div>\n" +
                    "  <div class=\"separator\"></div>\n" +
                    "  <div class=\"infoRight\">\n" +
                    "    <p>"+listData.get(2)+"</p>\n" +
                    "  </div>\n" +
                    "  <div class=\"infoLeft\">\n" +
                    "    <p>Riesgo ótimo según edad y sexo :</p>\n" +
                    "  </div>\n" +
                    "  <div class=\"separator\"></div>\n" +
                    "  <div class=\"infoRight\">\n" +
                    "    <p>"+listData.get(3)+"</p>\n" +
                    "  </div>\n" +
                    "  <div class=\"infoLeft\" >\n" +
                    "    <p>Riesgo ótimo según edad y sexo :</p>\n" +
                    "  </div>\n" +
                    "  </div>\n" +
                    "  <div class=\"separator\"></div>\n" +
                    " <div id=\"observations\">\n" +
                    " \t<h4>Observaciones</h4>\n" +
                    "    <p> Riesgo ótimo según edad y sexo :Riesgo ótimo según edad y sexo :Riesgo ótimo según edad y sexo : Observaciones Riesgo ótimo según edad y sexo :Riesgo ótimo según edad y sexo :Riesgo ótimo según edad y sexo :</p>\n" +
                    "  </div>\n" +
                    "\n" +
                    "</div>\n" +
                    "</body>\n" +
                    "</html>\n";
            String htmlToPDF = sContentHtml+sContentHtmlCSS+sContentHtmlSecond;

            xmlWorkerHelper.parseXHtml(pdfWriter, document, new StringReader(htmlToPDF));
            document.close();
            Toast.makeText(this, "PDF loading...", Toast.LENGTH_LONG).show();
            viewPDF(NameFull, this);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void viewPDF(String sArchive, Context context) {

        Toast.makeText(this, "PDF loading... archive", Toast.LENGTH_LONG).show();

        File file = new File(sArchive);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {

            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No  app", Toast.LENGTH_LONG).show();
        }

    }
}
