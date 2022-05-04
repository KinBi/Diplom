package com.monkeybusiness.core.model.service;

import com.monkeybusiness.core.model.eventCalendar.EventCalendar;

import java.util.List;

public interface EventCalendarService {
  EventCalendar getEventCalendar(Long id);

  List<EventCalendar> getAllEventCalendars();

  List<EventCalendar> getEventCalendarsByPracticeId(Long practiceId);

  void save(EventCalendar document);

  void update(EventCalendar document);

  void delete(Long id);
}
