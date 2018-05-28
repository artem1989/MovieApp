package ua.inovecs.movieapp.model;

import java.util.List;

public class VideoResponse {

    private List<Video> results;

    public List<Video> getResults() {
        return results;
    }

    public void setResults(List<Video> results) {
        this.results = results;
    }
}
