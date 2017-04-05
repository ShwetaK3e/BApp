package com.bzyness.bzyness.models;



import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;



@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "html_attributions",
        "result",
        "status"
})
public class PlaceDetails {

    @JsonProperty("html_attributions")
    private List<Object> htmlAttributions = null;
    @JsonProperty("result")
    private Result result;
    @JsonProperty("status")
    private String status;


    @JsonProperty("html_attributions")
    public List<Object> getHtmlAttributions() {
        return htmlAttributions;
    }

    @JsonProperty("html_attributions")
    public void setHtmlAttributions(List<Object> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    @JsonProperty("result")
    public Result getResult() {
        return result;
    }

    @JsonProperty("result")
    public void setResult(Result result) {
        this.result = result;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "address_components",
            "adr_address",
            "formatted_address",
            "formatted_phone_number",
            "geometry",
            "icon",
            "id",
            "international_phone_number",
            "name",
            "place_id",
            "scope",
            "alt_ids",
            "rating",
            "reference",
            "reviews",
            "types",
            "url",
            "vicinity",
            "website",
            "opening_hours",
            "permanently_closed",
            "photos",
            "price_level",
            "utc_offset"
    })
    public static class Result {

        @JsonProperty("address_components")
        private List<AddressComponent> addressComponents = null;
        @JsonProperty("adr_address")
        private String adrAddress;
        @JsonProperty("formatted_address")
        private String formattedAddress;
        @JsonProperty("formatted_phone_number")
        private String formattedPhoneNumber;
        @JsonProperty("geometry")
        private Geometry geometry;
        @JsonProperty("icon")
        private String icon;
        @JsonProperty("id")
        private String id;
        @JsonProperty("international_phone_number")
        private String internationalPhoneNumber;
        @JsonProperty("name")
        private String name;
        @JsonProperty("place_id")
        private String placeId;
        @JsonProperty("scope")
        private String scope;
        @JsonProperty("alt_ids")
        private List<AltId> altIds = null;
        @JsonProperty("rating")
        private Double rating;
        @JsonProperty("reference")
        private String reference;
        @JsonProperty("reviews")
        private List<Review> reviews = null;
        @JsonProperty("types")
        private List<String> types = null;
        @JsonProperty("url")
        private String url;
        @JsonProperty("vicinity")
        private String vicinity;
        @JsonProperty("website")
        private String website;
        @JsonProperty("opening_hours")
        private OpeningHours opening_hours;
        @JsonProperty("permanently_closed")
        private boolean permanently_closed;
        @JsonProperty("price_level")
        private int price_level;
        @JsonProperty("photos")
        private List<Photo> photos = null;
        @JsonProperty("utc_offset")
        private String utc_offset;


        @JsonProperty("address_components")
        public List<AddressComponent> getAddressComponents() {
            return addressComponents;
        }

        @JsonProperty("address_components")
        public void setAddressComponents(List<AddressComponent> addressComponents) {
            this.addressComponents = addressComponents;
        }

        @JsonProperty("adr_address")
        public String getAdrAddress() {
            return adrAddress;
        }

        @JsonProperty("adr_address")
        public void setAdrAddress(String adrAddress) {
            this.adrAddress = adrAddress;
        }

        @JsonProperty("formatted_address")
        public String getFormattedAddress() {
            return formattedAddress;
        }

        @JsonProperty("formatted_address")
        public void setFormattedAddress(String formattedAddress) {
            this.formattedAddress = formattedAddress;
        }

        @JsonProperty("formatted_phone_number")
        public String getFormattedPhoneNumber() {
            return formattedPhoneNumber;
        }

        @JsonProperty("formatted_phone_number")
        public void setFormattedPhoneNumber(String formattedPhoneNumber) {
            this.formattedPhoneNumber = formattedPhoneNumber;
        }

        @JsonProperty("geometry")
        public Geometry getGeometry() {
            return geometry;
        }

        @JsonProperty("geometry")
        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }

        @JsonProperty("icon")
        public String getIcon() {
            return icon;
        }

        @JsonProperty("icon")
        public void setIcon(String icon) {
            this.icon = icon;
        }

        @JsonProperty("id")
        public String getId() {
            return id;
        }

        @JsonProperty("id")
        public void setId(String id) {
            this.id = id;
        }

        @JsonProperty("international_phone_number")
        public String getInternationalPhoneNumber() {
            return internationalPhoneNumber;
        }

        @JsonProperty("international_phone_number")
        public void setInternationalPhoneNumber(String internationalPhoneNumber) {
            this.internationalPhoneNumber = internationalPhoneNumber;
        }

