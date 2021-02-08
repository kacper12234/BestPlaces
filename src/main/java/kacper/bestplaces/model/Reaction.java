package kacper.bestplaces.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name="reactions")
public class Reaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne
	private User user;

	@ManyToOne
	private Place place;

	@Column(name = "rate")
	@Enumerated(EnumType.STRING)
	private Type type;

	private String comment;


	
}	
