package com.example.he.myim.module.home.plugin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.he.myim.R;
import com.example.he.myim.base.BaseFragment;
import com.example.he.myim.module.home.MainActivity;
import com.example.he.myim.module.login.LoginActivity;
import com.example.he.myim.utils.ToastUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class PluginFragment extends BaseFragment implements PluginContract.PluginView, View.OnClickListener{
    private Button btn_logout;
    private PluginContract.PluginPresenter mPluginPresenter;

    public static PluginFragment newInstance() {
        return new PluginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mPluginPresenter = new PluginPresenterImpl(this);
        View view = inflater.inflate(R.layout.fragment_plugin, container, false);
        btn_logout = (Button) view.findViewById(R.id.btn_login_out);
        btn_logout.setOnClickListener(this);

        return view;
    }



    @Override
    public void onLogout(String user, boolean isSuccess, String msg) {
        ((MainActivity)getActivity()).hideProgressDialog();
        if (isSuccess) {
            MainActivity activity = (MainActivity) getActivity();
            activity.startActivity(LoginActivity.class, true);
            activity = null;
        } else {
            ToastUtils.showToast(getContext(), "退出失败:" + msg);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        btn_logout.setOnClickListener(null);
        btn_logout = null;
    }

    @Override
    public void onClick(View v) {
        ((MainActivity)getActivity()).showProgressDialog("正在紧张的退出...");
        mPluginPresenter.lougou();
    }
}
