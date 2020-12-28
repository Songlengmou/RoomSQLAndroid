package com.anningtex.roomsqlandroid.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.EditText;

import com.anningtex.roomsqlandroid.R;
import com.anningtex.roomsqlandroid.bean.OrderSpecEntity;
import com.anningtex.roomsqlandroid.dao.OrderSpecDao;
import com.anningtex.roomsqlandroid.db.PhoneDatabase;
import com.syp.library.BaseRecycleAdapter;

import java.util.List;

/**
 * @author Administrator
 */
public class OrderSpecActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mInsertOrderEdit, mInsertMetersPerBaleEdit, mInsertUnitEnEdit;
    private RecyclerView mRvItem;
    private BaseRecycleAdapter adapter;
    private OrderSpecDao orderSpecDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_spec);
        initView();
    }

    private void initView() {
        //Initialization
        mInsertOrderEdit = findViewById(R.id.insert_order_edit);
        mInsertMetersPerBaleEdit = findViewById(R.id.insert_metersPerBale_edit);
        mInsertUnitEnEdit = findViewById(R.id.insert_unitEn_edit);
        findViewById(R.id.insert_button).setOnClickListener(this);
        findViewById(R.id.query_button).setOnClickListener(this);
        mRvItem = findViewById(R.id.rv_item);
        //Dao
        orderSpecDao = PhoneDatabase.getDefault(getApplicationContext()).getOrderSpecDao();
        queryAllData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.insert_button:
                String order = mInsertOrderEdit.getText().toString().trim();
                String metersPerBale = mInsertMetersPerBaleEdit.getText().toString().trim();
                String unit = mInsertUnitEnEdit.getText().toString().trim();
                OrderSpecEntity orderSpecEntity = new OrderSpecEntity(order, metersPerBale, unit);
                orderSpecDao.insertOrderSpecEntity(orderSpecEntity);
                queryAllData();
                break;
            case R.id.query_button:
                queryAllData();
                break;
            default:
                break;
        }
    }

    private void queryAllData() {
        List<OrderSpecEntity> orderSpecEntities = orderSpecDao.queryAll();
        if (orderSpecEntities != null && orderSpecEntities.size() > 0) {
            adapter = new BaseRecycleAdapter(R.layout.item_phone_text, orderSpecEntities);
            mRvItem.setAdapter(adapter);
            adapter.setOnDataToViewListener((helper, item, position) -> {
                OrderSpecEntity orderSpecEntity = (OrderSpecEntity) item;
                helper.setText(R.id.phone_number_text, orderSpecEntity.getOrder() + " \n " +
                        orderSpecEntity.getMetersPerBale() + orderSpecEntity.getUnitEn());
            });
            adapter.setOnItemLongClickListener((adapter, view, position) -> {
                List<OrderSpecEntity> data = adapter.getData();
                OrderSpecEntity orderSpecEntity = data.get(position);
                orderSpecDao.deleteOrderSpecEntity(orderSpecEntity);
                orderSpecEntities.remove(position);
                adapter.notifyDataSetChanged();
                return false;
            });
        }
    }
}