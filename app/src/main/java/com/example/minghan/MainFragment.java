package com.example.minghan.ems;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        final TextView headUsername = (TextView) getView().findViewById (R.id.headUsername);
//        final TextView headEmail = (TextView) getView().findViewById(R.id.headEmail);
//        Bundle bundle = getArguments();
//
//        String name = bundle.getString("name");
//        String username = bundle.getString("username");
//        String age = bundle.getString("age");
//        String email = bundle.getString("email");
//        String password = bundle.getString("password");
//        String userType = bundle.getString("userType");
//
//        headUsername.setText(username);
//        headEmail.setText(email);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

}
