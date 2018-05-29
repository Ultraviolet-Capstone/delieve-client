package com.ultraviolet.delieve.dagger.module;

import com.ultraviolet.delieve.data.repository.AuthRepository;
import com.ultraviolet.delieve.data.repository.UserRepository;
import com.ultraviolet.delieve.data.repository.impl.AuthRepositoryImpl;
import com.ultraviolet.delieve.data.repository.impl.UserRepositoryImpl;
import com.ultraviolet.delieve.data.service.AuthService;

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

}
