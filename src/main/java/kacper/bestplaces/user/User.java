package kacper.bestplaces.user;

import kacper.bestplaces.reactions.Reaction;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Integer id;

	@NotNull
	private String email;

	@NotNull
	private String password;

	@NotNull
	private String name;

	@NotNull
	private String lastName;

	@NotNull
	private int active;

	private String activationCode;
	
	public String getActivationCode() {
		return activationCode;
	}
	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<Reaction> reactionList;

	@Transient
	private String operacja;
	
	@Transient
	private int nrRoli;
	
	@Transient
	private String newPassword;

	public String getUsername() {
		return name + ' ' + lastName;
	}
	
}
