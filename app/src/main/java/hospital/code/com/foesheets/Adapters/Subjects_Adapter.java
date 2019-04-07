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
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import hospital.code.com.foesheets.Activity.Edit_Subject_Activity;
import hospital.code.com.foesheets.Models.Subjects;
import hospital.code.com.foesheets.MySingleTon.MySingleTon;
import hospital.code.com.foesheets.R;

import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.centerCrop;


public class Subjects_Adapter extends ArrayAdapter<Subjects> {
    ArrayList<Subjects> item;
    Context context;
    int resource;

    public Subjects_Adapter(Context context, int resource ,ArrayList<Subjects> item) {
        super(context, resource , item);
        this.context = context ;
        this.resource = resource ;
        this.item = item ;
    }


    public View getView(final int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.custom_subjects_list, null, true);

        }
        ImageView edit_subject=(ImageView)view.findViewById(R.id.iv_edit_subject) ;
        ImageView delete_subject=(ImageView)view.findViewById(R.id.iv_delete_subject) ;

        delete_subject.setOnClickListener(new View.OnClickListener() {
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
               // delete(item.get(position).getId().toString());
                //remove(item.get(position));
            }
        });
        edit_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, Edit_Subject_Activity.class);
                i.putExtra("id",item.get(position).getId().toString());
                i.putExtra("name",item.get(position).getName().toString());
                i.putExtra("url",item.get(position).getLogo().toString());
                v.getContext().startActivity(i);
            }
        });
        ImageView logo=(ImageView)view.findViewById(R.id.iv_subject_logo);
        TextView name=(TextView) view.findViewById(R.id.tv_subject_name);
        name.setText(item.get(position).getName());
        String image_url=item.get(position).getLogo().toString();
        Glide.with(context).load(image_url).placeholder(R.mipmap.placeholder).into(logo);



    return view;}


    private void delete(final String id) {
        String url="https://onthesheets.000webhostapp.com/subjects/delete.php";
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