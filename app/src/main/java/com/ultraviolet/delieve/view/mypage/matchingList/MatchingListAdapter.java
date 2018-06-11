package com.ultraviolet.delieve.view.mypage.matchingList;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ultraviolet.delieve.R;
import com.ultraviolet.delieve.model.DeliveryMatching;
import com.ultraviolet.delieve.util.ImageLoadHelper;
import com.ultraviolet.delieve.view.send.SendMatchedActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MatchingListAdapter extends RecyclerView.Adapter<MatchingListAdapter.ViewHolder> {

    //@BindView(R.id.card_profilepic) ImageView mCardProfilePicImageView;
    //@BindView(R.id.card_delivername) TextView mCardDelieverNameTextView;
    @BindView(R.id.card_beginaddress) TextView mCardBeginAddress;
    @BindView (R.id.card_finish_address) TextView mCardFinishAddress;
    @BindView(R.id.card_stuffname) TextView mCardStuffName;
    @BindView(R.id.card_matching_status) TextView mCardMatchingStatus;
    @BindView(R.id.card_view_distance) TextView mDistanceView;

    private List<DeliveryMatching> mDataset;
    //private DeliveryClickListener deliveryClickListener;
    private View.OnClickListener onClickListener;
    private RecyclerView mRecyclerView;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView mCardView;
        public TextView mTextView;
        public ViewHolder(View v) {
            super(v);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MatchingListAdapter(List<DeliveryMatching> myDataset, RecyclerView v) {
        this.mRecyclerView = v;
        mDataset = myDataset;
        //this.deliveryClickListener = deliveryClickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MatchingListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_cardview, parent, false);
        v.setOnClickListener(v1 -> {
            int position = mRecyclerView.getChildAdapterPosition(v1);
            DeliveryMatching data = mDataset.get(position);

            Intent intent = new Intent(mRecyclerView.getContext(), SendMatchedActivity.class);
            intent.putExtra("Matching", data);
            mRecyclerView.getContext().startActivity(intent);

        });
        ViewHolder vh = new ViewHolder(v);
        ButterKnife.bind(this, v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DeliveryMatching item = mDataset.get(position);
        Log.d("credt", "position : " + position);
        Log.d("credt", "url : " + item.delieverSelfiURL);
        //Log.d("credt", mCardProfilePicImageView.toString());
        //if (item.delieverSelfiURL != null) {
        //    ImageLoadHelper.loadProfileImage(mCardProfilePicImageView, item.delieverSelfiURL);
        //}
        ///mCardDelieverNameTextView.setText(item.delieverName);
        mCardBeginAddress.setText(item.beginAddress);
        mCardFinishAddress.setText(item.finishAddress);
        mCardStuffName.setText(item.stuffName);
        mDistanceView.setText(String.format("%.2f", item.distance )+ "Km");
        String status = " ";
        switch(item.matchingStatus){
            case "READY":
                status = "양도 대기중";
                break;
            case "PROGRESS":
                status = "배달 진행중";
                break;
            case "FINISH":
                status = "배달 완료";
                break;
            case "ACCIDENT":
                status = "사고";
                break;
            case "CANCELED":
                status = "취소됨";
                break;
        }
        mCardMatchingStatus.setText(status);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    interface DeliveryClickListener {
        void onDelieryClick(DeliveryMatching city);
    }

}