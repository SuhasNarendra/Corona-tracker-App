package io.suhas.coronavirustracker.service;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import io.suhas.coronavirustracker.models.LocationStats;

@Service
public class CoronaVirusDataService {

	private String URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";

	private List<LocationStats> allStats = new ArrayList<>();

	public List<LocationStats> getAllStats() {
		return allStats;
	}

	public void setAllStats(List<LocationStats> allStats) {
		this.allStats = allStats;
	}

	@PostConstruct
	@Scheduled(cron = "* * 1 * * *")
	public void fetchdata() throws IOException, InterruptedException {

		List<LocationStats> newStats = new ArrayList<>();

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		// System.out.println(response.body());

		StringReader csvbodyreader = new StringReader(response.body());
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvbodyreader);

		for (CSVRecord csvRecord : records) {

			LocationStats stats = new LocationStats();
			stats.setState(csvRecord.get("Province/State"));
			stats.setCountry(csvRecord.get("Country/Region"));
			int latestCases = Integer.parseInt(csvRecord.get(csvRecord.size() - 1));
			int prevDayCases = Integer.parseInt(csvRecord.get(csvRecord.size() - 2));
			stats.setCurrent_date_count(latestCases);
			stats.setDiffFromPrevDay(latestCases - prevDayCases);

			newStats.add(stats);
		}

		this.allStats = newStats;
	}
}
