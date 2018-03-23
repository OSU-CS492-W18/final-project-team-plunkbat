package com.mobiledev.pubgtracker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by stphn on 3/7/2018.
 */

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.SavedPlayersViewHolder> {

    private ArrayList<String> mSavedPlayerList;
    OnSavedPlayerClickListener mSavedPlayerClickListener;

    // data is passed into the constructor
    public TrackAdapter(OnSavedPlayerClickListener savedPlayerClickListener) {
        mSavedPlayerClickListener = savedPlayerClickListener;
    }

    public void updateSavedPlayers(ArrayList<String> savedPlayerList) {
        mSavedPlayerList = savedPlayerList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mSavedPlayerList != null) {
            return mSavedPlayerList.size();
        } else {
            return 0;
        }
    }

    // inflates the row layout from xml when needed
    @Override
    public SavedPlayersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.player_saved_item, parent, false);
        return new SavedPlayersViewHolder(view);
    }

    // parent activity will implement this method to respond to click events
    public interface OnSavedPlayerClickListener {
        void onSavedPlayerClick(String player);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(SavedPlayersViewHolder holder, int position) {
        holder.bind(mSavedPlayerList.get(position));
    }

    // stores and recycles views as they are scrolled off screen
    public class SavedPlayersViewHolder extends RecyclerView.ViewHolder {
        private TextView mSavedPlayerTV;

        SavedPlayersViewHolder(View itemView) {
            super(itemView);
            mSavedPlayerTV = itemView.findViewById(R.id.tv_player_rv_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String savedPlayer = mSavedPlayerList.get(getAdapterPosition());
                    mSavedPlayerClickListener.onSavedPlayerClick(savedPlayer);
                }
            });
        }

        public void bind(String playerUsername) {
            mSavedPlayerTV.setText(playerUsername);
        }
    }
}
