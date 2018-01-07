package com.example.minghan.ems;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by MingHan on 6/5/2016.
 */
public class View_Holder extends RecyclerView.ViewHolder {

    public CardView cv;
    public TextView title;
    public TextView email;
    public TextView emp_no;
    public ImageView imageView;
    public Button btn_update;
    public Button btn_delete;

    View_Holder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardView);
        title = (TextView) itemView.findViewById(R.id.title);
        email = (TextView) itemView.findViewById(R.id.row_email);
        emp_no = (TextView) itemView.findViewById(R.id.row_emp_no);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
        btn_delete = (Button) itemView.findViewById(R.id.btn_delete);
        btn_update = (Button) itemView.findViewById(R.id.btn_update);
    }
}
