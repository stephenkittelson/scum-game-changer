public class Player {
    private Role role;
    private String name;

    public Player(Role role, String name) {
        this.role = role;
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
