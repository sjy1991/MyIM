package com.example.he.myim.module.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.he.myim.R;
import com.example.he.myim.base.BaseFragment;
import com.example.he.myim.widget.ContactLayout;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends BaseFragment implements ContactContract.ContactView{
    private ContactLayout mContactLayout;
    private List<String> mContacts;

    public static ContactFragment newInstance(){
        return new ContactFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmentz
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        mContactLayout = (ContactLayout) view.findViewById(R.id.contact_layout);
        return view;
    }

    @Override
    public void onInitContract(List<String> list) {
        mContacts = list;

    }

    @Override
    public void onUpdateContract(boolean isSuccess, String msg) {

    }


}
