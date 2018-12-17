package solutions.thinkbiz.comicskin;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yuku.ambilwarna.AmbilWarnaDialog;

public class FormActivity extends AppCompatActivity {

    int currentColor;
     Spinner spinerGrd,spinnerPgQL,spinnerNews;
     EditText Seriestitle,issue,publsher,pubdate,grdngbox,sortby,art,coverart,spclrqst,emailadd;
     EditText PrmyCLR,ScryCLR,TextColorTemp;
     Spinner grade,pgqlty,news,Phclr,Shclr;
     Button ButtonSubmit;
     ImageButton PrmryClrBtn,ScnClrBtn,TextClr;

    int mYear, mMonth, mDay;

    String Actname,temp1;
    TextView textname;

    RadioGroup radioGroup;
    RadioButton radioButtonYs,radioButtonNo;
    LinearLayout Selctgrad;
    TextView gradText;

    Bitmap bitmap;
    ProgressDialog progressDialog;
    ImageButton chooseArt,chooseCvrArt;
    ImageView imageArt,imageCvrArt;
    int PICK_IMAGE_REQUEST = 111;
    int PICK_IMAGE_REQUEST1 = 112;


    String monthYearStr;
    SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
    SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");

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
        temp1=pref.getString("Text","");
        textname=(TextView)findViewById(R.id.textname);
        textname.setText(Actname);

        ImageButton imageButton= (ImageButton)view.findViewById(R.id.action_bar_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });


        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioButtonYs = (RadioButton) findViewById(R.id.rb1);
        radioButtonNo = (RadioButton) findViewById(R.id.rb2);
        Selctgrad=(LinearLayout)findViewById(R.id.spinnerGrd1);
        gradText=(TextView)findViewById(R.id.gradT);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton rb=(RadioButton)findViewById(checkedId);
                String result=rb.getText().toString();
                if (result.equalsIgnoreCase("Yes")){
                    gradText.setVisibility(View.VISIBLE);
                    Selctgrad.setVisibility(View.VISIBLE);
                }
                if (result.equalsIgnoreCase("No")){
                    gradText.setVisibility(View.GONE);
                    Selctgrad.setVisibility(View.GONE);
                }


               // Log.e("value",result);

            }
        });

        imageArt = (ImageView)findViewById(R.id.imageart);
        imageCvrArt = (ImageView)findViewById(R.id.imageCovrart);
        chooseArt = (ImageButton)findViewById(R.id.addart);
        chooseCvrArt = (ImageButton)findViewById(R.id.addCovrartt);
        ButtonSubmit=(Button)findViewById(R.id.buttonsbmt);
        chooseArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });

        chooseCvrArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST1);
            }
        });





        spinerGrd = (Spinner) findViewById(R.id.spinnerGrd);
        //spinerGrd.setFocusable(true);
       // spinerGrd.setFocusableInTouchMode(true);
        String[] users = new String[]{
                "Please Select Grade*",
                "10","9.8","9.6","9.4","9.2","9.0","8.5","8.0","7.5","7.0",
                "6.5","6.0","5.5","5.0","4.5","4.0","3.5","3.0","2.5","2.0",
                "1.5","1.0","0.5","NG","M","NM","VF+","VF","F","VG","G"
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
                "WHITE Pages","OFF WHITE TO WHITE Pages","OFF WHITE Pages","BRITTLE Pages"
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
                "News Stand or Direct Distribution*",
                "News Stand","Direct Distribution"
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

         currentColor  = ContextCompat.getColor(this, R.color.gray);



        Seriestitle=(EditText)findViewById(R.id.series);
        issue=(EditText)findViewById(R.id.issue);
        publsher=(EditText)findViewById(R.id.publsher);
        pubdate=(EditText)findViewById(R.id.publsherdate);
        //grdngbox=(EditText)findViewById(R.id.grdngbox);
        sortby=(EditText)findViewById(R.id.storyby);
       // art=(EditText)findViewById(R.id.art);
       // coverart=(EditText)findViewById(R.id.covrart);
        spclrqst=(EditText)findViewById(R.id.sclrqst);
        emailadd=(EditText)findViewById(R.id.editTextemail);

        PrmyCLR=(EditText)findViewById(R.id.spinnerHdrClrP);
        PrmryClrBtn=(ImageButton)findViewById(R.id.spinnerHdrClrPbtn);
        ScnClrBtn=(ImageButton)findViewById(R.id.spinnerPdrClrPbtn);
        ScryCLR=(EditText)findViewById(R.id.spinnerHdrClrS);
        TextColorTemp=(EditText)findViewById(R.id.textclr);
        TextClr=(ImageButton)findViewById(R.id.textclrbtn);

        PrmryClrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDialogPrmry(true);

            }
        });

        ScnClrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDialogSecndry(true);

            }
        });

        TextClr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDialogTextClr(true);

            }
        });

        pubdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // openDatePicker();
                MonthYearPickerDialog pickerDialog = new MonthYearPickerDialog();
                pickerDialog.setListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int i2) {
                        monthYearStr = year + "-" + (month + 1) + "-" + i2;
                        pubdate.setText(formatMonthYear(monthYearStr));
                    }
                });
                pickerDialog.show(getSupportFragmentManager(),"MonthYearPickerDialog");
            }

        });


        ButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = new ProgressDialog(FormActivity.this);
                progressDialog.setMessage("Uploading, please wait...");
                progressDialog.show();

                // encode
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                Log.e("image", String.valueOf(imageString));

                StringRequest request = new StringRequest(Request.Method.POST, "",
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String s) {
                                Log.e("responce", s);
                                progressDialog.dismiss();

                                try {
                                    JSONObject obj = new JSONObject(s);
                                    String success=obj.getString("s");
                                    String error=obj.getString("e");
                                    String msg=obj.getString("m");

                                    if(success.equalsIgnoreCase("1")){
                                        Toast.makeText(FormActivity.this, msg, Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(FormActivity.this, msg, Toast.LENGTH_LONG).show();
                                    }

                                }
                                catch (JSONException e){
                                    e.printStackTrace();
                                    Toast.makeText(FormActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }
                        },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(FormActivity.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("image", imageString);
                        return parameters;
                    }
                };

                RequestQueue rQueue = Volley.newRequestQueue(FormActivity.this);
                rQueue.add(request);
            }
        });


