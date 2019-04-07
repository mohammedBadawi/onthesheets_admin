package hospital.code.com.foesheets.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Edit_Sheet_Activity extends AppCompatActivity {
    EditText edited_name,edited_lesson,drive_link;
    Button edit;
    String name,lesson,id_teacher,id,drive,id_subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__sheet_);
        edited_name=(EditText)findViewById(R.id.et_edit_sheet_name);
        edited_lesson=(EditText)findViewById(R.id.et_edit_sheet_lesson);
        drive_link=(EditText)findViewById(R.id.et_edited_sheet_drive);
        edited_name.setText(getIntent().getStringExtra("name").toString());
        edited_lesson.setText(getIntent().getStringExtra("lesson").toString());
        drive_link.setText(getIntent().getStringExtra("drive").toString());
        edit=(Button)findViewById(R.id.bt_edit_sheet);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=edited_name.getText().toString();
                lesson=edited_lesson.getText().toString();
                drive=drive_link.getText().toString();
                id_teacher=getIntent().getStringExtra("id_teacher");
                id=getIntent().getStringExtra("id").toString();
                id_subject=getIntent().getStringExtra("id_subject").toString();
                Edit_teacher(id,id_teacher,name,lesson,drive,id_subject);
                Thread t=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            Intent i=new Intent(Edit_Sheet_Activity.this,Sheets_Activity.class);
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





    private void Edit_teacher(final String id, final String id_teacher, final String name,final String lesson,final String drive,final String id_subject) {
        String url="https://onthesheets.000webhostapp.com/sheets/update.php";
        StringRequest request=new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Edit_Sheet_Activity.this,"Updated successfully",Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Edit_Sheet_Activity.this,"connection error",Toast.LENGTH_LONG).show();


            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map input =new HashMap();
                input.put("id",id);
                input.put("id_teacher",id_teacher);
                input.put("name",name);
                input.put("lesson",lesson);
                input.put("drive",drive);
                input.put("id_subject",id_subject);
                return input;
            }
        };
        MySingleTon.getMinstance(Edit_Sheet_Activity.this).addRequestQueuex(request);
    }

}
