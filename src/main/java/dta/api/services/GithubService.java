package dta.api.services;

import dta.api.models.GithubUser;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubService {
	  @GET("users/{pseudo}?access_token=26fe2c7c17e6a3998e3f1b4e525f6d51ad736397")
	  Call<GithubUser> getUserInfo(@Path("pseudo") String pseudo);
}
