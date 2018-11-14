package solutions.thinkbiz.comicskin;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

public class FormActivity extends AppCompatActivity {

    private Spinner spinerGrd,spinnerPgQL,spinnerNews,spinnerPCLR,spinnerSCLR;
    private EditText Seriestitle,issue,publsher,pubdate,grdngbox,sortby,art,coverart,spclrqst,emailadd;
    private Spinner grade,pgqlty,news,Phclr,Shclr;
    private Button mCreateButton;
    private File pdfFile;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;

    String Actname;
    TextView textname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.backbar);
        View view =getSupportActionBar().getCustomView();

        SharedPreferences pref = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        Actname=pref.getString("Actvname","");
        textname=(TextView)findViewById(R.id.textname);
        textname.setText(Actname);

        ImageButton imageButton= (ImageButton)view.findViewById(R.id.action_bar_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        spinerGrd = (Spinner) findViewById(R.id.spinnerGrd);
        //spinerGrd.setFocusable(true);
       // spinerGrd.setFocusableInTouchMode(true);
        String[] users = new String[]{
                "Please Select Grade*",
                "A+","A","B+","B","C+",
                "C","D","E","F","Fail"
        };

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.spinneritems, users
        ){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinneritems);
        spinerGrd.setAdapter(spinnerArrayAdapter);


        spinnerPgQL = (Spinner) findViewById(R.id.spinnerPgqlt);
        //spinnerPgQL.setFocusable(true);
        //spinnerPgQL.setFocusableInTouchMode(true);
        String[] users1 = new String[]{
                "Please Select Page Quality*",
                "Excellent","Very Good","Good","Poor",
                "Excellent","Very Good","Good","Poor"
        };

        ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(
                this, R.layout.spinneritems, users1
        ){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter1.setDropDownViewResource(R.layout.spinneritems);
        spinnerPgQL.setAdapter(spinnerArrayAdapter1);



        spinnerNews = (Spinner) findViewById(R.id.spinnerNewstnd);
        //spinnerPgQL.setFocusable(true);
        //spinnerPgQL.setFocusableInTouchMode(true);
        String[] users1News = new String[]{
                "Please Select Page Quality*",
                "Excellent","Very Good","Good","Poor",
                "Excellent","Very Good","Good","Poor"
        };

        ArrayAdapter<String> spinnerArrayAdapterNews = new ArrayAdapter<String>(
                this, R.layout.spinneritems, users1News
        ){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapterNews.setDropDownViewResource(R.layout.spinneritems);
        spinnerNews.setAdapter(spinnerArrayAdapterNews);



        spinnerPCLR = (Spinner) findViewById(R.id.spinnerHdrClrP);
        //spinnerPCLR.setFocusable(true);
       // spinnerPCLR.setFocusableInTouchMode(true);
        String[] users2 = new String[]{
                "Please Select Primary Heading Color*",
                "Red","Green","Blue","Black","Purple",
                "Red","Green","Blue","Black","Purple"
        };

        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(
                this, R.layout.spinneritems, users2
        ){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter2.setDropDownViewResource(R.layout.spinneritems);
        spinnerPCLR.setAdapter(spinnerArrayAdapter2);


        spinnerSCLR = (Spinner) findViewById(R.id.spinnerHdrClrS);
        //spinnerSCLR.setFocusable(true);
       // spinnerSCLR.setFocusableInTouchMode(true);
        String[] users3 = new String[]{
                "Please Select Secondary Heading Color*",
                "Red","Green","Blue","Black","Purple",
                "Red","Green","Blue","Black","Purple"
        };

        ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>(
                this, R.layout.spinneritems, users3
        ){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter3.setDropDownViewResource(R.layout.spinneritems);
        spinnerSCLR.setAdapter(spinnerArrayAdapter3);



        Seriestitle=(EditText)findViewById(R.id.series);
        issue=(EditText)findViewById(R.id.issue);
        publsher=(EditText)findViewById(R.id.publsher);
        pubdate=(EditText)findViewById(R.id.publsherdate);
        grdngbox=(EditText)findViewById(R.id.grdngbox);
        sortby=(EditText)findViewById(R.id.storyby);
        art=(EditText)findViewById(R.id.art);
        coverart=(EditText)findViewById(R.id.covrart);
        spclrqst=(EditText)findViewById(R.id.sclrqst);
        emailadd=(EditText)findViewById(R.id.editTextemail);

        mCreateButton=(Button)findViewById(R.id.buttonsbmt);
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    createPdfWrapper();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void createPdfWrapper() throws FileNotFoundException,DocumentException {

        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    showMessageOKCancel("You need to allow access to Storage",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        }else {
            createPdf();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    try {
                        createPdfWrapper();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Permission Denied
                    Toast.makeText(this, "WRITE_EXTERNAL Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void createPdf() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {

                docsFolder.mkdir();
                Log.d(String.valueOf(FormActivity.this), "Created a new directory for PDF");
            //Toast.makeText(getApplicationContext(), "Created...", Toast.LENGTH_LONG).show();

        }

        pdfFile = new File(docsFolder.getAbsolutePath(),"HelloWorld.pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        document.open();
        document.add(new Paragraph(Seriestitle.getText().toString()));
        document.add(new Paragraph(issue.getText().toString()));
        document.add(new Paragraph(publsher.getText().toString()));
        document.add(new Paragraph(pubdate.getText().toString()));
        document.add(new Paragraph(grdngbox.getText().toString()));
        document.add(new Paragraph(sortby.getText().toString()));
        document.add(new Paragraph(art.getText().toString()));
        document.add(new Paragraph(coverart.getText().toString()));
        document.add(new Paragraph(spclrqst.getText().toString()));
        document.add(new Paragraph(emailadd.getText().toString()));


        document.close();
        previewPdf();

    }

    private void previewPdf() {

        PackageManager packageManager = getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(pdfFile);
            intent.setDataAndType(uri, "application/pdf");
            startActivity(intent);
        }else{
            Toast.makeText(this,"Download a PDF Viewer to see the generated PDF",Toast.LENGTH_SHORT).show();
        }
    }
}
