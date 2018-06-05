package com.ultraviolet.delieve.view.mypage.matchingList;

public interface MatchingListContract {

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
