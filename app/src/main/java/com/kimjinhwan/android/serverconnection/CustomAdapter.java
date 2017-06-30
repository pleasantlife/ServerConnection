package com.kimjinhwan.android.serverconnection;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kimjinhwan.android.serverconnection.domain.Bbs;
import com.kimjinhwan.android.serverconnection.domain.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XPS on 2017-06-30.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder> {

    //내부에서 사용할 데이터 정의
    //처음엔 꼭 new를 해줘야 함. 생성자를 하든지.
    List<Bbs> data = new ArrayList<>();

    public void setData(List<Bbs> data){
        this.data = data;
    }
    //4. 리스트의 크기만큼 ViewHolder의 개수가 생성
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new Holder(view);
    }

    //5. 화면에 리스트의 아이템이 그려질 때마다 호출되는 함수
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Bbs bbs = data.get(position);
        holder.textNo.setText(bbs.id+"");
        holder.textTitle.setText(bbs.title);
        holder.textAuthor.setText(bbs.author);
    }

    //3. 데이터의 크기를 아답터에 알려줌. => 리스트의 크기를 결정 (야! 데이터 얼마나 있어? -> 응. 이만큼.)
    @Override
    public int getItemCount() {
        return data.size();
    }

    //2. 데이터를 세팅하는 함수 생성
    class Holder extends RecyclerView.ViewHolder {

        TextView textNo;
        TextView textTitle;
        TextView textAuthor;

        public Holder(View itemView) {
            super(itemView);
            textNo = (TextView) itemView.findViewById(R.id.textNo);
            textTitle = (TextView) itemView.findViewById(R.id.textTitle);
            textAuthor = (TextView) itemView.findViewById(R.id.textAuthor);
        }
    }
}
