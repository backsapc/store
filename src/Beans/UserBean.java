package Beans;

import java.io.Serializable;

public class UserBean implements Serializable {
    private String role;
    private String name;
    private String defaultTab;

    public UserBean(){
        setRole("NotAuthorized");
        setName("Undefined");
        setDefaultTab("Overview");
    }

    public String getDefaultTab() {
        return defaultTab;
    }

    public void setDefaultTab(String defaultTab) {
        this.defaultTab = defaultTab;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
