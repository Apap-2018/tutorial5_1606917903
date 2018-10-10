package com.apap.tutorial5.controller;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.service.FlightService;
import com.apap.tutorial5.service.PilotService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * PilotController
 * @author nasya
 */
@Controller
public class PilotController {
	@Autowired
	private PilotService pilotService;
	
	@Autowired
	private FlightService flightService;
	
	@RequestMapping("/")
	private String home(Model model) {
		model.addAttribute("title", "APAP");
		return "home";
	}
	
	@RequestMapping(value = "/pilot/add", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("pilot", new PilotModel());
		model.addAttribute("title", "APAP | Add Pilot");
		return "addPilot";
	}
	
	@RequestMapping(value = "/pilot/add", method = RequestMethod.POST)
	private String addPilotSubmit(@ModelAttribute PilotModel pilot, Model model) {
		pilotService.addPilot(pilot);
		model.addAttribute("title", "APAP");
		return "add";
	}
	
	/**
	 * View Pilot berdasarkan License Number
	 */
	@RequestMapping(value = "/pilot/view", method = RequestMethod.GET)
	private String viewPilotByLicenseNumber(@RequestParam("licenseNumber") String licenseNumber, Model model) {
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		if(pilot != null) {
			model.addAttribute("pilot", pilot);
			model.addAttribute("title", "APAP | View Pilot");
			return "view-pilot";
		}
		else {
			String errorMessage = "Pilot dengan license number "+licenseNumber+" tidak ditemukan";
			model.addAttribute("errorMessage", errorMessage);
			model.addAttribute("title", "APAP");
			return "error-page";
		}
	}
	
	@RequestMapping(value = "/pilot/delete/{licenseNumber}", method = RequestMethod.GET)
	private String deletePilot(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		pilotService.deletePilot(archive);
		model.addAttribute("title", "APAP");
		return "delete";
	}
	
	@RequestMapping(value = "/pilot/update/{licenseNumber}", method = RequestMethod.GET)
	private String updatePilot(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
		/*PilotModel pilot = new PilotModel();
		pilot.setLicenseNumber(licenseNumber);
		model.addAttribute("pilot", pilot);*/
		model.addAttribute("pilot", pilotService.getPilotDetailByLicenseNumber(licenseNumber));
		model.addAttribute("title", "APAP | Update Pilot");
		return "updatePilot";
	}
	
	@RequestMapping(value = "/pilot/update", method = RequestMethod.POST)
	private String updatePilotSubmit(@ModelAttribute PilotModel pilot, Model model) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(pilot.getLicenseNumber());
		archive.setFlyHour(pilot.getFlyHour());
		archive.setName(pilot.getName());
		pilotService.addPilot(archive);
		model.addAttribute("title", "APAP");
		return "update";
	}

}
