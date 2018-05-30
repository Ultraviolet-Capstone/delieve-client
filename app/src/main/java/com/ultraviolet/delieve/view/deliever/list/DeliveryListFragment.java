package com.ultraviolet.delieve.view.deliever.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.widget.PullRefreshLayout;
import com.ultraviolet.delieve.R;
import com.ultraviolet.delieve.model.DeliveryRequest;
import com.ultraviolet.delieve.view.base.ContractFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeliveryListFragment extends ContractFragment<DeliveryListFragment.Contract> implements DeliveryListContract.View {

    @Inject
    DeliveryListPresenter presenter;

    @BindView(R.id.deliever_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.pull_refresh_layout)
    PullRefreshLayout mPullRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDiComponent().inject(this);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_delivery_list, container, false);

        ButterKnife.bind(this, rootView);
        presenter.setView(this);
        setupUi();

        return rootView;

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

    }

    void setupUi(){
        setUpRecyclerView();
        setUpPullRefreshLayout();
    }

    private void setUpPullRefreshLayout() {
        // listen refresh event
        mPullRefreshLayout.setOnRefreshListener(()
                -> mPullRefreshLayout.setRefreshing(false));
    }

    void setUpRecyclerView(){

        String [] test = {"test1", "test2", "test3"};

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        DeliveryListAdapter adapter = new DeliveryListAdapter(
                test,
                dr -> getContract().onItemSelected(dr));
        mRecyclerView.setAdapter(adapter);

    }

    public interface Contract {
        void onItemSelected(DeliveryRequest dr);
    }

}
