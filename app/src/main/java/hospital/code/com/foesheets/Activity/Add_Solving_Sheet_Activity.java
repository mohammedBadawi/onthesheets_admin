package hospital.code.com.foesheets.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import hospital.code.com.foesheets.MySingleTon.MySingleTon;
import hospital.code.com.foesheets.R;

public class Add_Solving_Sheet_Activity extends AppCompatActivity {
    EditText solving_sheet_name;
    EditText lesson_name;
    EditText drive;
    Button add_solving_sheet;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__solving__sheet_);
        solving_sheet_name=(EditText)findViewById(R.id.et_add_solving_sheet_name);
        lesson_name=(EditText)findViewById(R.id.et_add_solving_sheet_lesson);
        drive=(EditText)findViewById(R.id.et_add_solving_sheet_drive);
        add_solving_sheet=(Button)findViewById(R.id.bt_add_solving_sheet);
        add_solving_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=solving_sheet_name.getText().toString();
                String lesson=lesson_name.getText().toString();
                String drive_link=drive.getText().toString();
                 String id_subject=getIntent().getStringExtra("id_sent").toString();
                insert_solving_sheet(name,lesson,id_subject,drive_link);
                Thread t=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2500);
                            Intent i=new Intent(Add_Solving_Sheet_Activity.this,Solving_Sheet_Activity.class);
                            i.putExtra("id_subject",getIntent().getStringExtra("id_sent").toString());
                            startActivity(i);
                        }catch (Exception e){

                        }
                    }
                });
                t.start();

            }
        });





    }

    private void insert_solving_sheet(final String name, final String lesson, final String id_subject, final String drive_link) {
        String url="https://onthesheets.000webhostapp.com/solving_sheets/insert.php";
        StringRequest request=new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Add_Solving_Sheet_Activity.this,"A new Sheet was added successfully",Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Add_Solving_Sheet_Activity.this,"check your internet connection",Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map input=new HashMap();
                input.put("name",name);
                input.put("lesson",lesson);
                input.put("id_subject",id_subject);
                input.put("drive",drive_link);
                return input;
            }
        };
        MySingleTon.getMinstance(Add_Solving_Sheet_Activity.this).addRequestQueuex(request);
    }
}
