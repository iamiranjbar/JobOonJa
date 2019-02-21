package Skill;

import java.util.ArrayList;

public class UserSkill extends Skill {
    private ArrayList<Integer> endorsers;

    public UserSkill(String name, int points) {
        this.name = name;
        this.points = points;
        this.endorsers = new ArrayList<>();
    }

    private boolean isUniqueEndorser(int id){
        for(int index:this.endorsers){
            if (index == id)
                return false;
        }
        return false;
    }

    public void endorse(int id){
        if (isUniqueEndorser(id)) {
            this.points++;
            this.endorsers.add(id);
        }
    }
}
