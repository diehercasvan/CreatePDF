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

public class MainActivity extends AppCompatActivity {
    private static final String NAME_FILE_APP = "createpdf";
    private static final String FILE_GENERATED = "MisArchivos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            ;
            document.addAuthor("Diego Casallas Vanegas ");
            document.addCreator("KREATOR");
            document.addSubject("Thank you");
            document.addCreationDate();
            document.addTitle("Titule ");
            XMLWorkerHelper xmlWorkerHelper = XMLWorkerHelper.getInstance();

            String htmlToPDF = "<!DOCTYPE html>\n" +
                    "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                    "<head>\n" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                    "<title>Documento sin título</title>\n" +
                    "</head>\n" +
                    "\n" +
                    "<body>\n" +
                    "<h1>My  PDF create</h1>\n" +
                    "<p>Test content Daniel  </p>\n" +
                    "</body>\n" +
                    "</html>";

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
