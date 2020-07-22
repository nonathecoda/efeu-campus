package de.fzi.efeu.service;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.fzi.efeu.efeuportal.ApiException;
import de.fzi.efeu.efeuportal.api.ContactApi;
import de.fzi.efeu.efeuportal.model.EfCaContact;
import de.fzi.efeu.efeuportal.model.EfCaContactResp;
import de.fzi.efeu.efeuportal.model.EfCaDateTimeSlot;
import de.fzi.efeu.efeuportal.model.EfCaOrder;
import de.fzi.efeu.efeuportal.model.EfCaSlotsPerDay;
import de.fzi.efeu.efeuportal.model.EfCaTimeSlot;

@Service
public class TimeSlotService {

    @Autowired
    private ContactApi contactApi;

    public EfCaOrder setBoxOrderPickupTimeSlotsFromContact(final EfCaOrder order) throws ParseException, ApiException {
        EfCaContactResp pickupContact = contactApi.findContactsByFinder(
                new EfCaContact().ident(order.getPickup().getStorageIds().getContactId()));
        order.setPickupTimeSlots(createTimeSlotsFromContact(
                pickupContact.getContacts().iterator().next()));
        return order;
    }

    public EfCaOrder setBoxOrderDeliveryTimeSlotsFromContact(final EfCaOrder order) throws ParseException, ApiException {
        EfCaContactResp deliveryContact = contactApi.findContactsByFinder(
                new EfCaContact().ident(order.getDelivery().getStorageIds().getContactId()));
        order.setDeliveryTimeSlots(createTimeSlotsFromContact(
                deliveryContact.getContacts().iterator().next()));
        return order;
    }

    public EfCaOrder setBoxOrderTimeSlotsFromContacts(final EfCaOrder order) throws ParseException, ApiException {
        setBoxOrderDeliveryTimeSlotsFromContact(order);
        setBoxOrderPickupTimeSlotsFromContact(order);
        return order;
    }

    private List<EfCaDateTimeSlot> createTimeSlotsFromContact(final EfCaContact contact) throws ParseException {
        List<EfCaDateTimeSlot> dateTimeSlots = new ArrayList<>();
        OffsetDateTime now = OffsetDateTime.now();
        for (int i = 0; i <= 7; ++i) {
            OffsetDateTime base = now.plusDays(i);
            dateTimeSlots.addAll(createTimeSlotsFromContactAndBase(contact, base));
        }
        return dateTimeSlots;
    }

    private List<EfCaDateTimeSlot> createTimeSlotsFromContactAndBase(final EfCaContact contact,
            final OffsetDateTime base) {
        List<EfCaDateTimeSlot> dateTimeSlots = new ArrayList<>();
        for (EfCaSlotsPerDay s : contact.getAsyncAvailabilityTimeSlots()) {
            if (s.getDay() == base.getDayOfWeek().getValue()) {
                EfCaTimeSlot timeSlot = s.getTimeSlots();
                LocalTime startTime = LocalTime.parse(timeSlot.getStart());
                LocalTime endTime = LocalTime.parse(timeSlot.getEnd());

                OffsetDateTime start = createOffsetDateTimeFromBaseAndTime(base, startTime);
                OffsetDateTime end = createOffsetDateTimeFromBaseAndTime(base, endTime);
                EfCaDateTimeSlot dateTimeSlot = new EfCaDateTimeSlot().start(start).end(end);
                if (end.isAfter(base)) {
                    dateTimeSlots.add(dateTimeSlot);
                }
            }
        }
        return dateTimeSlots;
    }

    private OffsetDateTime createOffsetDateTimeFromBaseAndTime(final OffsetDateTime base, final LocalTime time) {
        return OffsetDateTime.of(
                base.getYear(),
                base.getMonth().getValue(),
                base.getDayOfMonth(),
                time.getHour(),
                time.getMinute(),
                0,
                0,
                base.getOffset());
    }
}
