package dta.api.beans;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import dta.api.entities.Collegue;
import dta.api.models.GithubUser;
import dta.api.repository.CollegueRepository;
import dta.api.services.BackendAvailableService;
import dta.api.services.GithubService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

@Component
public class ApplicationStartUpBean {
	@Autowired
	private CollegueRepository collRepo;

	@Autowired
	BackendAvailableService availableService;
 
	@EventListener(ApplicationReadyEvent.class)
	public void initDatabase() {
		HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
		// set your desired log level
		logging.setLevel(Level.BASIC);
		OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
		httpClient.addInterceptor(logging);

		Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/")
				.addConverterFactory(JacksonConverterFactory.create())
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(httpClient.build()).build();
		GithubService service = retrofit.create(GithubService.class);

		String github_user_access = System.getenv("github_user_token");
		Observable.from(getObservableList(service, github_user_access))
				.flatMap(task -> task.observeOn(Schedulers.computation())).subscribe((user) -> {
					availableService.setIsReady(true);
				});
	}

	private List<Observable<GithubUser>> getObservableList(GithubService service, String github_user_access) {
		return Arrays.asList("AlexGeb", "MAWAAW", "rbonnamy", "thienban", "Melodie44", "Tagpower", "Kazekitai",
				"AssiaTrabelsi", "roddet").stream().map(pseudo -> {
					return service.getUserInfo(pseudo, github_user_access).doOnNext(userInfo -> {
						if (!collRepo.findByPseudo(userInfo.getLogin()).isPresent()) {
							collRepo.save(mapGithubUserToCollegue(userInfo));
						}
					});
				}).collect(Collectors.toList());
	}

	private Collegue mapGithubUserToCollegue(GithubUser user) {
		Collegue col = new Collegue();
		col.setImageUrl(user.getAvatar_url());
		col.setPseudo(user.getLogin());
		col.setName(user.getName());
		return col;
	}

}
