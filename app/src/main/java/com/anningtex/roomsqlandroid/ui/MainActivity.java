package com.anningtex.roomsqlandroid.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.anningtex.roomsqlandroid.R;
import com.anningtex.roomsqlandroid.adapter.PhoneListAdapter;
import com.anningtex.roomsqlandroid.bean.PhoneBean;
import com.anningtex.roomsqlandroid.db.PhoneDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 * desc:使用Room数据库在Android下的使用，而不是AndroidX
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mNameEdit, mPhoneEdit;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mNameEdit = findViewById(R.id.insert_name_edit);
        mPhoneEdit = findViewById(R.id.insert_phone_edit);
        mRecyclerView = findViewById(R.id.data_list_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        findViewById(R.id.query_button).setOnClickListener(this);
        findViewById(R.id.insert_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String mName = mNameEdit.getText().toString();
        String mPhone = mPhoneEdit.getText().toString();
        switch (v.getId()) {
            case R.id.query_button:
                queryPhone();
                break;
            case R.id.insert_button:
                if (mName.isEmpty()) {
                    Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
                } else if (mPhone.isEmpty()) {
                    Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    insertPhone(mName, mPhone);
                }
                break;
            default:
                break;
        }
    }

    private void insertPhone(String mName, String mPhone) {
        List<PhoneBean> mPhones = new ArrayList<>();
        mPhones.add(new PhoneBean(mPhone, mName, new Date()));
        PhoneDatabase.getDefault(getApplicationContext()).getPhoneDao().insertAll(mPhones);
        mNameEdit.setText("");
        mPhoneEdit.setText("");
    }

    private void queryPhone() {
        List<PhoneBean> mPhoneLists = PhoneDatabase.getDefault(getApplicationContext()).getPhoneDao().getPhoneAll();
        PhoneListAdapter mAdapter = new PhoneListAdapter(mPhoneLists);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(phoneBean -> {
            Bundle mBundle = new Bundle();
            mBundle.putParcelable("PhoneBean", phoneBean);
            EditDialog mDialog = new EditDialog();
            mDialog.show(MainActivity.this, mBundle, "edit");
            mDialog.setOnRefreshDataListener(() -> queryPhone());
        });
    }
}
