package com.dot.makeyourtrip.views.fragment.setting;

import android.content.Intent;

import com.dot.makeyourtrip.R;
import com.dot.makeyourtrip.databinding.FragmentSettingBinding;
import com.dot.makeyourtrip.utils.android.BaseFragment;
import com.dot.makeyourtrip.views.activity.login.LoginActivity;

public class SettingFragment extends BaseFragment<FragmentSettingBinding> implements SettingContract.View {
    @Override
    public int getLayoutID() {
        return R.layout.fragment_setting;
    }

    @Override
    public void initView(FragmentSettingBinding binding) {
        binding.setViewModel(new SettingViewModel(getComponent(), this));
    }

    @Override
    public void logOut() {
        startActivity(new Intent(getContext(), LoginActivity.class));
    }
}
