package com.mawenfei.hanfu.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mawenfei.hanfu.R;
import com.mawenfei.hanfu.adapter.MyCollectionAdapter;
import com.mawenfei.hanfu.bean.Collection;
import com.mawenfei.hanfu.util.MyCollectionDbHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * 我的收藏Activity类
 */
public class MyCollectionActivity extends AppCompatActivity {

    ListView lvMyCollection;
    List<Collection> myCollections = new ArrayList<>();
    TextView tvStuId;

    MyCollectionDbHelper dbHelper;
    //CommodityDbHelper commodityDbHelper;
    MyCollectionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        //返回
        TextView tvBack = findViewById(R.id.tv_back);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvStuId = findViewById(R.id.tv_stuId);
        tvStuId.setText(this.getIntent().getStringExtra("stuId"));
        lvMyCollection = findViewById(R.id.lv_my_collection);
        dbHelper = new MyCollectionDbHelper(getApplicationContext(),MyCollectionDbHelper.DB_NAME,null,1);
        myCollections = dbHelper.readMyCollections(tvStuId.getText().toString());
        adapter = new MyCollectionAdapter(getApplicationContext());
        adapter.setData(myCollections);
        lvMyCollection.setAdapter(adapter);
        //设置长按访问事件
        lvMyCollection.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Collection collection = (Collection) lvMyCollection.getAdapter().getItem(i);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("position",i);
                bundle1.putByteArray("picture",collection.getPicture());
                bundle1.putString("title",collection.getTitle());
                bundle1.putString("description",collection.getDescription());
                bundle1.putFloat("price",collection.getPrice());
                bundle1.putString("phone",collection.getPhone());
                bundle1.putString("stuId",null);
                Intent intent = new Intent(MyCollectionActivity.this, ReviewCommodityActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
                return false;
            }
        });

//        lvMyCollection.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(MyCollectionActivity.this);
//                builder.setTitle("提示:").setMessage("确定删除此收藏商品吗?").setIcon(R.drawable.icon_user).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        Collection collection = (Collection) adapter.getItem(position);
//                        //删除收藏商品项
//                        dbHelper.deleteMyCollection(collection.getTitle(),collection.getDescription(),collection.getPrice());
//                        Toast.makeText(MyCollectionActivity.this,"删除成功!",Toast.LENGTH_SHORT).show();
//                    }
//                }).show();
//                return false;
//            }
//        });
        //页面刷新
        TextView tvRefresh = findViewById(R.id.tv_refresh);
        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCollections = dbHelper.readMyCollections(tvStuId.getText().toString());
                adapter.setData(myCollections);
                lvMyCollection.setAdapter(adapter);
            }
        });

        //为每一个item设置点击删除事件
        lvMyCollection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyCollectionActivity.this);
                builder.setTitle("提示:").setMessage("确定删除此收藏商品吗?").setIcon(R.drawable.icon_user).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Collection collection = (Collection) adapter.getItem(position);
                        //删除收藏商品项
                        dbHelper.deleteMyCollection(collection.getTitle(),collection.getDescription(),collection.getPrice());
                        Toast.makeText(MyCollectionActivity.this,"删除成功!",Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });
    }
}
