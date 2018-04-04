package com.seniorproject.kabigonb.mahanoi.manager.http;

import com.seniorproject.kabigonb.mahanoi.dao.CloseListDao;
import com.seniorproject.kabigonb.mahanoi.dao.LoginDao;
import com.seniorproject.kabigonb.mahanoi.dao.MatchMakingDao;
import com.seniorproject.kabigonb.mahanoi.dao.OpenListDao;
import com.seniorproject.kabigonb.mahanoi.dao.ProviderDataDao;
import com.seniorproject.kabigonb.mahanoi.dao.RegisterDao;
import com.seniorproject.kabigonb.mahanoi.dao.RequestFormDao;
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

    @POST("userOfferRequest")
    Call<RequestFormDao> userRequest(@Body RequestFormDao requestFormDao);

    @POST("UserShowListResponse")
    Call<OpenListDao>  loadOpenService(@Body OpenListDao openListDao);

    @POST("userShowRequest")
    Call<CloseListDao> loadCloseService(@Body CloseListDao closeListDao);

    @POST("showProvider")
    Call<ProviderDataDao>  showProvider(@Body ProviderDataDao providerDataDao);

    @POST("userConfirmOffer")
    Call<MatchMakingDao>     userConfirmOffer(@Body MatchMakingDao matchMakingDao);
}
