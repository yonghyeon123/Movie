package model;

public class TheaterDTO {
    private int id;
    private String theaterName;
    private String location;
    private String number;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean equals(Object o){
        if(o instanceof TheaterDTO){
            TheaterDTO theaterDTO = (TheaterDTO) o;
            return id == theaterDTO.id;
        }

        return false;
    }
}
