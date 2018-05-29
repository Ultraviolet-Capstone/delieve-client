package com.ultraviolet.delieve.data.repository;

import rx.Observable;
import com.ultraviolet.delieve.data.dto.AuthDto;


public interface AuthRepository {
    Observable<AuthDto> getToken(String name, String password, String grantType);

}
