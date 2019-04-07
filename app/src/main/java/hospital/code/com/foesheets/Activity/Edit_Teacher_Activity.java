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

public class Edit_Teacher_Activity extends AppCompatActivity {
        EditText teacher_name;
        String id,id_subject,name;
        Button edit_teacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__teacher_);
        teacher_name=(EditText)findViewById(R.id.et_editteachername);
         id= getIntent().getStringExtra("id_to_edit");
         id_subject= getIntent().getStringExtra("id_subject");
         edit_teacher=(Button)findViewById(R.id.bt_editteacher);
         teacher_name.setText(getIntent().getStringExtra("old_teacher_name"));
         edit_teacher.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 name=teacher_name.getText().toString();
                 Edit_teacher(id,id_subject,name);
                 Thread t=new Thread(new Runnable() {
                     @Override
                     public void run() {
                         try {
                             Thread.sleep(2000);
                             Intent i=new Intent(Edit_Teacher_Activity.this,TeachersActivity.class);
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

    private void Edit_teacher(final String id, final String id_subject, final String name) {
        String url="https://onthesheets.000webhostapp.com/teachers/update.php";
        StringRequest request=new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Edit_Teacher_Activity.this,"Updated successfully",Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Edit_Teacher_Activity.this,"connection error",Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map input =new HashMap();
                input.put("id",id);
                input.put("id_subject",id_subject);
                input.put("name",name);
                return input;
            }
        };
        MySingleTon.getMinstance(Edit_Teacher_Activity.this).addRequestQueuex(request);
    }
}
