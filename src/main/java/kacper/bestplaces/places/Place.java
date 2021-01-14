package kacper.bestplaces.places;

import kacper.bestplaces.reactions.Reaction;
import kacper.bestplaces.reactions.Type;
import kacper.bestplaces.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "places")
public class Place {

    @Id
    private Integer id;

    @NotNull
    private String type;

    @NotNull
    private String name;

    @NotNull
    private String loc;

    @Lob
    @NotNull
    private String descrp;

    @NotNull
    @ManyToOne
    private User user;

    @OneToMany(mappedBy="place",cascade = CascadeType.ALL)
    private List<Reaction> reactionList = new ArrayList<>();

    private Integer count;

    public Integer getRate(Type type) {
        return Math.toIntExact(reactionList.stream().filter(reaction -> reaction.getType() == type).count());
    }

}