//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(isValidate())
//                {
//                    //SubmitData();
//                }
//
//            }
//        });
    }

    String formatMonthYear(String str) {
        Date date = null;
        try {
            date = input.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(date);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageArt.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (requestCode == PICK_IMAGE_REQUEST1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageCvrArt.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


//    private void openDatePicker() {
//
//        MonthYearPickerDialog pickerDialog = new MonthYearPickerDialog();
//        pickerDialog.setListener(new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int year, int month, int i2) {
//                monthYearStr = year + "-" + (month + 1) + "-" + i2;
//                pubdate.setText(formatMonthYear(monthYearStr));
//            }
//        });
//        pickerDialog.show(getSupportFragmentManager(), "MonthYearPickerDialog");
//    }
//
//    String formatMonthYear(String str) {
//        Date date = null;
//        try {
//            date = input.parse(str);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return sdf.format(date);
//    }

//        final Calendar c = Calendar.getInstance();
//        mYear = c.get(Calendar.YEAR);
//        mMonth = c.get(Calendar.MONTH);
//        mDay = c.get(Calendar.DAY_OF_MONTH);
//
//        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
//                new DatePickerDialog.OnDateSetListener() {
//
//
//                    @Override
//                    public void onDateSet(DatePicker view, int year,
//                                          int monthOfYear, int dayOfMonth) {
//
//                        //pubdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
//                        String stringOfDate = (monthOfYear + 1) + "/" + year;
//                        pubdate.setText(stringOfDate);
//
//                    }
//                }, mYear, mMonth, mDay);
//        datePickerDialog.show();
        //}



    private void openDialogPrmry(boolean supportsAlpha) {

        AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, currentColor, supportsAlpha, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                currentColor = color;
                //PrmyCLR.setBackgroundColor(color);
                String Selctedcolor=Integer.toHexString(currentColor);
                Selctedcolor=Selctedcolor.substring(2);
                String ColorCode="#"+Selctedcolor;
                PrmyCLR.setText(ColorCode);

               // PrmyCLR.setText("#"+Selctedcolor);
                Log.e("color", ColorCode);
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                Toast.makeText(getApplicationContext(), "Action canceled!", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }



    private void openDialogSecndry(boolean supportsAlpha) {
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, currentColor, supportsAlpha, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                currentColor = color;
               // ScryCLR.setBackgroundColor(color);
                String Selctedcolor=Integer.toHexString(currentColor);
                Selctedcolor=Selctedcolor.substring(2);
                String ColorCode="#"+Selctedcolor;
                ScryCLR.setText(ColorCode);

                Log.e("color",ColorCode);
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                Toast.makeText(getApplicationContext(), "Action canceled!", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    private void openDialogTextClr(boolean supportsAlpha) {
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, currentColor, supportsAlpha, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                currentColor = color;
                //TextColorTemp.setBackgroundColor(color);
                String Selctedcolor=Integer.toHexString(currentColor);
                Selctedcolor=Selctedcolor.substring(2);
                String ColorCode="#"+Selctedcolor;
                TextColorTemp.setText(ColorCode);

                Log.e("color",ColorCode);
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                Toast.makeText(getApplicationContext(), "Action canceled!", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }




//    private boolean isValidate()
//    {
//        final String mail = editTextemail.getText().toString().trim();
//        //int pos =spiner.getSelectedItemPosition();
//
//        if (editTextfnm.getText().toString().length() == 0) {
//            editTextfnm.setError("First name not entered");
//            editTextfnm.requestFocus();
//            return false;
//        }
//
//
//        if (TextUtils.isEmpty(mail)) {
//            editTextmail.setError("Please enter your email");
//            editTextmail.requestFocus();
//            return false;
//        }
//
//        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
//            editTextmail.setError("Enter a valid email");
//            editTextmail.requestFocus();
//            return false;
//        }
//
//        if (editTextstreet.getText().toString().length() == 0) {
//            editTextstreet.setError("Street not entered");
//            editTextstreet.requestFocus();
//            return false;
//        }
//
//        if (editTextcity.getText().toString().length() == 0) {
//            editTextcity.setError("City not entered");
//            editTextcity.requestFocus();
//            return false;
//        }
////        if (pos==0){
////            spiner.requestFocus();
////            Toast.makeText(SellersActivity.this, "Please Select State", Toast.LENGTH_LONG).show();
////            return false;
////        }
//        return true;
//    }

}
