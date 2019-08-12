package kacper.bestplaces.places;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.sun.istack.internal.NotNull;

@Entity
@Table(name="places")
public class Places {

	@Id
	@Column(name="id")
	private long id;
	
	@Column(name="type")
	@NotNull
	private String type;
	
	@Column(name="name")
	@NotNull
	private String name;
	
	@Column(name="loc")
	@NotNull
	private String loc;
	
	@Column(name="descrp")
	@NotNull
	private String descrp;
	
	@Column(name="author")
	@NotNull
	private String author;
	
	@Column(name="link")
	@NotNull
	private String link;
	
	@Column(name="up")
	private int up;
	
	@Column(name="down")
	private int down;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public String getDescrp() {
		return descrp;
	}

	public void setDescrp(String descrp) {
		this.descrp = descrp;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getLink() {
		return link;
	}


	public void setId(long id) {
		this.id = id;
	}

	public int getUp() {
		return up;
	}

	public void setUp(int up) {
		this.up = up;
	}

	public int getDown() {
		return down;
	}

	public void setDown(int down) {
		this.down = down;
	}

	
	
	}
