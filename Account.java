public class Account {
    private String name;
    private String username;
    private String password;
    private String category;

    public Account(String name, String username, String password, String category) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.category = category;
    }

    // Getters and Setters i f  get them right. 
    public String getName() { return name; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getCategory() { return category; }

    public void setName(String name) { this.name = name; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setCategory(String category) { this.category = category; }

    // To String for the print and such
    @Override
    public String toString() {
        return "Account: " + name + "\nUsername: " + username + "\nPassword: " + password + "\nCategory: " + category;
    }
}
