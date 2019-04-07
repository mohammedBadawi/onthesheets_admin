package hospital.code.com.foesheets.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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

import hospital.code.com.foesheets.Activity.Edit_Sheet_Activity;
import hospital.code.com.foesheets.Models.Sheet;
import hospital.code.com.foesheets.Models.Subjects;
import hospital.code.com.foesheets.MySingleTon.MySingleTon;
import hospital.code.com.foesheets.R;

public class Sheets_Adapter extends ArrayAdapter<Sheet> {
    ArrayList<Sheet> item;
    Context context;
    int resource;

    public Sheets_Adapter(Context context, int resource ,ArrayList<Sheet> item) {
        super(context, resource , item);
        this.context = context ;
        this.resource = resource ;
        this.item = item ;
    }


    public View getView(final int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.custom_sheets_list, null, true);

        }

        TextView name=(TextView) view.findViewById(R.id.tv_sheet_name);
        TextView lesson=(TextView) view.findViewById(R.id.tv_lesson);
        TextView created_at=(TextView) view.findViewById(R.id.tv_created_at);
        ImageView edit_sheet=(ImageView)view.findViewById(R.id.iv_edit_sheet);
        edit_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, Edit_Sheet_Activity.class);
                i.putExtra("id",item.get(position).getId().toString());
                i.putExtra("id_teacher",item.get(position).getId_teacher().toString());
                i.putExtra("id_subject",item.get(position).getId_subject().toString());
                i.putExtra("name",item.get(position).getName().toString());
                i.putExtra("lesson",item.get(position).getLesson().toString());
                i.putExtra("drive",item.get(position).getDrive().toString());
                v.getContext().startActivity(i);

            }
        });
        name.setText(item.get(position).getName());
        lesson.setText("("+item.get(position).getLesson()+")");
        created_at.setText(item.get(position).getCreated_at());
        ImageView delete_sheet=(ImageView)view.findViewById(R.id.iv_delete_sheet);
        ImageView drive=(ImageView)view.findViewById(R.id.iv_sheet_drive);
        drive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Uri uri = Uri.parse(item.get(position).getDrive()); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    v.getContext().startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(getContext(),"Link is not available yet",Toast.LENGTH_LONG).show();
                }
            }
        });
        delete_sheet.setOnClickListener(new View.OnClickListener() {
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
        String url="https://onthesheets.000webhostapp.com/sheets/delete.php";
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