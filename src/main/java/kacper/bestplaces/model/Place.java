package kacper.bestplaces.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
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

    public Integer getRate(Rate rate) {
        return Math.toIntExact(reactionList.stream().filter(reaction -> reaction.getRate() == rate).count());
    }

}
