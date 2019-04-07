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

public class AddSubject extends AppCompatActivity {
    EditText subject_name,logo_url;
    Button Add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);
        subject_name=(EditText)findViewById(R.id.et_addedsubjectname);
        logo_url=(EditText)findViewById(R.id.et_addedsubjectlogo);
        Add=(Button)findViewById(R.id.bt_addsubject);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=subject_name.getText().toString();
                String logo=logo_url.getText().toString();
                add_subject(name,logo);
                Thread thread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            Intent intent=new Intent(AddSubject.this,SubjectsActivity.class);
                            startActivity(intent);
                        } catch (InterruptedException e) {
                            e.printStackTrace(); }
                    }
                });
                thread.start();

            }
        });

    }

    private void add_subject(final String name, final String logo) {
        String url="https://onthesheets.000webhostapp.com/subjects/insert.php";
        StringRequest stringRequest=new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(AddSubject.this,"A new subject was added successfully",Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddSubject.this,"connection error",Toast.LENGTH_LONG).show();
 }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map subject =new HashMap();
                subject.put("name",name);
                subject.put("logo",logo);
                return subject;
            }
        };
        MySingleTon.getMinstance(this).addRequestQueuex(stringRequest);
    }
}
