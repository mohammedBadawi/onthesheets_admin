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

public class AddTeachers_Activity extends AppCompatActivity {
    EditText teacher_name;
    Button add_teacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teachers_);
        teacher_name=(EditText)findViewById(R.id.et_addedteachername);
        add_teacher=(Button)findViewById(R.id.bt_addteacher);
        add_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=teacher_name.getText().toString();
                String id_subject=getIntent().getStringExtra("id_sent").toString();
                insert_teacher(name,id_subject);
                Thread t=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            Intent i=new Intent(AddTeachers_Activity.this,TeachersActivity.class);
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

    private void insert_teacher(final String name, final String id_subject) {
        String url="https://onthesheets.000webhostapp.com/teachers/insert.php";
        StringRequest request=new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(AddTeachers_Activity.this,"A new Teacher was added successfully",Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddTeachers_Activity.this,"check your internet connection",Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map input=new HashMap();
                input.put("name",name);
                input.put("id_subject",id_subject);
                return input;
            }
        };
        MySingleTon.getMinstance(AddTeachers_Activity.this).addRequestQueuex(request);
    }
}
