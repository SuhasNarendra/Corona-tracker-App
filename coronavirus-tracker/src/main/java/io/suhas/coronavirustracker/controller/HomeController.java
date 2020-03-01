package io.suhas.coronavirustracker.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import io.suhas.coronavirustracker.models.LocationStats;
import io.suhas.coronavirustracker.service.CoronaVirusDataService;

@Controller
public class HomeController {

	@Autowired
	private CoronaVirusDataService dataservice;

	@GetMapping(path = "/home")
	public String home(Model model) {

		List<LocationStats> allStats = dataservice.getAllStats();
		int totalReportedCases = allStats.stream().mapToInt(x -> x.getCurrent_date_count()).sum();
		int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();

		model.addAttribute("locationStats", allStats);
		model.addAttribute("totalReportedCases", totalReportedCases);
		model.addAttribute("totalNewCases", totalNewCases);
		return "home";
	}
}
