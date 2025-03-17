
package data;


public class Department {
    private int DepartmentID;
    private String DepartmentName;
    private int ManagerID;

    public int getDepartmentID() {
        return DepartmentID;
    }

    public void setDepartmentID(int DepartmentID) {
        this.DepartmentID = DepartmentID;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String DeparmentName) {
        this.DepartmentName = DeparmentName;
    }

    public int getManagerID() {
        return ManagerID;
    }

    public void setManagerID(int ManagerID) {
        this.ManagerID = ManagerID;
    }
    
}
