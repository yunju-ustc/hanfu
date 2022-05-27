package com.mawenfei.hanfu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.mawenfei.hanfu.R;
import com.mawenfei.hanfu.adapter.AllCommodityAdapter1;
import com.mawenfei.hanfu.bean.Commodity;
import com.mawenfei.hanfu.util.CommodityDbHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * 不同类型商品信息的活动类
 */
public class CommodityTypeActivity extends AppCompatActivity {

    TextView tvCommodityType;
    ListView lvCommodityType;
    List<Commodity> commodities = new LinkedList<>();

    CommodityDbHelper dbHelper;
    AllCommodityAdapter1 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_type);
        //返回事件
        TextView tvBack = findViewById(R.id.tv_back);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvCommodityType = findViewById(R.id.tv_type);
        lvCommodityType = findViewById(R.id.list_commodity);
        dbHelper = new CommodityDbHelper(getApplicationContext(),CommodityDbHelper.DB_NAME,null,1);
        adapter = new AllCommodityAdapter1(getApplicationContext());
        //根据不同的状态显示不同的界面
        int status = this.getIntent().getIntExtra("status",0);
        if(status == 1) {
            tvCommodityType.setText("古风男装");
        }else if(status == 2) {
            tvCommodityType.setText("古风女装");
        }else if(status == 3) {
            tvCommodityType.setText("无");
        }else if(status == 4) {
            tvCommodityType.setText("无");
        }
        //根据不同类别显示不同的商品信息
        commodities = dbHelper.readCommodityType(tvCommodityType.getText().toString());
        adapter.setData(commodities);
        lvCommodityType.setAdapter(adapter);

        //为每一个item设置点击事件
        lvCommodityType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Commodity commodity = (Commodity) lvCommodityType.getAdapter().getItem(position);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("position",position);
                bundle1.putByteArray("picture",commodity.getPicture());
                bundle1.putString("title",commodity.getTitle());
                bundle1.putString("description",commodity.getDescription());
                bundle1.putFloat("price",commodity.getPrice());
                bundle1.putString("phone",commodity.getPhone());
                bundle1.putString("stuId",null);
                Intent intent = new Intent(CommodityTypeActivity.this, ReviewCommodityActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

    }
}
