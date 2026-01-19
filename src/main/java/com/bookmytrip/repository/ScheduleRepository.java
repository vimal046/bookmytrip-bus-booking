package com.bookmytrip.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookmytrip.model.Bus;
import com.bookmytrip.model.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

	List<Schedule> findByBus(Bus bus);

	List<Schedule> findByDate(LocalDate date);

	@Query("SELECT s FROM Schedule s WHERE s.bus.id = :busId AND s.date = :date")
	Optional<Schedule> findByBusIdAndDate(@Param("busId") Long busId,
			@Param("date") LocalDate date);

	@Query("SELECT s FROM Schedule s " + "WHERE s.bus.route.source = :source "
			+ "AND s.bus.route.destination = :destination " + "AND s.date = :date " + "AND s.availableSeats > 0")
	List<Schedule> findAvailableSchedules(@Param("source") String source,
			@Param("destination") String destination,
			@Param("date") LocalDate date);

}
