package com.bookmytrip.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookmytrip.dto.BusDTO;
import com.bookmytrip.enums.BusStatus;
import com.bookmytrip.enums.BusType;
import com.bookmytrip.exception.ResourceNotFoundException;
import com.bookmytrip.model.Bus;
import com.bookmytrip.model.Route;
import com.bookmytrip.repository.BusRepository;
import com.bookmytrip.repository.RouteRepository;
import com.bookmytrip.service.BusService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BusServiceImpl implements BusService {

	private final BusRepository busRepository;
	private final RouteRepository routeRepository;

	@Override
	public Bus createBus(BusDTO dto) {
		log.info("Creating new bus: {}", dto.getBusNumber());

		Route route = routeRepository.findById(dto.getRouteId())
				.orElseThrow(() -> new ResourceNotFoundException("Route not found with id: " + dto.getRouteId()));

		Bus bus = Bus.builder()
				.busNumber(dto.getBusNumber())
				.busType(BusType.valueOf(dto.getBusType()))
				.totalSeats(dto.getTotalSeats())
				.status(BusStatus.ACTIVE)
				.route(route)
				.build();

		Bus savedBus = busRepository.save(bus);
		log.info("Bus created successfully: {}", savedBus.getBusNumber());
		return savedBus;
	}

	@Override
	public Bus updateBus(Long id,
			BusDTO dto) {
		log.info("Updating bus: {}", id);

		Bus bus = getBusById(id);
		Route route = routeRepository.findById(dto.getRouteId())
				.orElseThrow(() -> new ResourceNotFoundException("Route not found with id: " + dto.getRouteId()));

		bus.setBusNumber(dto.getBusNumber());
		bus.setBusType(BusType.valueOf(dto.getBusType()));
		bus.setTotalSeats(dto.getTotalSeats());
		bus.setRoute(route);

		Bus updatedBus = busRepository.save(bus);
		log.info("Bus updated successfully: {}", id);
		return updatedBus;
	}

	@Override
	public void deleteBus(Long id) {
		Bus bus = getBusById(id);
		busRepository.delete(bus);
		log.info("Bus deleted: {}", id);
	}

	@Override
	@Transactional(readOnly = true)
	public Bus getBusById(Long id) {
		return busRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Bus not found with id: " + id));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Bus> getAllBuses() {
		return busRepository.findAll();
	}
	
	public List<Bus> getBusesWithAllRoutes(){
		return busRepository.findAllWithRoutes();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Bus> getBusesByRoute(Long routeId) {
		Route route = routeRepository.findById(routeId)
				.orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + routeId));
		return busRepository.findByRoute(route);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Bus> searchBuses(String source,String destination) {
		return busRepository.findActiveSourceAndDestination(source, destination);
	}

}