        @JsonProperty("name")
        public String getName() {
            return name;
        }

        @JsonProperty("name")
        public void setName(String name) {
            this.name = name;
        }

        @JsonProperty("place_id")
        public String getPlaceId() {
            return placeId;
        }

        @JsonProperty("place_id")
        public void setPlaceId(String placeId) {
            this.placeId = placeId;
        }

        @JsonProperty("scope")
        public String getScope() {
            return scope;
        }

        @JsonProperty("scope")
        public void setScope(String scope) {
            this.scope = scope;
        }

        @JsonProperty("alt_ids")
        public List<AltId> getAltIds() {
            return altIds;
        }

        @JsonProperty("alt_ids")
        public void setAltIds(List<AltId> altIds) {
            this.altIds = altIds;
        }

        @JsonProperty("rating")
        public Double getRating() {
            return rating;
        }

        @JsonProperty("rating")
        public void setRating(Double rating) {
            this.rating = rating;
        }

        @JsonProperty("reference")
        public String getReference() {
            return reference;
        }

        @JsonProperty("reference")
        public void setReference(String reference) {
            this.reference = reference;
        }

        @JsonProperty("reviews")
        public List<Review> getReviews() {
            return reviews;
        }

        @JsonProperty("reviews")
        public void setReviews(List<Review> reviews) {
            this.reviews = reviews;
        }

        @JsonProperty("types")
        public List<String> getTypes() {
            return types;
        }

        @JsonProperty("types")
        public void setTypes(List<String> types) {
            this.types = types;
        }

        @JsonProperty("url")
        public String getUrl() {
            return url;
        }

        @JsonProperty("url")
        public void setUrl(String url) {
            this.url = url;
        }

        @JsonProperty("vicinity")
        public String getVicinity() {
            return vicinity;
        }

        @JsonProperty("vicinity")
        public void setVicinity(String vicinity) {
            this.vicinity = vicinity;
        }

        @JsonProperty("website")
        public String getWebsite() {
            return website;
        }

        @JsonProperty("website")
        public void setWebsite(String website) {
            this.website = website;
        }


        @JsonProperty("opening_hours")
        public OpeningHours getOpening_hours() {
            return opening_hours;
        }

        @JsonProperty("opening_hours")
        public void setOpening_hours(OpeningHours opening_hours) {
            this.opening_hours= opening_hours;
        }

        @JsonProperty("photos")
        public List<Photo> getPhotos() {
            return photos;
        }

        @JsonProperty("photos")
        public void setPhotos(List<Photo> photos) {
            this.photos= photos;
        }


        @JsonProperty("permanently_closed")
        public boolean getPermanentlyClosed() {
            return permanently_closed;
        }

        @JsonProperty("permanently_closed")
        public void setPermanentlyClosed(boolean permanently_closed ){
            this.permanently_closed= permanently_closed;
        }


        @JsonProperty("price_level")
        public int getPriceLevel() {
            return price_level;
        }

