package com.example.minghan.ems;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by MingHan on 6/5/2016.
 */
public class Recycler_View_Adapter extends RecyclerView.Adapter<View_Holder> {

    List<User> list;
    private Context context;

    public Recycler_View_Adapter(List<User> list,Context context) {
        this.context = context;
        this.list = list;
    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(View_Holder holder, final int position) {

        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        holder.title.setText(list.get(position).name);
        holder.email.setText(list.get(position).email);
        holder.emp_no.setText(list.get(position).emp_no);
        holder.imageView.setImageResource(list.get(position).imageId);

        holder.btn_delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v) {
                if (AppStatus.getInstance(v.getContext()).isOnline()) {
                    final ProgressDialog progressDialog = new ProgressDialog(v.getContext());
                    progressDialog.setMessage("Deleting from server...");
                    progressDialog.setCancelable(false);

                    progressDialog.show();

                    final String emp_no = list.get(position).emp_no;
                    RequestQueue queue = Volley.newRequestQueue(v.getContext());
                    final String url = "minghan1219.site88.net/DeleteUser.php?id=" + emp_no;
                    // prepare the Request
                    JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    progressDialog.dismiss();
                                    try {
                                        boolean success = response.getBoolean("success");
                                        // display response
                                        if (success) {
                                            Context context = v.getContext();
                                            CharSequence text = "Record had been deleted successfully.";
                                            int duration = Toast.LENGTH_LONG;
                                            Toast toast = Toast.makeText(context, text, duration);
                                            toast.show();
                                        } else {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                            builder.setMessage("Delete failed1")
                                                    .setNegativeButton("Retry", null)
                                                    .create()
                                                    .show();
                                        }
                                    } catch (JSONException e) {
                                        progressDialog.dismiss();
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    progressDialog.dismiss();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                    builder.setMessage("Delete failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }
                            }
                    );

                    // add it to the RequestQueue
                    queue.add(getRequest);
                }
                else{
                    Context context = v.getContext();
                    CharSequence text = "Network connection is needed";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });

        holder.btn_update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), UpdateUser.class);
                intent.putExtra("name", list.get(position).name);
                intent.putExtra("username", list.get(position).username);
                intent.putExtra("email", list.get(position).email);
                intent.putExtra("emp_no", list.get(position).emp_no);
                intent.putExtra("ic", list.get(position).ic);
                intent.putExtra("date", list.get(position).date);
                intent.putExtra("gender", list.get(position).gender);

                v.getContext().startActivity(intent);
            }
        });

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //animate(holder);
    }

    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, User data) {
        list.add(position, data);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(User data) {
        int position = list.indexOf(data);
        list.remove(position);
        notifyItemRemoved(position);
    }
//
//    public void animate(RecyclerView.ViewHolder viewHolder) {
//        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.bounce_interpolator);
//        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
//    }
}