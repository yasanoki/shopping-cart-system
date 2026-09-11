public class User {
    protected String userId;
    protected String username;
    protected String password;
    protected String phoneNumber;
    protected String email;

    public User(String userId, String username, String password, 
               String phoneNumber, String email) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public boolean authenticate(String inputUserId, String inputPassword) {
        return this.userId.equals(inputUserId) && this.password.equals(inputPassword);
    }

    public String getUserId() { 
        return userId; 
    }
    public String getUsername() { 
        return username;
    }
    public String getPhoneNumber() { 
        return phoneNumber; 
    }
    public String getEmail() { 
        return email; 
    }

    public String toFileString() {
        return String.join(",",
            userId,
            username,
            password,
            phoneNumber,
            email
        );
    }
}
