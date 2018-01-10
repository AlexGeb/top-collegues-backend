package dta.api.beans;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import dta.api.entities.Collegue;
import dta.api.models.GithubUser;
import dta.api.repository.CollegueRepository;
import dta.api.services.GithubService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Component
public class ApplicationStartUpBean {
	@Autowired
	private CollegueRepository collRepo;

	@EventListener(ApplicationReadyEvent.class)
	public void initDatabase() {
		HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
		// set your desired log level
		logging.setLevel(Level.BASIC);
		OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
		httpClient.addInterceptor(logging);

		Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/")
				.addConverterFactory(JacksonConverterFactory.create()).client(httpClient.build()).build();
		GithubService service = retrofit.create(GithubService.class);

		Arrays.asList("AlexGeb", "MAWAAW","rbonnamy","thienban","Melodie44","Tagpower","Kazekitai","AssiaTrabelsi","roddet").forEach(pseudo -> {
			Call<GithubUser> callAsync = service.getUserInfo(pseudo);
			callAsync.enqueue(new Callback<GithubUser>() {

				@Override
				public void onResponse(Call<GithubUser> call, Response<GithubUser> response) {
					GithubUser user = response.body();
					if (user != null) {
						if (!collRepo.findByPseudo(user.getLogin()).isPresent()) {
							Collegue col = new Collegue();
							col.setImageUrl(user.getAvatar_url());
							col.setPseudo(user.getLogin());
							col.setName(user.getName());
							collRepo.save(col);
						}
					}
				}

				@Override
				public void onFailure(Call<GithubUser> call, Throwable throwable) {
					System.out.println(throwable);
				}
			});
		});

	}

}
