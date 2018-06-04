package com.ultraviolet.delieve.view.deliever.list;

import com.ultraviolet.delieve.model.DeliveryMatchingForDeliever;

import java.util.ArrayList;
import java.util.List;

public class DeliveryListPresenter implements DeliveryListContract.Presenter {

    private DeliveryListContract.View view;
    private List<DeliveryMatchingForDeliever> mDeliveryRequestList;

    // @Inject
    // DeliveryListRepository

    @Override
    public void setView(DeliveryListContract.View view) {
        this.view = view;
        loadData();
    }

    @Override
    public void loadData() {
        if (view != null) {
            view.showLoadingLayout();
            // Delivery Repositoty
            mDeliveryRequestList = new ArrayList<>();
            refresh();
        }
    }

    @Override
    public void refresh() {
    }
}