        @JsonProperty("price_level")
        public void setPriceLevel(int price_level ){
            this.price_level= price_level;
        }



    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "html_attributions",
            "height",
            "width",
            "photo_reference"
    })
    public static class Photo {

        @JsonProperty("html_attributions")
        private List<Object> htmlAttributions = null;
        @JsonProperty("height")
        private Integer height;
        @JsonProperty("width")
        private Integer width;
        @JsonProperty("photo_reference")
        private String photoReference;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("html_attributions")
        public List<Object> getHtmlAttributions() {
            return htmlAttributions;
        }

        @JsonProperty("html_attributions")
        public void setHtmlAttributions(List<Object> htmlAttributions) {
            this.htmlAttributions = htmlAttributions;
        }

        @JsonProperty("height")
        public Integer getHeight() {
            return height;
        }

        @JsonProperty("height")
        public void setHeight(Integer height) {
            this.height = height;
        }

        @JsonProperty("width")
        public Integer getWidth() {
            return width;
        }

        @JsonProperty("width")
        public void setWidth(Integer width) {
            this.width = width;
        }

        @JsonProperty("photo_reference")
        public String getPhotoReference() {
            return photoReference;
        }

        @JsonProperty("photo_reference")
        public void setPhotoReference(String photoReference) {
            this.photoReference = photoReference;
        }
    }





    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "open_now",
            "periods",
            "weekday_text",
            "exceptional_date"
    })
    public static class OpeningHours {

        @JsonProperty("open_now")
        private boolean open_now;
        @JsonProperty("periods")
        private List<Period> periods;
        @JsonProperty("weekday_text")
        private List<String> weekday_text = null;
        @JsonProperty("exceptional_date")
        private String exceptional_date = null;



        @JsonProperty("open_now")
        public boolean getOpenNow() {
            return open_now;
        }

        @JsonProperty("open_now")
        public void setOpenNow(boolean open_now) {
            this.open_now = open_now;
        }

        @JsonProperty("periods")
        public List<Period> getPeriods() {
            return periods;
        }

        @JsonProperty("periods")
        public void setPeriods(List<Period> periods) {
            this.periods = periods;
        }

        @JsonProperty("weekday_text")
        public List<String> getweekdayText() {
            return weekday_text;
        }

        @JsonProperty("weekday_text")
        public void setWeekdayText(List<String> weekday_text) {
            this.weekday_text = weekday_text;
        }

        @JsonProperty("exceptional_date")
        public String  getExceptionalDate() {
            return exceptional_date;
        }

        @JsonProperty("exceptional_date")
        public void setExceptionalDate(String exceptional_date) {
            this.exceptional_date = exceptional_date;
        }

    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "open",
            "close",
    })
    public static class Period {

        @JsonProperty("open")
        private Open open;
        @JsonProperty("close")
        private Close close;



        @JsonProperty("open")
        public Open getOpen() {
            return open;
        }

        @JsonProperty("open")
        public void setOpen(Open open) {
            this.open = open;
        }


        @JsonProperty("close")
        public Close getClose() {
            return close;
        }

        @JsonProperty("close")
        public void setClose(Close close) {
            this.close = close;
        }
    }



    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "day",
            "time",
    })
    public static class Open {

        @JsonProperty("day")
        private int day;
        @JsonProperty("time")
        private String time;



        @JsonProperty("day")
        public int getDay() {
            return day;
        }

        @JsonProperty("day")
        public void setday(int day) {
            this.day = day;
        }


        @JsonProperty("time")
        public String  getTime() {
            return time;
        }

        @JsonProperty("time")
        public void setTime(String time) {
            this.time = time;
        }
    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "day",
            "time",
    })
    public static class Close {

        @JsonProperty("day")
        private int day;
        @JsonProperty("time")
        private String time;


        @JsonProperty("day")
        public int getDay() {
            return day;
        }

        @JsonProperty("day")
        public void setday(int day) {
            this.day = day;
        }


        @JsonProperty("time")
        public String  getTime() {
            return time;
        }

        @JsonProperty("time")
        public void setTime(String time) {
            this.time = time;
        }
    }



    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "long_name",
            "short_name",
            "types"
    })
    public  static class AddressComponent {

        @JsonProperty("long_name")
        private String longName;
        @JsonProperty("short_name")
        private String shortName;
        @JsonProperty("types")
        private List<String> types = null;


        @JsonProperty("long_name")
        public String getLongName() {
            return longName;
        }

        @JsonProperty("long_name")
        public void setLongName(String longName) {
            this.longName = longName;
        }

        @JsonProperty("short_name")
        public String getShortName() {
            return shortName;
        }

        @JsonProperty("short_name")
        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        @JsonProperty("types")
        public List<String> getTypes() {
            return types;
        }

        @JsonProperty("types")
        public void setTypes(List<String> types) {
            this.types = types;
        }
    }





    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "place_id",
            "scope"
    })
    public static class AltId {

        @JsonProperty("place_id")
        private String placeId;
        @JsonProperty("scope")
        private String scope;


        @JsonProperty("place_id")
        public String getPlaceId() {
            return placeId;
        }

        @JsonProperty("place_id")
        public void setPlaceId(String placeId) {
            this.placeId = placeId;
        }

        @JsonProperty("scope")
        public String getScope() {
            return scope;
        }

        @JsonProperty("scope")
        public void setScope(String scope) {
            this.scope = scope;
        }
    }




    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "aspects",
            "author_name",
            "author_url",
            "language",
            "rating",
            "text",
            "time",
            "profile_photo_url",
            "relative_time_description"
    })
    public static class Review {

        @JsonProperty("aspects")
        private List<Aspect> aspects = null;
        @JsonProperty("author_name")
        private String authorName;
        @JsonProperty("author_url")
        private String authorUrl;
        @JsonProperty("language")
        private String language;
        @JsonProperty("rating")
        private Integer rating;
        @JsonProperty("text")
        private String text;
        @JsonProperty("time")
        private Integer time;
        @JsonProperty("profile_photo_url")
        private String profile_photo_url;
        @JsonProperty("relative_time_description")
        private String relative_time_description;


        @JsonProperty("aspects")
        public List<Aspect> getAspects() {
            return aspects;
        }

        @JsonProperty("aspects")
        public void setAspects(List<Aspect> aspects) {
            this.aspects = aspects;
        }

        @JsonProperty("author_name")
        public String getAuthorName() {
            return authorName;
        }

        @JsonProperty("author_name")
        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }

        @JsonProperty("author_url")
        public String getAuthorUrl() {
            return authorUrl;
        }

        @JsonProperty("author_url")
        public void setAuthorUrl(String authorUrl) {
            this.authorUrl = authorUrl;
        }

        @JsonProperty("language")
        public String getLanguage() {
            return language;
        }

        @JsonProperty("language")
        public void setLanguage(String language) {
            this.language = language;
        }

        @JsonProperty("rating")
        public Integer getRating() {
            return rating;
        }

        @JsonProperty("rating")
        public void setRating(Integer rating) {
            this.rating = rating;
        }

        @JsonProperty("text")
        public String getText() {
            return text;
        }

        @JsonProperty("text")
        public void setText(String text) {
            this.text = text;
        }

        @JsonProperty("time")
        public Integer getTime() {
            return time;
        }

        @JsonProperty("time")
        public void setTime(Integer time) {
            this.time = time;
        }
    }





    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "location",
            "viewport"
    })
    public static class Geometry {

        @JsonProperty("location")
        private Location location;
        @JsonProperty("viewport")
        private Viewport viewport;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("location")
        public Location getLocation() {
            return location;
        }

        @JsonProperty("location")
        public void setLocation(Location location) {
            this.location = location;
        }

        @JsonProperty("viewport")
        public Viewport getViewport() {
            return viewport;
        }

        @JsonProperty("viewport")
        public void setViewport(Viewport viewport) {
            this.viewport = viewport;
        }

    }





    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "lat",
            "lng"
    })
    public static class Location {

        @JsonProperty("lat")
        private Double lat;
        @JsonProperty("lng")
        private Double lng;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("lat")
        public Double getLat() {
            return lat;
        }

        @JsonProperty("lat")
        public void setLat(Double lat) {
            this.lat = lat;
        }

        @JsonProperty("lng")
        public Double getLng() {
            return lng;
        }

        @JsonProperty("lng")
        public void setLng(Double lng) {
            this.lng = lng;
        }
    }




    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "northeast",
            "southwest"
    })
    public static  class Viewport {

        @JsonProperty("northeast")
        private Northeast northeast;
        @JsonProperty("southwest")
        private Southwest southwest;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("northeast")
        public Northeast getNortheast() {
            return northeast;
        }

        @JsonProperty("northeast")
        public void setNortheast(Northeast northeast) {
            this.northeast = northeast;
        }

        @JsonProperty("southwest")
        public Southwest getSouthwest() {
            return southwest;
        }

        @JsonProperty("southwest")
        public void setSouthwest(Southwest southwest) {
            this.southwest = southwest;
        }

    }





    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "lat",
            "lng"
    })
    public static class Southwest {

        @JsonProperty("lat")
        private Double lat;
        @JsonProperty("lng")
        private Double lng;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("lat")
        public Double getLat() {
            return lat;
        }

        @JsonProperty("lat")
        public void setLat(Double lat) {
            this.lat = lat;
        }

        @JsonProperty("lng")
        public Double getLng() {
            return lng;
        }

        @JsonProperty("lng")
        public void setLng(Double lng) {
            this.lng = lng;
        }
    }



    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "lat",
            "lng"
    })
    public static class Northeast {

        @JsonProperty("lat")
        private Double lat;
        @JsonProperty("lng")
        private Double lng;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("lat")
        public Double getLat() {
            return lat;
        }

        @JsonProperty("lat")
        public void setLat(Double lat) {
            this.lat = lat;
        }

        @JsonProperty("lng")
        public Double getLng() {
            return lng;
        }

        @JsonProperty("lng")
        public void setLng(Double lng) {
            this.lng = lng;
        }
    }




    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "rating",
            "type"
    })
    public static class Aspect {

        @JsonProperty("rating")
        private Integer rating;
        @JsonProperty("type")
        private String type;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("rating")
        public Integer getRating() {
            return rating;
        }

        @JsonProperty("rating")
        public void setRating(Integer rating) {
            this.rating = rating;
        }

        @JsonProperty("type")
        public String getType() {
            return type;
        }

        @JsonProperty("type")
        public void setType(String type) {
            this.type = type;
        }
    }

}