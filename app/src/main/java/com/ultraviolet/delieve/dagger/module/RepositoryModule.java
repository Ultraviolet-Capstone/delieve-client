package com.ultraviolet.delieve.dagger.module;

import com.ultraviolet.delieve.data.repository.AuthRepository;
import com.ultraviolet.delieve.data.repository.DeliveryRepository;
import com.ultraviolet.delieve.data.repository.EnrollRepository;
import com.ultraviolet.delieve.data.repository.GPSTrackingRepository;
import com.ultraviolet.delieve.data.repository.QRApiRepository;
import com.ultraviolet.delieve.data.repository.UserInfoRepository;
import com.ultraviolet.delieve.data.repository.UserRepository;
import com.ultraviolet.delieve.data.repository.impl.AuthRepositoryImpl;
import com.ultraviolet.delieve.data.repository.impl.DeliveryRepositoryImpl;
import com.ultraviolet.delieve.data.repository.impl.EnrollRepositoryImpl;
import com.ultraviolet.delieve.data.repository.impl.GPSTrackingRepositoryImpl;
import com.ultraviolet.delieve.data.repository.impl.QRApiRepositoryImpl;
import com.ultraviolet.delieve.data.repository.impl.UserInfoRepositoryImpl;
import com.ultraviolet.delieve.data.repository.impl.UserRepositoryImpl;
import com.ultraviolet.delieve.data.service.AuthService;
import com.ultraviolet.delieve.data.service.DeliveryService;
import com.ultraviolet.delieve.data.service.EnrollService;
import com.ultraviolet.delieve.data.service.GPSTrackingService;
import com.ultraviolet.delieve.data.service.QRApiService;
import com.ultraviolet.delieve.data.service.UserInfoService;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    private UserRepository mUserRepository;

    public RepositoryModule() {
        this.mUserRepository = new UserRepositoryImpl();
    }

    @Provides
    UserInfoRepository provideUserInfoRepository(UserInfoService service) {
        return new UserInfoRepositoryImpl(service);
    }

    @Provides
    AuthRepository provideAuthRepository(AuthService service) {
        return new AuthRepositoryImpl(service);
    }

    @Provides
    UserRepository provideUserRepository() {
        return mUserRepository;
    }

    @Provides
    DeliveryRepository provideDeliveryRequestRepository(DeliveryService service) {
        return new DeliveryRepositoryImpl(service);
    }

    @Provides
    EnrollRepository provideEnrollRepository(EnrollService service) {
        return new EnrollRepositoryImpl(service);
    }

    @Provides
    QRApiRepository provideQRApiRepository(QRApiService service) {
        return new QRApiRepositoryImpl(service);
    }

    @Provides
    GPSTrackingRepository provideGpsTrackingRepository(GPSTrackingService service) {
        return new GPSTrackingRepositoryImpl(service);
    }
}