package com.example.final_project.ticket;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private Embedded _embedded;

    public Embedded get_embedded() {
        return _embedded;
    }

    public void set_embedded(Embedded _embedded) {
        this._embedded = _embedded;
    }

    public static class Embedded {
        private List<Event> events;

        public List<Event> getEvents() {
            return events;
        }

        public void setEvents(List<Event> events) {
            this.events = events;
        }
    }

    public static class Event {
        private String name;
        private String type;
        private String id;
        private boolean test;
        private String url;
        private String locale;
        private List<Image> images;
        private Sales sales;
        private Dates dates;
        private List<Classification> classifications;
        private String pleaseNote;
        private List<PriceRange> priceRanges;
        private Links _links;
        private EmbeddedVenues _embedded;


        public Event(Dates dates) {
            this.dates = dates;
        }



        public Event(List<PriceRange> priceRanges) {
            this.priceRanges = priceRanges;
        }

        public Event(String name, String id, String url, ArrayList<Image> imglist, Dates dates, ArrayList<PriceRange> priceRangeList) {
       this.name=name;
       this.id=id;
       this.url=url;
       this.images=imglist;
       this.dates=dates;
       this.priceRanges=priceRangeList;
        }

        public Event(String id, String url, ArrayList<Image> imglist,
//                     List<Dates> start,
                     List<PriceRange> priceranges) {
        this.id=id;
        this.url=url;
//        this.dates=getDates();
        this.priceRanges= priceranges;
        this.images=imglist;


        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isTest() {
            return test;
        }

        public void setTest(boolean test) {
            this.test = test;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getLocale() {
            return locale;
        }

        public void setLocale(String locale) {
            this.locale = locale;
        }

        public List<Image> getImages() {
            return images;
        }

        public void setImages(List<Image> images) {
            this.images = images;
        }

        public Sales getSales() {
            return sales;
        }

        public void setSales(Sales sales) {
            this.sales = sales;
        }

        public Dates getDates() {
            return dates;
        }

        public void setDates(Dates dates) {
            this.dates = dates;
        }

        public List<Classification> getClassifications() {
            return classifications;
        }

        public void setClassifications(List<Classification> classifications) {
            this.classifications = classifications;
        }

        public String getPleaseNote() {
            return pleaseNote;
        }

        public void setPleaseNote(String pleaseNote) {
            this.pleaseNote = pleaseNote;
        }

        public List<PriceRange> getPriceRanges() {
            return priceRanges;
        }

        public void setPriceRanges(List<PriceRange> priceRanges) {
            this.priceRanges = priceRanges;
        }

        public Links get_links() {
            return _links;
        }

        public void set_links(Links _links) {
            this._links = _links;
        }

        public EmbeddedVenues get_embedded() {
            return _embedded;
        }

        public void set_embedded(EmbeddedVenues _embedded) {
            this._embedded = _embedded;
        }
    }

    public static class Image {
        private String ratio;
        private String url;
        private int width;
        private int height;
        private boolean fallback;

        public Image(String url) {

        }

        public String getRatio() {
            return ratio;
        }

        public void setRatio(String ratio) {
            this.ratio = ratio;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public boolean isFallback() {
            return fallback;
        }

        public void setFallback(boolean fallback) {
            this.fallback = fallback;
        }
    }

    public static class Sales {
        private Public _public;

        public Public get_public() {
            return _public;
        }

        public void set_public(Public _public) {
            this._public = _public;
        }
    }

    public static class Public {
        private String startDateTime;
        private boolean startTBD;
        private boolean startTBA;
        private String endDateTime;

        public String getStartDateTime() {
            return startDateTime;
        }

        public void setStartDateTime(String startDateTime) {
            this.startDateTime = startDateTime;
        }

        public boolean isStartTBD() {
            return startTBD;
        }

        public void setStartTBD(boolean startTBD) {
            this.startTBD = startTBD;
        }

        public boolean isStartTBA() {
            return startTBA;
        }

        public void setStartTBA(boolean startTBA) {
            this.startTBA = startTBA;
        }

        public String getEndDateTime() {
            return endDateTime;
        }

        public void setEndDateTime(String endDateTime) {
            this.endDateTime = endDateTime;
        }
    }

    public static class Dates {
        private Access access;
        private Start start;
        private End end;
        private String timezone;
        private Status status;
        private boolean spanMultipleDays;

        public Dates(Start startdate) {

        }

        public Access getAccess() {
            return access;
        }

        public void setAccess(Access access) {
            this.access = access;
        }

        public Start getStart() {
            return start;
        }

        public void setStart(Start start) {
            this.start = start;
        }

        public End getEnd() {
            return end;
        }

        public void setEnd(End end) {
            this.end = end;
        }

        public String getTimezone() {
            return timezone;
        }

        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public boolean isSpanMultipleDays() {
            return spanMultipleDays;
        }

        public void setSpanMultipleDays(boolean spanMultipleDays) {
            this.spanMultipleDays = spanMultipleDays;
        }
    }

    public static class Access {
        private String startDateTime;
        private boolean startApproximate;
        private boolean endApproximate;

        public String getStartDateTime() {
            return startDateTime;
        }

        public void setStartDateTime(String startDateTime) {
            this.startDateTime = startDateTime;
        }

        public boolean isStartApproximate() {
            return startApproximate;
        }

        public void setStartApproximate(boolean startApproximate) {
            this.startApproximate = startApproximate;
        }

        public boolean isEndApproximate() {
            return endApproximate;
        }

        public void setEndApproximate(boolean endApproximate) {
            this.endApproximate = endApproximate;
        }
    }

    public static class Start {
        private String localDate;
        private String localTime;
        private String dateTime;
        private boolean dateTBD;
        private boolean dateTBA;
        private boolean timeTBA;
        private boolean noSpecificTime;

        public Start(String start) {
        }


        public String getLocalDate() {
            return localDate;
        }

        public void setLocalDate(String localDate) {
            this.localDate = localDate;
        }

        public String getLocalTime() {
            return localTime;
        }

        public void setLocalTime(String localTime) {
            this.localTime = localTime;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public boolean isDateTBD() {
            return dateTBD;
        }

        public void setDateTBD(boolean dateTBD) {
            this.dateTBD = dateTBD;
        }

        public boolean isDateTBA() {
            return dateTBA;
        }

        public void setDateTBA(boolean dateTBA) {
            this.dateTBA = dateTBA;
        }

        public boolean isTimeTBA() {
            return timeTBA;
        }

        public void setTimeTBA(boolean timeTBA) {
            this.timeTBA = timeTBA;
        }

        public boolean isNoSpecificTime() {
            return noSpecificTime;
        }

        public void setNoSpecificTime(boolean noSpecificTime) {
            this.noSpecificTime = noSpecificTime;
        }
    }

    public static class End {
        private boolean approximate;
        private boolean noSpecificTime;

        public boolean isApproximate() {
            return approximate;
        }

        public void setApproximate(boolean approximate) {
            this.approximate = approximate;
        }

        public boolean isNoSpecificTime() {
            return noSpecificTime;
        }

        public void setNoSpecificTime(boolean noSpecificTime) {
            this.noSpecificTime = noSpecificTime;
        }
    }

    public static class Status {
        private String code;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    public static class Classification {
        private boolean primary;
        private Segment segment;
        private Genre genre;
        private SubGenre subGenre;
        private Type type;
        private SubType subType;
        private boolean family;

        public boolean isPrimary() {
            return primary;
        }

        public void setPrimary(boolean primary) {
            this.primary = primary;
        }

        public Segment getSegment() {
            return segment;
        }

        public void setSegment(Segment segment) {
            this.segment = segment;
        }

        public Genre getGenre() {
            return genre;
        }

        public void setGenre(Genre genre) {
            this.genre = genre;
        }

        public SubGenre getSubGenre() {
            return subGenre;
        }

        public void setSubGenre(SubGenre subGenre) {
            this.subGenre = subGenre;
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public SubType getSubType() {
            return subType;
        }

        public void setSubType(SubType subType) {
            this.subType = subType;
        }

        public boolean isFamily() {
            return family;
        }

        public void setFamily(boolean family) {
            this.family = family;
        }
    }

    public static class Segment {
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Genre {
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class SubGenre {
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Type {
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class SubType {
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class PriceRange {
        private String type;
        private String currency;
        private double min;
        private double max;

        public PriceRange(double min, double max, String type, String currency) {
            this.min=min;
            this.currency= currency;
            this.max = max;
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public double getMin() {
            return min;
        }

        public void setMin(double min) {
            this.min = min;
        }

        public double getMax() {
            return max;
        }

        public void setMax(double max) {
            this.max = max;
        }
    }

    public static class Links {
        private Self self;
        private List<Venue> venues;

        public Self getSelf() {
            return self;
        }

        public void setSelf(Self self) {
            this.self = self;
        }

        public List<Venue> getVenues() {
            return venues;
        }

        public void setVenues(List<Venue> venues) {
            this.venues = venues;
        }
    }

    public static class Self {
        private String href;

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }
    }

    public static class Venue {
        private String href;

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }
    }

    public static class EmbeddedVenues {
        private List<Venue> venues;

        public List<Venue> getVenues() {
            return venues;
        }

        public void setVenues(List<Venue> venues) {
            this.venues = venues;
        }
    }

    public static class EventDetails {
        private String currency;
        private String type;
        private String date;
        private double min;
        private double max;
        private String url;

        public EventDetails(String currency, String type, double min, double max, String url) {
            this.currency = currency;
            this.type = type;
//            this.date = date;
            this.min = min;
            this.max = max;
            this.url = url;
        }

        @NonNull
        @Override
        public String toString() {
            return "currency "+currency +" type "+type +"date "+date;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public double getMin() {
            return min;
        }

        public void setMin(double min) {
            this.min = min;
        }

        public double getMax() {
            return max;
        }

        public void setMax(double max) {
            this.max = max;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}