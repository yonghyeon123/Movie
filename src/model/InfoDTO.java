package model;

public class InfoDTO {
    private int id;
    private int movieId;
    private int theaterId;
    private String screenTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(int theaterId) {
        this.theaterId = theaterId;
    }

    public String getScreenTime() {
        return screenTime;
    }

    public void setScreenTime(String screenTime) {
        this.screenTime = screenTime;
    }

    public boolean equals(Object o){
        if(o instanceof InfoDTO){
            InfoDTO infoDTO = (InfoDTO) o;
            return id == infoDTO.id;
        }

        return false;
    }
}
