package com.ultraviolet.delieve.dagger.module;

import com.ultraviolet.delieve.data.repository.AuthRepository;
import com.ultraviolet.delieve.data.repository.DeliveryRepository;
import com.ultraviolet.delieve.data.repository.EnrollRepository;
import com.ultraviolet.delieve.data.repository.UserRepository;
import com.ultraviolet.delieve.data.repository.impl.AuthRepositoryImpl;
import com.ultraviolet.delieve.data.repository.impl.DeliveryRepositoryImpl;
import com.ultraviolet.delieve.data.repository.impl.EnrollRepositoryImpl;
import com.ultraviolet.delieve.data.repository.impl.UserRepositoryImpl;
import com.ultraviolet.delieve.data.service.AuthService;
import com.ultraviolet.delieve.data.service.DeliveryService;
import com.ultraviolet.delieve.data.service.EnrollService;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    AuthRepository provideAuthRepository(AuthService service) {
        return new AuthRepositoryImpl(service);
    }

    @Provides
    UserRepository provideUserRepository() {
        return new UserRepositoryImpl();
    }

    @Provides
    DeliveryRepository provideDeliveryRequestRepository(DeliveryService service) {
        return new DeliveryRepositoryImpl(service);
    }

    @Provides
    EnrollRepository provideEnrollRepository(EnrollService service) {
        return new EnrollRepositoryImpl(service);
    }

}