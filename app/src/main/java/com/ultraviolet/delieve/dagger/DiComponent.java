package com.ultraviolet.delieve.dagger;

import com.ultraviolet.delieve.dagger.module.RepositoryModule;
import com.ultraviolet.delieve.dagger.module.ServiceModule;
import com.ultraviolet.delieve.view.deliever.DelieverWaitingForMatchingActivity;
import com.ultraviolet.delieve.view.deliever.list.DeliveryListFragment;
import com.ultraviolet.delieve.view.enroll.BeforeEnrollFragment;
import com.ultraviolet.delieve.view.enroll.EvaluateDeliver3;
import com.ultraviolet.delieve.view.login.SignupActivity;
import com.ultraviolet.delieve.view.login.KakaoSignupActivity;
import com.ultraviolet.delieve.view.main.MainActivity;
import com.ultraviolet.delieve.dagger.module.ApplicationModule;
import com.ultraviolet.delieve.dagger.module.NetworkModule;
import com.ultraviolet.delieve.view.send.SendFragment;
import com.ultraviolet.delieve.view.send.SendWaitingActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkModule.class,
        RepositoryModule.class,
        ServiceModule.class
})
public interface DiComponent {

    void inject(MainActivity activity);

    void inject(SendFragment fragment);

    void inject(BeforeEnrollFragment fragment);

    void inject(DeliveryListFragment fragment);

    void inject(EvaluateDeliver3 activity);

    void inject(SignupActivity activity);

    void inject(KakaoSignupActivity activity);

    void inject(DelieverWaitingForMatchingActivity activity);

    void inject(SendWaitingActivity activity);

}