package com.bookmytrip.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookmytrip.dto.ScheduleDTO;
import com.bookmytrip.exception.ResourceNotFoundException;
import com.bookmytrip.model.Bus;
import com.bookmytrip.model.Schedule;
import com.bookmytrip.repository.BusRepository;
import com.bookmytrip.repository.ScheduleRepository;
import com.bookmytrip.service.ScheduleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

	private final ScheduleRepository scheduleRepository;
	private final BusRepository busRepository;

	@Override
	public Schedule createSchedule(ScheduleDTO dto) {
		log.info("Creating new schedule for bus id: {}", dto.getBusId());

		Bus bus = busRepository.findById(dto.getBusId())
				.orElseThrow(() -> new ResourceNotFoundException("Bus not found with id: " + dto.getBusId()));

		Schedule schedule = Schedule.builder()
				.bus(bus)
				.date(dto.getDate())
				.departureTime(dto.getDepartureTime())
				.arrivalTime(dto.getArrivalTime())
				.fare(dto.getFare())
				.availableSeats(bus.getTotalSeats())
				.build();

		Schedule savedSchedule = scheduleRepository.save(schedule);
		log.info("Schedule created sucessfully with id: {}", savedSchedule.getId());
		return savedSchedule;
	}

	@Override
	public Schedule updateSchedule(Long id,
			ScheduleDTO dto) {
		log.info("Updating schedule: {}", id);

		Schedule schedule = getScheduleById(id);
		Bus bus = busRepository.findById(dto.getBusId())
				.orElseThrow(() -> new ResourceNotFoundException("Bus not found with id: " + dto.getBusId()));

		schedule.setBus(bus);
		schedule.setDate(dto.getDate());
		schedule.setDepartureTime(dto.getDepartureTime());
		schedule.setArrivalTime(dto.getArrivalTime());
		schedule.setFare(dto.getFare());
		
		Schedule updatedSchedule=scheduleRepository.save(schedule);
        log.info("Schedule updated successfully: {}",id);
		return updatedSchedule;
	}

	@Override
	public void deleteSchedule(Long id) {
		Schedule schedule=getScheduleById(id);
        scheduleRepository.delete(schedule);
        log.info("Schedule deleted: {}",id);
	}

	@Override
	@Transactional(readOnly = true)
	public Schedule getScheduleById(Long id) {
		return scheduleRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Schedule not found with id: "+id));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Schedule> getAllSchedules() {
		return scheduleRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Schedule> getSchedulesByBus(Long busId) {
		Bus bus=busRepository.findById(busId)
		.orElseThrow(()->new ResourceNotFoundException("Bus not found with id: "+busId));
		return scheduleRepository.findByBus(bus);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Schedule> searchSchedules(String source,String destination,LocalDate date) {
		return scheduleRepository.findAvailableSchedules(source, destination, date);
	}

}
