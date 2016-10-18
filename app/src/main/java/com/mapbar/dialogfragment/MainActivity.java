package com.mapbar.dialogfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;


import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements DialogListView.DialogDismissListener {
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        ButterKnife.inject(this);
    }
    @OnClick({R.id.bt_center_dialog,R.id.bt_top_dialog,R.id.bt_bottom_dialog,R.id.bt_left_dialog,R.id.bt_right_dialog})
    void viewOnclick(View view){
        switch (view.getId()){
            case R.id.bt_center_dialog:
               /* Dialog center = Dialog.getInstance(0,1,1,true);
                center.show(getSupportFragmentManager(),Dialog.class.getSimpleName());*/
                ArrayList<String>data=new ArrayList<>();
                data.add("移动端采集模式");
                data.add("服务端采集模式");
                data.add("移动端定位模式");
                data.add("服务端定位模式");
                data.add("混合定位模式");
                DialogListView center = DialogListView.getInstance(0,1,1,true,data);
                center.show(getSupportFragmentManager(),DialogListView.class.getSimpleName());
                break;
            case R.id.bt_top_dialog:
                Dialog top = Dialog.getInstance(3,0,1,true);
                top.show(getSupportFragmentManager(),Dialog.class.getSimpleName());
                break;
            case R.id.bt_bottom_dialog:
                Dialog bottom = Dialog.getInstance(4,0,1,true);
                bottom.show(getSupportFragmentManager(),Dialog.class.getSimpleName());
                break;
            case R.id.bt_left_dialog:
                Dialog left = Dialog.getInstance(1,1,0,true);
                left.show(getSupportFragmentManager(),Dialog.class.getSimpleName());
                break;
            case R.id.bt_right_dialog:
                Dialog right = Dialog.getInstance(2,1,0,true);
                right.show(getSupportFragmentManager(),Dialog.class.getSimpleName());
                break;
        }
    }

    @Override
    public void dialogDis(String name) {
        if(name.equalsIgnoreCase("")){
            Toast.makeText(mContext,"名字为空",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(mContext,name,Toast.LENGTH_SHORT).show();
        }
    }

}
