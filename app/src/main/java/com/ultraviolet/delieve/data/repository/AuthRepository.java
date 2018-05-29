package com.ultraviolet.delieve.data.repository;

import retrofit2.Response;
import rx.Observable;
import com.ultraviolet.delieve.data.dto.TokenDto;
import com.ultraviolet.delieve.data.dto.EmptyDto;
import com.ultraviolet.delieve.data.dto.UserDto;


public interface AuthRepository {
    Observable<TokenDto> getToken(String name, String password, String grantType);
    Observable<TokenDto> login(String token);
    Observable<Response<Void>> register(UserDto userDto);
}
