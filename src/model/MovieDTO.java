package model;

public class MovieDTO {
    private int id;
    private String title;
    private String storyLine;
    private int degree;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStoryLine() {
        return storyLine;
    }

    public void setStoryLine(String storyLine) {
        this.storyLine = storyLine;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public boolean equals(Object o){
        if(o instanceof MovieDTO){
            MovieDTO movieDTO = (MovieDTO) o;
            return id == movieDTO.id;
        }

        return false;
    }
}
