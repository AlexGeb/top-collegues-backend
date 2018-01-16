package dta.api.services;

import org.springframework.stereotype.Service;

@Service
public class BackendAvailableService {
	private boolean ready = false;

	public boolean isBackendReady() {
		return ready;
	}

	public void setIsReady(boolean isready) {
		ready = isready;
	}
}
