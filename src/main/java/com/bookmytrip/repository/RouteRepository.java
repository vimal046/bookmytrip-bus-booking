package com.bookmytrip.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookmytrip.model.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

	List<Route> findBySourceAndDestination(String source,String destination);

	List<Route> findBySource(String source);

	List<Route> findByDestination(String destination);

}
