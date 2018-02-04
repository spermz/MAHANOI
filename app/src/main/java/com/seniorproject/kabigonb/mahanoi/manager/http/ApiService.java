package com.seniorproject.kabigonb.mahanoi.manager.http;

import com.seniorproject.kabigonb.mahanoi.dao.LoginDao;
import com.seniorproject.kabigonb.mahanoi.dao.RegisterDao;
import com.seniorproject.kabigonb.mahanoi.dao.TokenDao;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by LaFezTer on 28-Jan-18.
 */

public interface ApiService {

    @POST("registerUser")
    Call<RegisterDao> registerUser(@Body RegisterDao registerDao);

    @POST("userLogin")
    Call<TokenDao> userLogin(@Body LoginDao loginDao);

    @POST("userLogout")
    Call<TokenDao> userLogout(@Body TokenDao tokenDao);


}
