package model;

public class RateDTO {
    private int id;
    private int writerId;
    private int movieId;
    private int rating;
    private String review;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWriterId() {
        return writerId;
    }

    public void setWriterId(int writerId) {
        this.writerId = writerId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public boolean equals(Object o){
        if(o instanceof RateDTO){
            RateDTO rateDTO = (RateDTO) o;
            return id == rateDTO.id;
        }

        return false;
    }
}
