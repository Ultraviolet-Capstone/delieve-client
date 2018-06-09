package com.ultraviolet.delieve.view.mypage.matchingList;

public class MatchingListPresenter implements MatchingListContract.Presenter {

    private MatchingListContract.View view;
    /*
    private List<DeliveryMatching> matchingList;

    private int userId;
*/

    @Override
    public void setView(MatchingListContract.View view) {
        this.view = view;
        loadData();
    }

    @Override
    public void loadData() {
        if (view != null) {
//            view.showLoadingLayout();

            refresh();
        }
    }

    @Override
    public void refresh() {
        view.updateContent();
    }
}
