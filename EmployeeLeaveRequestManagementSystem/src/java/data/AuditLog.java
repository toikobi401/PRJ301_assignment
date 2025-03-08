package data;

import java.time.LocalDateTime;

public class AuditLog {
    private int LogID;
    private int UserID;
    private String Action;
    private String Entity;
    private int EntityID;
    private LocalDateTime Timestamp;

    public int getLogID() {
        return LogID;
    }

    public void setLogID(int LogID) {
        this.LogID = LogID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String Action) {
        this.Action = Action;
    }

    public String getEntity() {
        return Entity;
    }

    public void setEntity(String Entity) {
        this.Entity = Entity;
    }

    public int getEntityID() {
        return EntityID;
    }

    public void setEntityID(int EntityID) {
        this.EntityID = EntityID;
    }

    public LocalDateTime getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(LocalDateTime Timestamp) {
        this.Timestamp = Timestamp;
    }



}
