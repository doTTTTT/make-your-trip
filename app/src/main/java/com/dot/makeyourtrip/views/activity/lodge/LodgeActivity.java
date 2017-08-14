package com.dot.makeyourtrip.views.activity.lodge;

import com.dot.makeyourtrip.R;
import com.dot.makeyourtrip.databinding.ActivityLodgeBinding;
import com.dot.makeyourtrip.utils.android.Activity;

public class LodgeActivity extends Activity<ActivityLodgeBinding> implements LodgeContract.View {
    private LodgeViewModel viewModel;

    @Override
    protected void initView(ActivityLodgeBinding binding) {
        if (getIntent() != null) {
            LodgeAdapter adapter = new LodgeAdapter(getIntent().getStringExtra("TRIP_ID"), getComponent(), this);
            viewModel = new LodgeViewModel(getComponent(), this, adapter);

            binding.setViewModel(viewModel);
            binding.lodgeList.setAdapter(adapter);
        }
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_lodge;
    }

    @Override
    public void setCheckIn(String checkIn) {
        binding.lodgeCheckin.setText(checkIn);
    }

    @Override
    public void setCheckOut(String checkOut) {
        binding.lodgeCheckout.setText(checkOut);
    }

    @Override
    public void finishActivity() {
        finish();
    }
}
