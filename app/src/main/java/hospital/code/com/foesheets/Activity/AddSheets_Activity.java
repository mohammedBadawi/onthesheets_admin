package hospital.code.com.foesheets.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import hospital.code.com.foesheets.MySingleTon.MySingleTon;
import hospital.code.com.foesheets.R;

public class AddSheets_Activity extends AppCompatActivity {
    EditText sheet_name,lesson_name,drive_link;
    Button Add_sheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sheets_);
        sheet_name=(EditText)findViewById(R.id.et_sheet_name);
        lesson_name=(EditText)findViewById(R.id.et_lesson);
        drive_link=(EditText)findViewById(R.id.et_add_sheet_drive);
        Add_sheet=(Button)findViewById(R.id.bt_add_sheet);
        Add_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=sheet_name.getText().toString();
                String lesson=lesson_name.getText().toString();
                String drive=drive_link.getText().toString();
                final String id_teacher=getIntent().getStringExtra("id_teacher").toString();
                final String id_subject=getIntent().getStringExtra("id_subject").toString();
                insert_sheet(name,lesson,id_teacher,drive,id_subject);
                Thread t=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            Intent i=new Intent(AddSheets_Activity.this,Sheets_Activity.class);
                            i.putExtra("id_teacher",id_teacher);
                            startActivity(i);

                        }catch (Exception e){

                        }
                    }
                });
                t.start();


            }
        });
    }

    private void insert_sheet(final String name,final String lesson,final String id_teacher,final String drive,final String id_subject) {
        String url="https://onthesheets.000webhostapp.com/sheets/insert.php";
        StringRequest request=new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(AddSheets_Activity.this,"A new Note was added successfully",Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddSheets_Activity.this,"check your internet connection",Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map input=new HashMap();
                input.put("name",name);
                input.put("lesson",lesson);
                input.put("id_teacher",id_teacher);
                input.put("drive",drive);
                input.put("id_subject",id_subject);
                return input;
            }
        };
        MySingleTon.getMinstance(AddSheets_Activity.this).addRequestQueuex(request);
    }
    }

