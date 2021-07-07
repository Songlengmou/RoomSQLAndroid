package com.anningtex.roomsqlandroid.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.anningtex.roomsqlandroid.R;
import com.anningtex.roomsqlandroid.bean.PhoneBean;
import com.anningtex.roomsqlandroid.db.PhoneDatabase;

import java.util.Date;

/**
 * @author Administrator
 */
public class EditDialog extends BaseDialog implements View.OnClickListener {
    private EditText mNameEdit, mPhoneEdit;
    private PhoneBean mPhoneBean;
    private OnRefreshDataListener mListener;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_edit_info;
    }

    @Override
    protected void initView(View view) {
        mNameEdit = view.findViewById(R.id.update_name_edit);
        mPhoneEdit = view.findViewById(R.id.update_phone_edit);
        view.findViewById(R.id.update_button).setOnClickListener(this);
        view.findViewById(R.id.delete_button).setOnClickListener(this);
    }

    @Override
    protected void loadData(Bundle bundle) {
        if (bundle != null) {
            mPhoneBean = bundle.getParcelable("PhoneBean");
            mNameEdit.setText(mPhoneBean.getName());
            mPhoneEdit.setText(mPhoneBean.getPhone());
        }
    }

    @Override
    public void onClick(View v) {
        String mName = mNameEdit.getText().toString();
        String mPhone = mPhoneEdit.getText().toString();
        switch (v.getId()) {
            case R.id.update_button:
                if (mName.isEmpty()) {
                    Toast.makeText(getActivity(), "姓名不能为空", Toast.LENGTH_SHORT).show();
                } else if (mPhone.isEmpty()) {
                    Toast.makeText(getActivity(), "手机号不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    updatePhone(mName, mPhone);
                }
                break;
            case R.id.delete_button:
                deletePhone();
                break;
            default:
                break;
        }
    }

    private void updatePhone(String name, String phone) {
        mPhoneBean.setName(name);
        mPhoneBean.setPhone(phone);
        mPhoneBean.setDate(new Date());
        PhoneDatabase.getDefault(getActivity().getApplicationContext()).getPhoneDao().update(mPhoneBean);
        dismiss();
        if (mListener != null) {
            mListener.onRefresh();
        }
    }

    private void deletePhone() {
        PhoneDatabase.getDefault(getActivity().getApplicationContext()).getPhoneDao().delete(mPhoneBean);
        dismiss();
        if (mListener != null) {
            mListener.onRefresh();
        }
    }

    public void setOnRefreshDataListener(OnRefreshDataListener listener) {
        this.mListener = listener;
    }

    public interface OnRefreshDataListener {
        void onRefresh();
    }
}
