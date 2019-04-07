package hospital.code.com.foesheets.Activity;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import hospital.code.com.foesheets.Adapters.Subjects_Adapter;
import hospital.code.com.foesheets.Models.Subjects;
import hospital.code.com.foesheets.Models.Teacher;
import hospital.code.com.foesheets.MySingleTon.MySingleTon;
import hospital.code.com.foesheets.R;

public class SubjectsActivity extends AppCompatActivity {
    ArrayList<Subjects> arrayList;
    ListView listView;
    Subjects_Adapter adapter;
    FloatingActionButton add_button;
    private long backpressedtime;
    private Toast t;

    @Override
    public void onBackPressed() {
        if(backpressedtime+2000>System.currentTimeMillis()){
          t.cancel();
          Intent intent=new Intent(Intent.ACTION_MAIN);
          intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
          intent.addCategory(Intent.CATEGORY_HOME);
          startActivity(intent);
          finish();
          System.exit(0);
           // super.onBackPressed();
            //return;
        }else{
            t= Toast.makeText(SubjectsActivity.this,"Press again to exit",Toast.LENGTH_SHORT);
            t.show();

        }

        backpressedtime=System.currentTimeMillis();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subjects_list);
    arrayList=new ArrayList<Subjects>();
    listView=(ListView)findViewById(R.id.lv_subjects);
    add_button=(FloatingActionButton)findViewById(R.id.fb_add_subject);
    add_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i =new Intent(SubjectsActivity.this,AddSubject.class);
            startActivity(i);
        }
    });
    filllist();
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          // Intent i =new Intent(SubjectsActivity.this,TeachersActivity.class);
          //  i.putExtra("id_subject",arrayList.get(position).getId());
            Intent intent=new Intent(SubjectsActivity.this,Choosing_Activity.class);
            intent.putExtra("id_subject_to_choosing",arrayList.get(position).getId());
            startActivity(intent);
        }
    });
    }






    private void filllist() {
        String url="https://onthesheets.000webhostapp.com/subjects/select.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                read(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                final Toast toast;
                toast=Toast.makeText(SubjectsActivity.this,"connection error",Toast.LENGTH_LONG);
                toast.show();
                Thread t=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            toast.cancel();
                            filllist();


                        }catch (Exception e){

                        }
                    }
                });
                t.start();


            }
        });
        MySingleTon.getMinstance(SubjectsActivity.this).addRequestQueuex(stringRequest);
    }

    private void read(String response) {
        try {
            JSONObject jsonObject=new JSONObject(response);
            JSONArray result=jsonObject.getJSONArray("result");
            String id;String name;String logo;
            for(int i=0;i<result.length();i++){
                JSONObject subject=result.getJSONObject(i);
                id=subject.getString("id");
                name=subject.getString("name");
                logo=subject.getString("logo");
                Subjects subject_element=new Subjects(id,name,logo);
                arrayList.add(subject_element);
            }
            adapter=new Subjects_Adapter(SubjectsActivity.this,R.layout.custom_subjects_list,arrayList);
            listView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
