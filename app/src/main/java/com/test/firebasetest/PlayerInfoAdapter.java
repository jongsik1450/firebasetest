package com.test.firebasetest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlayerInfoAdapter extends RecyclerView.Adapter<PlayerInfoAdapter.ViewHolder> {

    private List<PlayerInsertData> playerList;
    private boolean showHeader; // 헤더를 보여줄지 여부를 저장하는 변수

    public PlayerInfoAdapter(List<PlayerInsertData> playerList) {
        this.playerList = playerList;
        this.showHeader = false; // 초기값은 헤더를 보여주지 않음
    }

    public void setShowHeader(boolean showHeader) {
        this.showHeader = showHeader;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 헤더를 보여줄지 여부에 따라 데이터 표시 여부 결정
        if (showHeader) {
            if (position == 0) {
                holder.textViewPlayerName.setText("이름");
                holder.textViewAge.setText("나이");
                return;
            }
            position--; // 헤더를 제외한 아이템 위치 계산
        }

        PlayerInsertData playerData = playerList.get(position);

        holder.textViewPlayerName.setText(playerData.getpName());
        holder.textViewAge.setText(playerData.getage());
    }

    @Override
    public int getItemCount() {
        // 헤더를 보여줄지 여부에 따라 아이템 개수 결정
        if (showHeader) {
            return playerList.size() + 1;
        } else {
            return playerList.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewPlayerName;
        public TextView textViewAge;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPlayerName = itemView.findViewById(R.id.textViewPlayerName);
            textViewAge = itemView.findViewById(R.id.textViewAge);
        }
    }
}
