package com.ultraviolet.delieve.view.deliever.list;

public interface DeliveryListContract {

    interface  View {
        void showLoadingLayout();
        void showNoContentLayout();
        void showContentLayout();
        void updateContent();

    }
    interface  Presenter  {
        void setView (View view);
        void loadData();
        void refresh();
    }
}
