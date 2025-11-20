package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Match {
    private int id;
    private Tournament tournament;
    private Team teamA;
    private Team teamB;
    private Map map;//domain model contains the real object and not just its id as in DTO
    private String score;
}
