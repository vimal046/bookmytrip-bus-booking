package com.bookmytrip.service;

import java.util.List;

import com.bookmytrip.dto.RouteDTO;
import com.bookmytrip.model.Route;

public interface RouteService {
	
	Route createRoute(RouteDTO dto);

	Route updateRout(Long id,RouteDTO dto);

	void deleteRoute(Long id);

	Route getRouteById(Long id);

	List<Route> getAllRoutes();

	List<Route> getRouteBySourceAndDestination(String source,String destination);
}
