package com.bookmytrip.service;

import java.util.List;

import com.bookmytrip.dto.BusDTO;
import com.bookmytrip.model.Bus;

public interface BusService {

	Bus createBus(BusDTO dto);

	Bus updateBus(Long id, BusDTO dto);

	void deleteBus(Long id);
	
	Bus getBusById(Long id);

	List<Bus> getAllBuses();

	List<Bus> getBusesByRoute(Long routeId);

	List<Bus> searchBuses(String source, String destination);

	List<Bus> getBusesWithAllRoutes();
}
