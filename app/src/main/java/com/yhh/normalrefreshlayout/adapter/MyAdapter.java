package com.yhh.normalrefreshlayout.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yhh.normalrefreshlayout.R;
import com.yhh.normalrefreshlayout.activity.CircleProgressActivity;
import com.yhh.normalrefreshlayout.activity.NormalRefreshActivity;
import com.yhh.normalrefreshlayout.activity.WaveActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yx on 2017/2/7.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    public List<String> list = new ArrayList();
    public Context context;

    public MyAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        holder.tvTitle.setText(list.get(position));
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (list.get(position)){
                    case("下拉刷新Demo") :
                        Intent intent1 = new Intent(context, NormalRefreshActivity.class);
                        context.startActivity(intent1);
                    break;
                    case("圆形进度条Demo") :
                        Intent intent2 = new Intent(context,CircleProgressActivity.class);
                        context.startActivity(intent2);
                        break;
                    case("View边界波浪Demo") :
                        Intent intent3 = new Intent(context,WaveActivity.class);
                        context.startActivity(intent3);
                        break;
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{

        TextView tvTitle;
        RelativeLayout relativeLayout;
        public MyHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.rl_item);

        }
    }
}
