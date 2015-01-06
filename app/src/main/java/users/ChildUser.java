package users;

import java.util.ArrayList;
import java.util.UUID;

public class ChildUser {
    private String parentUserName;

    private String parentPassword;

    private String name;

    private String email;

    private String ID = UUID.randomUUID().toString();

    private ArrayList<String> parentIDs = new ArrayList<String>();

    public String getParentUserName() {
        return parentUserName;
    }

    public void setParentUserName(String parentUserName) {
        this.parentUserName = parentUserName;
    }

    public String getParentPassword() {
        return parentPassword;
    }

    public void setParentPassword(String parentPassword) {
        this.parentPassword = parentPassword;
    }

    public void setName(String name){this.name = name;}

    public String getName(){return this.name;}

    public String getEmail(){return this.email;}

    public void setEmail(String email){this.email = email;}

    public ArrayList<String> getParentIDs(){return this.parentIDs;}

    public void setParentIDs(ArrayList<String> parentIDs){
        this.parentIDs = new ArrayList<String>();
        for(String id:parentIDs) this.parentIDs.add(id);
        this.name = name;}

    public String getID() {return ID;}
}
