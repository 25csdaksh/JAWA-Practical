package form;

public class SignupForm {
    @NotBlank(message = "Username is mandatory and cannot be blank")
    @MaxLength(value = 12, message = "Username cannot exceed 12 characters")
    private String username;

    @NotBlank(message = "Email address is required")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @MaxLength(value = 20, message = "Password cannot exceed 20 characters")
    private String password;

    public SignupForm(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}
