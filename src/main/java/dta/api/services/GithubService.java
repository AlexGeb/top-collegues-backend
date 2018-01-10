package dta.api.services;

import dta.api.models.GithubUser;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubService {
	  @GET("users/{pseudo}?access_token=github token")
	  Call<GithubUser> getUserInfo(@Path("pseudo") String pseudo);
}
