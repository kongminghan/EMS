//package com.example.minghan.ems;
//
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Toast;
//
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.List;
//
///**
// * Created by MingHan on 11/5/2016.
// */
//public class DeleteAdapter extends RecyclerView.Adapter<View_Holder> {
//
//    List<User> list;
//    private Context context;
//
//    public DeleteAdapter(List<User> list,Context context) {
//        this.context = context;
//        this.list = list;
//    }
//
//    @Override
//    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
//        //Inflate the layout, initialize the View Holder
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
//        View_Holder holder = new View_Holder(v);
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(View_Holder holder, final int position) {
//
//        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
//        holder.title.setText(list.get(position).name);
//        holder.email.setText(list.get(position).email);
//        holder.emp_no.setText(list.get(position).emp_no);
//        holder.imageView.setImageResource(list.get(position).imageId);
//
//        holder.cv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View v) {
//                Intent intent = new Intent(v.getContext(), UpdateUser.class);
//                final String emp_no = list.get(position).emp_no;
//                v.getContext().startActivity(intent);
//
//                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
//                alert.setTitle("Delete user");
//                alert.setMessage("Are you sure want to delete this user?");
//                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String DELETE_URL = "minghan1219.site88.net/Delete.php?id="+emp_no;
//
//                        JsonObjectRequest delete_request = new JsonObjectRequest(DELETE_URL,
//                                null, new Response.Listener<JSONObject>() {
//
//                            @Override
//                            public void onResponse(JSONObject response) {
//
//                                try {
//                                    int success = response.getInt("success");
//
//                                    if (success == 1) {
//                                        Toast.makeText(v.getContext(),
//                                                "Deleted Successfully",
//                                                Toast.LENGTH_SHORT).show();
//                                    } else {
//                                        Toast.makeText(v.getContext(),
//                                                "failed to delete", Toast.LENGTH_SHORT)
//                                                .show();
//                                    }
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        }, new Response.ErrorListener() {
//
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//
//                            }
//                        });
//
//                        // Adding request to request queue
//                        DeleteAdapter.getInstance().addToReqQueue(delete_request);
//
//                        dialog.dismiss();
//                    }
//                });
//            }
//        });
//
//        //animate(holder);
//    }
//
//    @Override
//    public int getItemCount() {
//        //returns the number of elements the RecyclerView will display
//        return list.size();
//    }
//
//    @Override
//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//    }
//
//    // Insert a new item to the RecyclerView on a predefined position
//    public void insert(int position, User data) {
//        list.add(position, data);
//        notifyItemInserted(position);
//    }
//
//    // Remove a RecyclerView item containing a specified Data object
//    public void remove(User data) {
//        int position = list.indexOf(data);
//        list.remove(position);
//        notifyItemRemoved(position);
//    }
////
////    public void animate(RecyclerView.ViewHolder viewHolder) {
////        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.bounce_interpolator);
////        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
////    }
//}
