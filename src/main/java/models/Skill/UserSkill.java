package models.Skill;

import java.util.ArrayList;

public class UserSkill extends Skill {
    private ArrayList<String> endorsers;

    public UserSkill() {

    }

    public UserSkill(String name, int points) {
        this.name = name;
        this.point = points;
        this.endorsers = new ArrayList<>();
    }

    public boolean isUniqueEndorser(String id){
        for(String index:this.endorsers){
            if (index.equals(id))
                return false;
        }
        return true;
    }

    public void endorse(String id){
        if (isUniqueEndorser(id)) {
            this.point++;
            this.endorsers.add(id);
        }
    }

    public ArrayList<String> getEndorsers() {
        return endorsers;
    }
}
