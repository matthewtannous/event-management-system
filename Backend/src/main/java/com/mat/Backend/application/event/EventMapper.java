package com.mat.Backend.application.event;

import org.springframework.stereotype.Component;

@Component
public class EventMapper {
    public Event toEntity(EventRequest request) {
        Event event = new Event();
        event.setName(request.name());
        event.setFromTime(request.fromTime());
        event.setToTime(request.toTime());
        event.setTicketPrice(request.ticketPrice());
        event.setLocation(request.location());
        event.setCapacity(request.capacity());
        event.setRegistered(request.registered() == null ? 0 : request.registered());
        return event;
    }

    public EventResponse toResponse(Event event) {
        return new EventResponse(event.getId(), event.getName(), event.getFromTime(), event.getToTime(), event.getTicketPrice(), event.getLocation(), event.getCapacity(), event.getRegistered());
    }

    public void updateEntity(Event event, EventRequest request) {
        event.setName(request.name());
        event.setFromTime(request.fromTime());
        event.setToTime(request.toTime());
        event.setTicketPrice(request.ticketPrice());
        event.setLocation(request.location());
        event.setCapacity(request.capacity());
        event.setRegistered(request.registered() == null ? 0 : request.registered());
    }
}
