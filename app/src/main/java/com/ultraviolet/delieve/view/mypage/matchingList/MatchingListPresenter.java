package com.ultraviolet.delieve.view.mypage.matchingList;

import com.ultraviolet.delieve.data.dto.DeliveryMatchingForSenderDto;
import com.ultraviolet.delieve.data.repository.DeliveryRepository;
import com.ultraviolet.delieve.data.repository.UserRepository;
import com.ultraviolet.delieve.model.DeliveryMatchingForSender;
import com.ultraviolet.delieve.model.User;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

public class MatchingListPresenter implements MatchingListContract.Presenter {

    private MatchingListContract.View view;
    /*
    private List<DeliveryMatchingForSender> matchingList;

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
