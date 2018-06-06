package ua.nure.tur.entities;

public class User extends Entity {

    private static final long serialVersionUID = 6138007042347044115L;

    private String userName;

    private String password;

    private double balance;

    private boolean banned;

    private String email;

    private String lang;

    private Role role;

    private Long userProfileId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(Long userProfileId) {
        this.userProfileId = userProfileId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                ", banned=" + banned +
                ", email='" + email + '\'' +
                ", lang='" + lang + '\'' +
                ", role=" + role +
                ", userProfileId=" + userProfileId +
                '}';
    }
}
