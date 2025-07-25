# Package Diagram Structure Overview

## Visual Structure of the Package Diagram

```
    Package Diagram - PRJ301 Leave Request Management System
    
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   <<package>>   │    │   <<package>>   │    │   <<package>>   │
│      data       │◄──►│      dal        │    │      view       │
│                 │    │                 │    │                 │
│ + User          │    │ + DBContext     │    │ + auth/         │
│ + LeaveRequest  │    │ + UserDBContext │    │   - login.jsp   │
│ + Department    │    │ + LeaveRequest..│    │   - home.jsp    │
│ + Role          │    │ + Department..  │    │ + function/     │
│ + LeaveStatus   │    │ + Role..        │    │   - leavereq..  │
│ + Feature       │    │ + UserWorking.. │    │ + manage/       │
│ + UserWorking.. │    │                 │    │   - managereq.. │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         ▲                       ▲                       │
         │                       │                       │
         │              ┌────────┴────────┐             │
         │              │                 │             │
         └──────────────►│  <<package>>    │◄────────────┘
                        │   controller    │
                        │                 │
                        │ ┌─────────────┐ │
                        │ │authentication│ │
                        │ │ + Login..   │ │
                        │ │ + Logout..  │ │
                        │ │ + Home..    │ │
                        │ └─────────────┘ │
                        │                 │
                        │ ┌─────────────┐ │
                        │ │leaverequest │ │
                        │ │ + Create..  │ │
                        │ │ + Update..  │ │
                        │ │ + Approve.. │ │
                        │ │ + Reject..  │ │
                        │ └─────────────┘ │
                        │                 │
                        │ ┌─────────────┐ │
                        │ │   agenda    │ │
                        │ │ + UserList..│ │
                        │ │ + UserWork..│ │
                        │ └─────────────┘ │
                        └─────────────────┘
                                 │
                                 ▼
                        ┌─────────────────┐
                        │   <<package>>   │
                        │    Database     │
                        │                 │
                        │ Tables:         │
                        │ + User          │
                        │ + LeaveRequest  │
                        │ + Department    │
                        │ + Role          │
                        │ + LeaveStatus   │
                        │ + Feature       │
                        │ + FeatureRole   │
                        │ + UserRole      │
                        └─────────────────┘
```

## Package Dependencies:
- DAL ──►Data (uses entity classes)
- Controller ──► DAL (uses data access layer)  
- Controller ──► Data (uses entity classes directly)
- View ──► Controller (calls controllers for processing)
- DAL ──► Database (accesses database tables)

## Architecture Pattern: MVC
- **Model**: Data + DAL packages
- **View**: View package (JSP files)
- **Controller**: Controller package (with sub-packages)

This diagram can be imported into draw.io using the PackageDiagram.drawio.xml file.