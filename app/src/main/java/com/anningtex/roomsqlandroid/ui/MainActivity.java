package com.anningtex.roomsqlandroid.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anningtex.roomsqlandroid.R;
import com.anningtex.roomsqlandroid.adapter.PhoneListAdapter;
import com.anningtex.roomsqlandroid.adapter.PopPhoneAdapter;
import com.anningtex.roomsqlandroid.bean.PhoneBean;
import com.anningtex.roomsqlandroid.db.PhoneDatabase;
import com.anningtex.roomsqlandroid.pop.PopupWindowUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 * desc:使用Room数据库在Android下的使用，而不是AndroidX
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mNameEdit, mPhoneEdit;
    private TextView mTvTotal;
    private RecyclerView mRecyclerView;
    private PopupWindowUtils popupWindow;
    private PopPhoneAdapter popPhoneAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        addEtNameViewClick();
    }

    private void initView() {
        mNameEdit = findViewById(R.id.insert_name_edit);
        mPhoneEdit = findViewById(R.id.insert_phone_edit);
        mTvTotal = findViewById(R.id.tv_total);
        mRecyclerView = findViewById(R.id.data_list_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        findViewById(R.id.query_button).setOnClickListener(this);
        findViewById(R.id.insert_button).setOnClickListener(this);
        findViewById(R.id.btn_launch).setOnClickListener(this);
        Integer total = PhoneDatabase.getDefault(getApplicationContext()).getPhoneDao().queryPhoneAllDataNum();
        mTvTotal.setText("提示：点击条目进行修改和删除操作( total: " + total + " )");
    }

    private void addEtNameViewClick() {
        popPhoneAdapter = new PopPhoneAdapter(MainActivity.this);
        popupWindow = new PopupWindowUtils(this);
        mNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<PhoneBean> phoneBeanList = PhoneDatabase.getDefault(getApplicationContext()).getPhoneDao().getPhoneBeanByName(s.toString());
                Log.e("666", "queryName" + phoneBeanList.size());
                popPhoneAdapter.setList(phoneBeanList);
                showPop();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void showPop() {
        View popView = LayoutInflater.from(this).inflate(R.layout.popup_window_list, null, false);
        ListView popList = popView.findViewById(R.id.pop_list);
        popupWindow.setContentView(popView);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchInterceptor((v, event) -> {
            // 这里拦截不到返回键
            return false;
        });
        popList.setDivider(new ColorDrawable(Color.WHITE));
        popList.setDividerHeight(1);
        popList.setAdapter(popPhoneAdapter);
        popPhoneAdapter.notifyDataSetChanged();
        popList.setOnItemClickListener((parent, view, position, id) -> {
            mNameEdit.setText(popPhoneAdapter.getList().get(position).getName());
            mNameEdit.setSelection(mNameEdit.length());
            popupWindow.dismiss();
        });
        popupWindow.showAsDropDown(mNameEdit);
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
            case R.id.btn_launch:
                startActivity(new Intent(MainActivity.this, OrderSpecActivity.class));
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
        //倒序
        Collections.reverse(mPhoneLists);
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
