package model;

public class Admin extends Person {
    private static final long serialVersionUID = 1L;

    private String role;

    public Admin() {
        super();
    }

    public Admin(String id, String name, String password, String role) {
        super(id, name, password);
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Admin{id='" + getId() + "', name='" + getName() + "', role='" + role + "'}";
    }
}
