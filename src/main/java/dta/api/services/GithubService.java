package dta.api.services;

import dta.api.models.GithubUser;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GithubService {
	  @GET("users/{pseudo}")
	  Call<GithubUser> getUserInfo(@Path("pseudo") String pseudo,@Query("access_token") String github_user_access);
}
