package codemark.testservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "users", schema = "heroku_a3f774e3ab98d63")
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends AbstractEntity{
    @Basic
    @Column(name = "username")
    private String username;
    @Id
    @Column(name = "login")
    private String login;
    @Basic
    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "login"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<RoleEntity> roles;
}
