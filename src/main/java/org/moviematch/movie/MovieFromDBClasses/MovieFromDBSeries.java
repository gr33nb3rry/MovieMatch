package org.moviematch.movie.MovieFromDBClasses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

public class MovieFromDBSeries {
    @JsonProperty("id")
    private int id;

    @JsonProperty("url")
    private String url;

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private String type;

    @JsonProperty("language")
    private String language;

    @JsonProperty("genres")
    private List<String> genres;

    @JsonProperty("status")
    private String status;

    @JsonProperty("runtime")
    private int runtime;

    @JsonProperty("averageRuntime")
    private int averageRuntime;

    @JsonProperty("premiered")
    private String premiered;

    @JsonProperty("ended")
    private LocalDate ended;

    @JsonProperty("officialSite")
    private String officialSite;

    @JsonProperty("schedule")
    private Schedule schedule;

    @JsonProperty("rating")
    private Rating rating;

    @JsonProperty("weight")
    private int weight;

    @JsonProperty("network")
    private Network network;

    @JsonProperty("webChannel")
    private WebChannel webChannel;

    @JsonProperty("dvdCountry")
    private String dvdCountry;

    @JsonProperty("externals")
    private Externals externals;

    @JsonProperty("image")
    private Image image;

    @JsonProperty("summary")
    private String summary;

    @JsonProperty("updated")
    private long updated;

    @JsonProperty("_links")
    private Links _links;

    public MovieFromDBSeries() {
    }

    public MovieFromDBSeries(int id, String url, String name, String type, String language, List<String> genres, String status, int runtime, int averageRuntime, String premiered, LocalDate ended, String officialSite, Schedule schedule, Rating rating, int weight, Network network, WebChannel webChannel, String dvdCountry, Externals externals, Image image, String summary, long updated, Links _links) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.type = type;
        this.language = language;
        this.genres = genres;
        this.status = status;
        this.runtime = runtime;
        this.averageRuntime = averageRuntime;
        this.premiered = premiered;
        this.ended = ended;
        this.officialSite = officialSite;
        this.schedule = schedule;
        this.rating = rating;
        this.weight = weight;
        this.network = network;
        this.webChannel = webChannel;
        this.dvdCountry = dvdCountry;
        this.externals = externals;
        this.image = image;
        this.summary = summary;
        this.updated = updated;
        this._links = _links;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public int getAverageRuntime() {
        return averageRuntime;
    }

    public void setAverageRuntime(int averageRuntime) {
        this.averageRuntime = averageRuntime;
    }

    public String getPremiered() {
        return premiered;
    }

    public void setPremiered(String premiered) {
        this.premiered = premiered;
    }

    public LocalDate getEnded() {
        return ended;
    }

    public void setEnded(LocalDate ended) {
        this.ended = ended;
    }

    public String getOfficialSite() {
        return officialSite;
    }

    public void setOfficialSite(String officialSite) {
        this.officialSite = officialSite;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public WebChannel getWebChannel() {
        return webChannel;
    }

    public void setWebChannel(WebChannel webChannel) {
        this.webChannel = webChannel;
    }

    public String getDvdCountry() {
        return dvdCountry;
    }

    public void setDvdCountry(String dvdCountry) {
        this.dvdCountry = dvdCountry;
    }

    public Externals getExternals() {
        return externals;
    }

    public void setExternals(Externals externals) {
        this.externals = externals;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public Links get_links() {
        return _links;
    }

    public void set_links(Links _links) {
        this._links = _links;
    }
}

