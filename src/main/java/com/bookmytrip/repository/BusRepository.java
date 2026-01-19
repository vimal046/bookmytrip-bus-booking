package com.bookmytrip.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookmytrip.enums.BusStatus;
import com.bookmytrip.model.Bus;
import com.bookmytrip.model.Route;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {

	List<Bus> findByRoute(Route route);

	Optional<Bus> findByBusNumber(String busNumber);

	List<Bus> findByStatus(BusStatus status);

	@Query("SELECT b FROM Bus b WHERE b.route.source = :source AND b.route.destination = :destination AND b.status = 'ACTIVE'")
	List<Bus> findActiveSourceAndDestination(@Param("source") String source,
			@Param("destination") String destination);

	@Query("SELECT b FROM Bus b JOIN FETCH b.route")
	List<Bus> findAllWithRoutes();
}
