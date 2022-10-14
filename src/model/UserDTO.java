package model;

public class UserDTO {
    private int id;
    private String username;
    private String password;
    private String nickname;
    private int grade;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getGrade(){
        return grade;
    }

    public void setGrade(int grade){
        this.grade = grade;
    }

    public boolean equals(Object o){
        if(o instanceof UserDTO){
            UserDTO userDTO = (UserDTO) o;
            return id == userDTO.id;
        }

        return false;
    }
}
