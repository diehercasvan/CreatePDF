package com.casallas.diego.createpdf;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

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

/**
 * Created by DIEGO CASALLAS on 20/05/2015.
 */
public class Screen_Email {

    private File file;
    private String sRoute;
    private String sNameFolder;
    private String sFilePath;
    private FileOutputStream fileOutputStream;
    private View view;
    private String sHtml;
    private Bitmap bitmap;
    private Activity activity;
    private String NAME_FILE;
    private static final String NAME_FILE_APP = "calculator";


/*
    public Screen_Email(RelativeLayout frameLayout) {

        this.view = frameLayout;
        this.sRoute = General.ROUTE;
        this.sNameFolder = General.NAME_FOLDER_MAIL;
        this.activity = General.ACTIVITY;
    }
    public Screen_Email(String sHtml) {

        this.sHtml = sHtml;
        this.sRoute = General.ROUTE;
        this.sNameFolder = General.NAME_FOLDER_MAIL;
        this.activity = General.ACTIVITY;
    }

    public void createScreen() {

        view.setDrawingCacheEnabled(true);
        bitmap = view.getDrawingCache();
        saveBitmap(bitmap);
        view.destroyDrawingCache();
    }


    public void saveBitmap(Bitmap bitmap) {

        NAME_FILE="screenshot";
        sFilePath = Environment.getExternalStorageDirectory()+ File.separator + sNameFolder + "/"+NAME_FILE+".png";
        file = new File(sFilePath);


        try {
            fileOutputStream = new FileOutputStream(file);


            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            sendMail(sFilePath,0);
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }
    public void createPDFOnClick() {
        NAME_FILE = "Data_pdf_calculator.pdf";
        Document document = new Document(PageSize.LETTER);
        sFilePath = Environment.getExternalStorageDirectory()+ File.separator + sNameFolder + "/"+NAME_FILE+".png";
        file = new File(sFilePath);

        String SD_Card = Environment.getExternalStorageDirectory().toString();
        File filePDF = new File(SD_Card + File.separator + NAME_FILE_APP);
        if (!filePDF.exists()) {
            filePDF.mkdir();
        }
        File pdfSubDir = new File(filePDF.getPath() + File.separator + sNameFolder);
        if (!pdfSubDir.exists()) {
            pdfSubDir.mkdir();
        }
        String NameFull = Environment.getExternalStorageDirectory() + File.separator + NAME_FILE_APP + File.separator + sNameFolder + File.separator + NAME_FILE;

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
            xmlWorkerHelper.parseXHtml(pdfWriter, document, new StringReader(sHtml));
            document.close();
            General.printToast(R.string.messages5);
            sendMail(NameFull, 1);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendMail(String path, int type) {
        try {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{General.MAIL_CONTENT[0]});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, General.MAIL_CONTENT[1]);
            emailIntent.putExtra(Intent.EXTRA_TEXT, General.MAIL_CONTENT[2]);
            if(type==0){
            emailIntent.setType("image/png");
            }
            else{
                emailIntent.setType("application/pdf");
            }
            Uri myUri = Uri.parse("file://" + path);
            emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
            activity.startActivity(Intent.createChooser(emailIntent,activity.getString(R.string.share) ));
        } catch (Exception e) {

            General.printToast(R.string.messages4);

        }

    }

    public void diary(String path) {
        Intent emailIntent = new Intent(Intent.ACTION_EDIT);
        emailIntent.setType("vnd.android.cursor.item/event");
       /* emailIntent.putExtra(Intent.EXTRA_EMAIL,
                new String[] { DTO_General.MAIL_CONTENT[0] });
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                DTO_General.MAIL_CONTENT[1]);
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                DTO_General.MAIL_CONTENT[2]);

        emailIntent.setType("image/png");
        Uri myUri = Uri.parse("file://" + path);
        emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
        activity.startActivity(Intent.createChooser(emailIntent, "Send mail..."));*/
    }

}

