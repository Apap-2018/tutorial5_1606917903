package com.apap.tutorial5.service;

import java.util.List;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;

/**
 * FlightService
 * @author nasya
 */
public interface FlightService {
	void addFlight(FlightModel flight);
	void deleteFlight(FlightModel flight);
	void deleteFlightById(long id);
	FlightModel getFlightById(long id);
	List<FlightModel> getFlightList();
}
