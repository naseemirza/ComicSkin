package solutions.thinkbiz.comicskin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class AllTemplatesActivity extends AppCompatActivity {

    String Actname ;
    TextView textname;

    CardView card1,card2,card3,card4,card5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_templates);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.onlybackbar);
        View view =getSupportActionBar().getCustomView();

        ImageButton imageButton= (ImageButton)view.findViewById(R.id.action_bar_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        Actname=pref.getString("Actvname","");
        textname=(TextView)findViewById(R.id.textname);
        textname.setText(Actname);

        card1=(CardView)findViewById(R.id.card1);
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text="Template 1";
                Intent intent=new Intent(AllTemplatesActivity.this,FormActivity.class);

                intent.putExtra("Text",text);
                startActivity(intent);
            }
        });

        card2=(CardView)findViewById(R.id.card2);
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text="Template 2";
                Intent intent=new Intent(AllTemplatesActivity.this,FormActivity.class);

                intent.putExtra("Text",text);
                startActivity(intent);
            }
        });

        card3=(CardView)findViewById(R.id.card3);
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text="Template 3";
                Intent intent=new Intent(AllTemplatesActivity.this,FormActivity.class);

                intent.putExtra("Text",text);
                startActivity(intent);
            }
        });

        card4=(CardView)findViewById(R.id.card4);
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text="Template 4";
                Intent intent=new Intent(AllTemplatesActivity.this,FormActivity.class);

                intent.putExtra("Text",text);
                startActivity(intent);
            }
        });

        card5=(CardView)findViewById(R.id.card5);
        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text="Template 5";
                Intent intent=new Intent(AllTemplatesActivity.this,FormActivity.class);

                intent.putExtra("Text",text);
                startActivity(intent);
            }
        });
    }
}
