package com.bookmytrip.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookmytrip.dto.RouteDTO;
import com.bookmytrip.exception.ResourceNotFoundException;
import com.bookmytrip.model.Route;
import com.bookmytrip.repository.RouteRepository;
import com.bookmytrip.service.RouteService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RouteServiceImpl implements RouteService {

	private final RouteRepository routeRepository;

	@Override
	public Route createRoute(RouteDTO dto) {
		log.info("Creating new route: {} to {}", dto.getSource(), dto.getDestination());

		Route route = Route.builder()
				.source(dto.getSource())
				.destination(dto.getDestination())
				.distanceKm(dto.getDistanceKm())
				.duration(dto.getDuration())
				.build();

		Route savedRoute = routeRepository.save(route);
		log.info("Route created successfully with id: {}", savedRoute.getId());
		return savedRoute;
	}

	@Override
	public Route updateRout(Long id,
			RouteDTO dto) {
		log.info("Updating route: {}", id);

		Route route = getRouteById(id);
		route.setSource(dto.getSource());
		route.setDestination(dto.getDestination());
		route.setDistanceKm(dto.getDistanceKm());
		route.setDuration(dto.getDuration());

		Route updateRoute=routeRepository.save(route);
		log.info("Route updates successfully: {}",id);
		return updateRoute;
	}

	@Override
	public void deleteRoute(Long id) {
		Route route=getRouteById(id);
		routeRepository.delete(route);
		log.info("Route deleted: {}",id);
	}

	@Override
	@Transactional(readOnly = true)
	public Route getRouteById(Long id) {
		return routeRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Route not found with id: "+id));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Route> getAllRoutes() {
		return routeRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Route> getRouteBySourceAndDestination(String source,String destination) {
		return routeRepository.findBySourceAndDestination(source, destination);
	}

}
