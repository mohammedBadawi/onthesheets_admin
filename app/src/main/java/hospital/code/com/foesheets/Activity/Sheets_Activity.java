package hospital.code.com.foesheets.Activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hospital.code.com.foesheets.Adapters.Sheets_Adapter;
import hospital.code.com.foesheets.Models.Sheet;
import hospital.code.com.foesheets.Models.Subjects;
import hospital.code.com.foesheets.MySingleTon.MySingleTon;
import hospital.code.com.foesheets.R;

public class Sheets_Activity extends AppCompatActivity {
ArrayList<Sheet>arrayList;
ListView listView;
Sheets_Adapter adapter;
FloatingActionButton fb_add_sheets;
String id_subject;




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Changes 'back' button action
        try {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                int position = 0;
                Intent intent = new Intent(Sheets_Activity.this, TeachersActivity.class);
                intent.putExtra("id_subject", arrayList.get(position).getId_subject());
                startActivity(intent);

            }
        }catch (Exception e){finish();}
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheets_);
        arrayList=new ArrayList<Sheet>();
        listView=(ListView)findViewById(R.id.lv_sheets) ;
        fb_add_sheets=(FloatingActionButton)findViewById(R.id.fb_add_sheet) ;
        id_subject=getIntent().getStringExtra("id_subject");
        fb_add_sheets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Sheets_Activity.this,AddSheets_Activity.class);
                i.putExtra("id_teacher",getIntent().getStringExtra("id_teacher").toString());
                i.putExtra("id_subject",id_subject);
                startActivity(i);
            }
        });
        fill_list();

    }

    private void fill_list() {
        String url="https://onthesheets.000webhostapp.com/sheets/select.php";
        StringRequest request=new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                read(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                final Toast toast;
                toast=Toast.makeText(Sheets_Activity.this,"connection error",Toast.LENGTH_LONG);
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
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map map=new HashMap();
                map.put("id_wanted",getIntent().getStringExtra("id_teacher").toString());
                return map;
            }
        };
        MySingleTon.getMinstance(Sheets_Activity.this).addRequestQueuex(request);
    }

    private void read(String response) {
        try {
            JSONObject object=new JSONObject(response);
            JSONArray result=object.getJSONArray("result");
            String id;String name;String created_at;String id_teacher;String lesson;String drive;String id_subject;
            for(int i=0;i<result.length();i++){
                JSONObject sheet=result.getJSONObject(i);
                id=sheet.getString("id");
                name=sheet.getString("name");
                created_at=sheet.getString("created_at");
                id_teacher=sheet.getString("id_teacher");
                lesson=sheet.getString("lesson");
                drive=sheet.getString("drive");
                id_subject=sheet.getString("id_subject");
                Sheet sheet_element=new Sheet(id,name,created_at,id_teacher,lesson,drive,id_subject);
                arrayList.add(sheet_element);

            }
            adapter=new Sheets_Adapter(Sheets_Activity.this,R.layout.custom_sheets_list,arrayList);
            listView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
