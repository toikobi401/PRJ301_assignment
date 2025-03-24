package data;
import java.util.ArrayList;

public class Feature {
    private int FeatureID;
    private String FeatureURL;
    private ArrayList<Role> roles = new ArrayList<>();

    public ArrayList<Role> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<Role> roles) {
        this.roles = roles;
    } 

    public int getFeatureID() {
        return FeatureID;
    }

    public void setFeatureID(int FeatureID) {
        this.FeatureID = FeatureID;
    }

    public String getFeatureURL() {
        return FeatureURL;
    }

    public void setFeatureURL(String FeatureURL) {
        this.FeatureURL = FeatureURL;
    }
    
}
