package com.mapbar.dialogfragment;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
* @Description: 对话框的fragment
* @author  作者 :likun
* @date 创建时间：2016/10/9 20:04
* @parameter :
* @return :
*/
public class DialogListView extends DialogFragment{
    //对话框显示的位置
    private static int sLocation;
    //对话框的宽度
    private static int sWidth;
    //对话框的高度
    private static int sHeight;
    //对话框的位置
    private static final int CENTER=0;
    private static final int LEFT_CENTER =1;
    private static final int RIGHT_CENTER=2;
    private static final int TOP_CENTER =3;
    private static final int BOTTOM_CENTER=4;
    //宽度
    private static final int WIDTH_MATCH=0;
    private static final int WIDTH_WRAP=1;
    //高度
    private static final int HEIGHT_MATCH=0;
    private static final int HEIGHT_WRAP=1;
    //点击空白处是否消失，默认点击空白处消失
    private static boolean sCancle=true;
    @InjectView(R.id.lv_dialog)
    ListView lv_dialog;
    //布局
    private View mView;
    //要展示的数据
    private static ArrayList<String> sDatas;

    /**
    * @Description: 初始化对话框对象
    * @author  作者 :likun
    * @date 创建时间：2016/10/10 14:22
    * @parameter : location 对话框的位置；width 对话框的宽度；height 对话框的高度；iscancle 是否可以点击空白处消失，true表示可以；
     * datas 要展示的数据；
    * @return :
    */
    public static DialogListView getInstance(int location, int width, int height, boolean iscancle, ArrayList<String> datas){
        Bundle bundle=new Bundle();
        DialogListView dialog=new DialogListView();
        dialog.setArguments(bundle);
        sLocation = location;
        sWidth = width;
        sHeight = height;
        sCancle=iscancle;
        sDatas = datas;
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        //设置弹出对话框的位置
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity= getLocation(sLocation);
        //params.width=WindowManager.LayoutParams.WRAP_CONTENT;
        params.width=getWidth(sWidth);
        //params.height=WindowManager.LayoutParams.WRAP_CONTENT;
        params.height=getHeight(sHeight);
        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //如果setCancelable()中参数为true，若点击dialog覆盖不到的activity的空白或者按返回键，则进行cancel，
        // 状态检测依次onCancel()和onDismiss()。如参数为false，则按空白处或返回键无反应。缺省为true
        setCancelable(sCancle);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //去掉对话框的标题头
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //确定对话框进出动画
        doAnimation(sLocation);
        mView = inflater.inflate(R.layout.dialog_listview,container,false);
        ButterKnife.inject(this, mView);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv_dialog.setAdapter(new MySelectAdapter(getContext()));
        lv_dialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int positon, long l) {
                if(sDatas!=null&&sDatas.size()>0){
                    //点击对话框的回调
                    DialogDismissListener listener=(DialogDismissListener)getActivity();
                    listener.dialogDis(sDatas.get(positon));
                    dismiss();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

    }
    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

    @Override
    public void onStop() {
        super.onStop();
    }
    /**
     * @Description: 设置对话框的高度
     * @author  作者 :likun
     * @date 创建时间：2016/10/10 17:17
     * @parameter :
     * @return :
     */
    private int getHeight(int height) {
        int h;
        switch (height){
            case HEIGHT_MATCH:
                h=WindowManager.LayoutParams.MATCH_PARENT;
                break;
            case HEIGHT_WRAP:
                h=WindowManager.LayoutParams.WRAP_CONTENT;
                break;
            default:
                h=WindowManager.LayoutParams.WRAP_CONTENT;
                break;
        }
        return h;
    }

    /**
     * @Description: 设置对话框的宽度
     * @author  作者 :likun
     * @date 创建时间：2016/10/10 17:17
     * @parameter :
     * @return :
     */
    private int getWidth(int width) {
        int w;
        switch (width){
            case WIDTH_MATCH:
                w=WindowManager.LayoutParams.MATCH_PARENT;
                break;
            case WIDTH_WRAP:
                w=WindowManager.LayoutParams.WRAP_CONTENT;
                break;
            default:
                w=WindowManager.LayoutParams.WRAP_CONTENT;
                break;
        }
        return w;
    }

    /**
     * @Description: 确定对话框的位置,默认为居中显示
     * @author  作者 :likun
     * @date 创建时间：2016/10/10 10:08
     * @parameter : location 需要弹出对话框的位置
     * @return :
     */
    private int getLocation(int location) {
        int dialogLocation;
        switch(location){
            case LEFT_CENTER:
                dialogLocation= Gravity.LEFT|Gravity.CENTER_VERTICAL;
                break;
            case RIGHT_CENTER:
                dialogLocation= Gravity.RIGHT|Gravity.CENTER_VERTICAL;
                break;
            case TOP_CENTER:
                dialogLocation= Gravity.TOP|Gravity.CENTER_HORIZONTAL;
                break;
            case BOTTOM_CENTER:
                dialogLocation= Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL;
                break;
            case CENTER:
                dialogLocation= Gravity.CENTER;
                break;
            default:
                dialogLocation= Gravity.CENTER;
                break;
        }
        return dialogLocation;
    }
    /**
    * @Description: 根据对话框的位置选择弹出移除动画
    * @author  作者 :likun
    * @date 创建时间：2016/10/11 10:40
    * @parameter :
    * @return :
    */
    private void doAnimation(int location) {
        switch (location){
            case LEFT_CENTER:
                getDialog().getWindow().setWindowAnimations(R.style.left_dialog);
                break;
            case RIGHT_CENTER:
                getDialog().getWindow().setWindowAnimations(R.style.right_dialog);
                break;
            case TOP_CENTER:
                getDialog().getWindow().setWindowAnimations(R.style.top_dialog);
                break;
            case BOTTOM_CENTER:
                getDialog().getWindow().setWindowAnimations(R.style.bottom_dialog);
                break;
            case CENTER:
                getDialog().getWindow().setWindowAnimations(R.style.center_dialog);
                break;
        }
    }
    /**
    * @Description: 向activity传递参数的回调接口
    * @author  作者 :likun
    * @date 创建时间：2016/10/11 9:43
    * @parameter :
    * @return :
    */
    public interface DialogDismissListener{
        void dialogDis(String name);
    }
    private class MySelectAdapter extends BaseAdapter {
        LayoutInflater mLayoutInflater = null;

        public MySelectAdapter(Context context) {
            super();
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            if (sDatas != null && sDatas.size() > 0) {
                return sDatas.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (sDatas != null && sDatas.size() > 0) {
                return sDatas.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (sDatas == null && sDatas.size() > 0) {
                return null;
            }
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.sinner_item, null);
            }
            SelectViewHolder viewHolder=SelectViewHolder.getViewHolder(convertView);
            viewHolder.tv_desc.setText(sDatas.get(position));
            if(position==sDatas.size()-1){
                viewHolder.tv_line.setVisibility(View.GONE);
            }else{
                viewHolder.tv_line.setVisibility(View.VISIBLE);
            }
            return convertView;
        }
    }
    static class SelectViewHolder {
        TextView tv_desc = null;
        TextView tv_line = null;

        public SelectViewHolder(View view) {
            tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            tv_line = (TextView) view.findViewById(R.id.tv_line);
        }

        public static SelectViewHolder getViewHolder(View view) {
            SelectViewHolder viewHolder = (SelectViewHolder) view.getTag();
            if (viewHolder == null) {
                viewHolder = new SelectViewHolder(view);
                view.setTag(viewHolder);
            }
            return viewHolder;
        }
    }
}
