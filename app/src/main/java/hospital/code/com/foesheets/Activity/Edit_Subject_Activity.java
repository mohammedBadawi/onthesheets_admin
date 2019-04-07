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

public class Edit_Subject_Activity extends AppCompatActivity {
    EditText edited_name,edited_logo;
    Button edit;
    String name,logo,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__subject_);

        edited_name=(EditText)findViewById(R.id.et_editsubjectname);
        edited_logo=(EditText)findViewById(R.id.et_editsubjectlogo);
        edited_name.setText(getIntent().getStringExtra("name"));
        edited_logo.setText(getIntent().getStringExtra("url"));
        edit=(Button)findViewById(R.id.bt_editsubject);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=edited_name.getText().toString();
                logo=edited_logo.getText().toString();
                id=getIntent().getStringExtra("id").toString();
                Edit_subject(id,name,logo);
                Thread t=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            Intent i=new Intent(Edit_Subject_Activity.this,SubjectsActivity.class);
                            startActivity(i);

                        }catch (Exception e){

                        }
                    }
                });
                t.start();
            }
        });

    }

    private void Edit_subject(final String id, final String name,final String logo) {
        String url="https://onthesheets.000webhostapp.com/subjects/update.php";
        StringRequest request=new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Edit_Subject_Activity.this,"Updated successfully",Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Edit_Subject_Activity.this,"connection error",Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map input =new HashMap();
                input.put("id",id);
                input.put("name",name);
                input.put("logo",logo);
                return input;
            }
        };
        MySingleTon.getMinstance(Edit_Subject_Activity.this).addRequestQueuex(request);
    }

}

