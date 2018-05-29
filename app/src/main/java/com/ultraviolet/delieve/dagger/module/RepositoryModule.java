package com.ultraviolet.delieve.dagger.module;

import com.ultraviolet.delieve.data.repository.AuthRepository;
import com.ultraviolet.delieve.data.repository.DeliveryRequestRepository;
import com.ultraviolet.delieve.data.repository.UserRepository;
import com.ultraviolet.delieve.data.repository.impl.AuthRepositoryImpl;
import com.ultraviolet.delieve.data.repository.impl.DeliveryRequestRepositoryImpl;
import com.ultraviolet.delieve.data.repository.impl.UserRepositoryImpl;
import com.ultraviolet.delieve.data.service.AuthService;
import com.ultraviolet.delieve.data.service.DeliveryRequestService;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    AuthRepository provideAuthRepository(AuthService service) {
        return new AuthRepositoryImpl(service);
    }
    @Provides
    UserRepository provideUserRepository(){
        return new UserRepositoryImpl();
    }

    @Provides
    DeliveryRequestRepository provideDeliveryRequestRepository(DeliveryRequestService service)
    {return new DeliveryRequestRepositoryImpl(service); }
}
