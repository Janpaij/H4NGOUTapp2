package jr.h4ngout;

/**
 * Created by jrfif on 19/02/2018.
 */

public class User {
    //private int cateogry_id;
    private String cateogry_id;//new
    private String category_Name;
    private boolean isSelected;

    //public int getCateogry_id() { return cateogry_id; }
    public String getCateogry_id() {
        return cateogry_id;
    } //new
    //public void setCateogry_id(int cateogry_id) { this.cateogry_id = cateogry_id;}
    public void setCateogry_id(String cateogry_id) { this.cateogry_id = cateogry_id;}//new
    public String getCategory_Name() {
        return category_Name;
    }
    public void setCategory_Name(String category_Name) {
        this.category_Name = category_Name;
    }
    public boolean isSelected() {
        return isSelected;
    }
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
