package kacper.bestplaces.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	private int roleId;
	
	@Transient
	private String newPassword;

	public String getUsername() {
		return name + ' ' + lastName;
	}

	private User(User user){
		this.activationCode =user.activationCode;
		this.email = user.email;
		this.lastName = user.lastName;
		this.name = user.name;
		this.id = user.id;
		this.password = user.password;
	}

	public User clone(){
		return new User(this);
	}

}
