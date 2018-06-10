package com.ultraviolet.delieve.view.mypage.matchingList;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.widget.PullRefreshLayout;
import com.ultraviolet.delieve.R;
import com.ultraviolet.delieve.data.dto.DeliveryMatchingDto;
import com.ultraviolet.delieve.data.repository.DeliveryRepository;
import com.ultraviolet.delieve.data.repository.UserRepository;
import com.ultraviolet.delieve.model.DeliveryMatching;
import com.ultraviolet.delieve.view.base.ContractFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MatchingListFragment extends ContractFragment<MatchingListFragment.Contract> implements MatchingListContract.View {

    private List<DeliveryMatching> matchingList;

    @Inject
    DeliveryRepository mDeliveryRepository;

    @Inject
    UserRepository mUserRepository;

    @Inject
    MatchingListPresenter presenter;

    @BindView(R.id.deliever_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.pull_refresh_layout)
    PullRefreshLayout mPullRefreshLayout;

    private MatchingListAdapter mMatchingListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_matching_list, container, false);

        getDiComponent().inject(this);
        ButterKnife.bind(this, rootView);
        presenter.setView(this);
        init();
        setupUi();
        return rootView;
    }

    private void init() {
        this.matchingList = new ArrayList<>();
    }

    @Override
    public void showLoadingLayout() {

    }

    @Override
    public void showNoContentLayout() {

    }

    @Override
    public void showContentLayout() {

    }

    @Override
    public void updateContent() {
        mDeliveryRepository.getDeliveryMatchingListForSender(mUserRepository.getUserId())
                .subscribe(res->{
                    if (res.code() == 200 || res.code() == 304) {
                        if (res.body() == null){
                            showNoContentLayout();
                            matchingList.clear();
                        }
                        else {
                            matchingList.clear();
                            for (DeliveryMatchingDto E : res.body()) {
                                Log.d("credt","matching status : " +  E.matchingStatus);
                                matchingList.add(new DeliveryMatching(E));
                            }
                        }
                    }
                    mMatchingListAdapter.notifyDataSetChanged();
                    mPullRefreshLayout.setRefreshing(false);
                }, throwable -> {
                    throwable.printStackTrace();
                    mPullRefreshLayout.setRefreshing(false);

//                        view.showNoContentLayout();
                });
    }

    void setupUi(){
        setUpRecyclerView();
        setUpPullRefreshLayout();
        updateContent();
    }

    private void setUpPullRefreshLayout() {
        // listen refresh event
        mPullRefreshLayout.setOnRefreshListener(()
                -> {
            updateContent();
        });
    }

    void setUpRecyclerView(){
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        mMatchingListAdapter = new MatchingListAdapter(
                matchingList, mRecyclerView);
        mRecyclerView.setAdapter(mMatchingListAdapter);

    }
    public interface Contract {
        void onItemSelected(DeliveryMatching dr);
    }

}
