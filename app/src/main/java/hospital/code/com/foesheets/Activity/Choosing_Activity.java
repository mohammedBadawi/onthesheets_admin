package hospital.code.com.foesheets.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import hospital.code.com.foesheets.R;

public class Choosing_Activity extends AppCompatActivity {
    ImageView iv_notes,iv_sheets;
    TextView tv_notes,tv_sheets;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Changes 'back' button action
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            Intent intent=new Intent(Choosing_Activity.this,SubjectsActivity.class);
            startActivity(intent);

        }

        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosing_);
        iv_notes=(ImageView)findViewById(R.id.iv_choose_notes);
        iv_sheets=(ImageView)findViewById(R.id.iv_choose_sheets);
        iv_sheets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Choosing_Activity.this,Solving_Sheet_Activity.class);
                i.putExtra("id_subject",getIntent().getStringExtra("id_subject_to_choosing"));
                startActivity(i);
            }
        });
        iv_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Choosing_Activity.this,TeachersActivity.class);
                i.putExtra("id_subject",getIntent().getStringExtra("id_subject_to_choosing"));
                startActivity(i);
            }
        });
        tv_notes=(TextView)findViewById(R.id.tv_notes_img);
        tv_sheets=(TextView)findViewById(R.id.tv_sheets_img);
        tv_sheets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Choosing_Activity.this,Solving_Sheet_Activity.class);
                i.putExtra("id_subject",getIntent().getStringExtra("id_subject_to_choosing"));
                startActivity(i);
            }
        });
        tv_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Choosing_Activity.this,TeachersActivity.class);
                i.putExtra("id_subject",getIntent().getStringExtra("id_subject_to_choosing"));
                startActivity(i);
            }
        });

    }
}
