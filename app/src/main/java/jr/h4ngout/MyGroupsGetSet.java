package jr.h4ngout;

/**
 * Created by jrfif on 26/02/2018.
 */

public class MyGroupsGetSet {
    private String title, members, GroupID;

    public MyGroupsGetSet(String title, String members, String GroupID)
    {
        this.setTitle(title);
        this.setMembers(members);
        this.setGroupID(GroupID);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public String getGroupID() {
        return GroupID;
    }

    public void setGroupID(String GroupID) {
        this.GroupID = GroupID;
    }
}
