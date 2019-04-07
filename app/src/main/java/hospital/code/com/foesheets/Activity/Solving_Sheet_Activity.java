package hospital.code.com.foesheets.Activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hospital.code.com.foesheets.Adapters.Solving_Sheets_Adapter;
import hospital.code.com.foesheets.Adapters.Teacher_Adapter;
import hospital.code.com.foesheets.Models.Solving_Sheet;
import hospital.code.com.foesheets.Models.Teacher;
import hospital.code.com.foesheets.MySingleTon.MySingleTon;
import hospital.code.com.foesheets.R;

public class Solving_Sheet_Activity extends AppCompatActivity {
    ArrayList<Solving_Sheet> arrayList;
    ListView listView;
    Solving_Sheets_Adapter adapter;
    FloatingActionButton fb_add_solving_sheet;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Changes 'back' button action
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            Intent intent=new Intent(Solving_Sheet_Activity.this,Choosing_Activity.class);
            intent.putExtra("id_subject_to_choosing",getIntent().getStringExtra("id_subject").toString());
            startActivity(intent);

        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solving_sheets_);

        arrayList = new ArrayList<Solving_Sheet>();
        listView=(ListView)findViewById(R.id.lv_solving_sheet);
        fb_add_solving_sheet=(FloatingActionButton)findViewById(R.id.fb_add_solving_sheet);
        fb_add_solving_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Solving_Sheet_Activity.this,Add_Solving_Sheet_Activity.class);
                intent.putExtra("id_sent",getIntent().getStringExtra("id_subject").toString());
                startActivity(intent);
            }
        });


        fill_list();

    }

    private void fill_list() {


        String url = "https://onthesheets.000webhostapp.com/solving_sheets/select.php";
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                read(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                final Toast toast;
                toast=Toast.makeText(Solving_Sheet_Activity.this,"connection error",Toast.LENGTH_LONG);
                toast.show();
                Thread t=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            toast.cancel();
                            fill_list();


                        }catch (Exception e){

                        }
                    }
                });
                t.start();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String id_wanted=getIntent().getStringExtra("id_subject").toString();
                Map map = new HashMap();
                map.put("id_wanted",id_wanted);
                return map;


            }
        };
        MySingleTon.getMinstance(Solving_Sheet_Activity.this).addRequestQueuex(stringRequest);
    }

    private void read(String response) {
        try {
            JSONObject jsonObject=new JSONObject(response);
            JSONArray jsonArray=jsonObject.getJSONArray("result");
            String id;String name;String id_subject;String created_at;String lesson;String drive;

            for(int i=0;i<jsonArray.length();i++){
                JSONObject solving_sheet=jsonArray.getJSONObject(i);
                id=solving_sheet.getString("id");
                name=solving_sheet.getString("name");
                id_subject=solving_sheet.getString("id_subject");
                created_at=solving_sheet.getString("created_at");
                lesson=solving_sheet.getString("lesson");
                drive=solving_sheet.getString("drive");
                Solving_Sheet member=new Solving_Sheet(id,name,created_at,id_subject,lesson,drive);
                arrayList.add(member);

            }
            adapter=new Solving_Sheets_Adapter(Solving_Sheet_Activity.this,R.layout.custom_solving_sheets_list,arrayList);
            listView.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}