package dta.api.services;

import dta.api.models.GithubUser;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubService {
	  @GET("users/{pseudo}?access_token=6b4d1059ad3605c3a1912e5f1cf98e1dfdf84d97")
	  Call<GithubUser> getUserInfo(@Path("pseudo") String pseudo);
}
