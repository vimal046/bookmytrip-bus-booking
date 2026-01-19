package com.bookmytrip.service;

import java.time.LocalDate;
import java.util.List;

import com.bookmytrip.dto.ScheduleDTO;
import com.bookmytrip.model.Schedule;

public interface ScheduleService {

	Schedule createSchedule(ScheduleDTO dto);

	Schedule updateSchedule(Long id,ScheduleDTO dto);

	void deleteSchedule(Long id);

	Schedule getScheduleById(Long id);

	List<Schedule> getAllSchedules();

	List<Schedule> getSchedulesByBus(Long busId);

	List<Schedule> searchSchedules(String source,String destination,LocalDate date);
}
