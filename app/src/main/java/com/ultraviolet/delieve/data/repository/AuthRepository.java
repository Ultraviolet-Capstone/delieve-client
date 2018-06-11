package com.ultraviolet.delieve.data.repository;

import retrofit2.Response;
import rx.Observable;

import com.ultraviolet.delieve.data.dto.LoginDto;
import com.ultraviolet.delieve.data.dto.TokenDto;
import com.ultraviolet.delieve.data.dto.UserDto;


public interface AuthRepository {
    Observable<TokenDto> getToken(String name, String password, String grantType);
    Observable<Response<LoginDto>> login(String token, String pushToken);
    Observable<Response<Void>> register(UserDto userDto);
}
