package com.ultraviolet.delieve.dagger.module;

import com.ultraviolet.delieve.data.service.AuthService;
import com.ultraviolet.delieve.data.service.DeliveryRequestService;
import com.ultraviolet.delieve.data.service.EnrollService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ServiceModule {

    @Provides
    AuthService provideAuthService(Retrofit retrofit) {
        return retrofit.create(AuthService.class);
    }

    @Provides
    EnrollService provideEnrollService(Retrofit retrofit){
        return retrofit.create(EnrollService.class);
    }

    @Provides
    DeliveryRequestService provideDeliveryRequestService(Retrofit retrofit) {
        return retrofit.create(DeliveryRequestService.class);
    }


}
