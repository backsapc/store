package Entities;

import javax.persistence.*;

@Entity
@Table(name = "USER", schema = "PUBLIC", catalog = "PUBLIC")
public class UserEntity {
    private String username;
    private String defaulttab;

    @Id
    @Column(name = "USERNAME", nullable = false, length = 60)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "DEFAULTTAB", nullable = true, length = 60)
    public String getDefaulttab() {
        return defaulttab;
    }

    public void setDefaulttab(String defaulttab) {
        this.defaulttab = defaulttab;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (defaulttab != null ? !defaulttab.equals(that.defaulttab) : that.defaulttab != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (defaulttab != null ? defaulttab.hashCode() : 0);
        return result;
    }
}
