package hospital.code.com.foesheets.Adapters;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hospital.code.com.foesheets.Activity.Edit_Teacher_Activity;
import hospital.code.com.foesheets.Models.Subjects;
import hospital.code.com.foesheets.Models.Teacher;
import hospital.code.com.foesheets.MySingleTon.MySingleTon;
import hospital.code.com.foesheets.R;


public class Teacher_Adapter extends ArrayAdapter<Teacher> {
    ArrayList<Teacher> item;
    Context context;
    int resource;

    public Teacher_Adapter(Context context, int resource ,ArrayList<Teacher> item) {
        super(context, resource , item);
        this.context = context ;
        this.resource = resource ;
        this.item = item ;
    }


    public View getView(final int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.custom_teacher_list, null, true);

        }

        TextView name=(TextView) view.findViewById(R.id.teacher_name);
        name.setText(item.get(position).getName().toString());
        ImageView delete_teacher=(ImageView)view.findViewById(R.id.iv_delete_teacher);
        ImageView edit_teacher=(ImageView)view.findViewById(R.id.iv_edit_teacher);
        edit_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, Edit_Teacher_Activity.class);
                intent.putExtra("id_to_edit",item.get(position).getId().toString());
                intent.putExtra("id_subject",item.get(position).getId_subject());
                intent.putExtra("old_teacher_name",item.get(position).getName());
                v.getContext().startActivity(intent);

            }
        });
        delete_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are You Sure You Want to delete "+item.get(position).getName().toString()+" ?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(item.get(position).getId().toString());
                        remove(item.get(position));
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.show();

            }
        });



        return view;}

    private void delete(final String id) {
        String url="https://onthesheets.000webhostapp.com/teachers/delete.php";
        StringRequest stringRequest=new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(context,response,Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"connection error",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map input =new HashMap();
                input.put("id_wanted",id);
                return input;
            }
        };
        MySingleTon.getMinstance(context).addRequestQueuex(stringRequest);
    }
}