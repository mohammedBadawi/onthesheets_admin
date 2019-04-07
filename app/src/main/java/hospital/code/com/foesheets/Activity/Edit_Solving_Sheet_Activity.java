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

public class Edit_Solving_Sheet_Activity extends AppCompatActivity {
    EditText edited_name,edited_lesson,edited_drive;
    Button edit;
    String name,lesson,id_subject,id,drive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__solving__sheet_);
        edited_name=(EditText)findViewById(R.id.et_edit_solving_sheet_name);
        edited_lesson=(EditText)findViewById(R.id.et_edit_solving_sheet_lesson);
        edited_drive=(EditText)findViewById(R.id.et_edit_solving_sheet_drive) ;
        edited_name.setText(getIntent().getStringExtra("name").toString());
        edited_lesson.setText(getIntent().getStringExtra("lesson").toString());
        edited_drive.setText(getIntent().getStringExtra("drive").toString());
        edit=(Button)findViewById(R.id.bt_edit_solving_sheet);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=edited_name.getText().toString();
                lesson=edited_lesson.getText().toString();
                id_subject=getIntent().getStringExtra("id_subject");
                id=getIntent().getStringExtra("id").toString();
                drive=edited_drive.getText().toString();
                Edit_teacher(id,id_subject,name,lesson,drive);
                Thread t=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2500);
                            Intent i=new Intent(Edit_Solving_Sheet_Activity.this,Solving_Sheet_Activity.class);
                            i.putExtra("id_subject",id_subject);
                            startActivity(i);
                        }catch (Exception e){

                        }
                    }
                });
                t.start();

            }
        });

    }




    private void Edit_teacher(final String id, final String id_subject, final String name,final String lesson,final String drive) {
        String url="https://onthesheets.000webhostapp.com/solving_sheets/update.php";
        StringRequest request=new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Edit_Solving_Sheet_Activity.this,"Updated successfully",Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Edit_Solving_Sheet_Activity.this,"connection error",Toast.LENGTH_LONG).show();


            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map input =new HashMap();
                input.put("id",id);
                input.put("id_subject",id_subject);
                input.put("name",name);
                input.put("lesson",lesson);
                input.put("drive",drive);
                return input;
            }
        };
        MySingleTon.getMinstance(Edit_Solving_Sheet_Activity.this).addRequestQueuex(request);
    }
}
