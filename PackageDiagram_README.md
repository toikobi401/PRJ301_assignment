# Package Diagram - PRJ301 Leave Request Management System

## Mô tả
Biểu đồ gói (Package Diagram) này mô tả kiến trúc tổng thể của hệ thống quản lý đơn xin nghỉ phép PRJ301 Assignment, sử dụng mô hình MVC (Model-View-Controller).

## Cách sử dụng file draw.io
1. Truy cập [draw.io](https://app.diagrams.net/)
2. Chọn "Open Existing Diagram"
3. Upload file `PackageDiagram.drawio.xml`
4. Biểu đồ sẽ được tải và có thể chỉnh sửa

## Cấu trúc gói chính

### 1. Data Package (Lớp Model)
- **Mục đích**: Chứa các lớp thực thể (Entity classes) đại diện cho dữ liệu
- **Các lớp chính**:
  - `User`: Thông tin người dùng
  - `LeaveRequest`: Đơn xin nghỉ phép
  - `Department`: Phòng ban
  - `Role`: Vai trò/quyền hạn
  - `LeaveStatus`: Trạng thái đơn nghỉ
  - `Feature`: Tính năng hệ thống
  - `UserWorkingDetail`: Chi tiết công việc

### 2. DAL Package (Data Access Layer)
- **Mục đích**: Lớp truy cập dữ liệu, xử lý kết nối và truy vấn database
- **Các lớp chính**:
  - `DBContext`: Lớp cơ sở quản lý kết nối database
  - `UserDBContext`: Truy cập dữ liệu người dùng
  - `LeaveRequestDBContext`: Truy cập dữ liệu đơn nghỉ phép
  - `DepartmentDBContext`: Truy cập dữ liệu phòng ban
  - `RoleDBContext`: Truy cập dữ liệu vai trò
  - `UserWorkingDetailDBContext`: Truy cập chi tiết công việc

### 3. Controller Package (Lớp Controller)
- **Mục đích**: Xử lý logic nghiệp vụ và điều khiển luồng ứng dụng
- **Các sub-package**:

#### 3.1. Authentication Sub-package
- `LoginController`: Xử lý đăng nhập
- `LogoutController`: Xử lý đăng xuất
- `HomeController`: Trang chủ
- `BaseRequiredAuthenticationController`: Lớp cơ sở yêu cầu xác thực

#### 3.2. LeaveRequest Sub-package
- `CreateLeaveRequestController`: Tạo đơn nghỉ phép
- `UpdateLeaveRequestController`: Cập nhật đơn nghỉ phép
- `ApproveLeaveRequestController`: Phê duyệt đơn
- `RejectLeaveRequestController`: Từ chối đơn
- `DeleteLeaveRequestController`: Xóa đơn
- `YourLeaveRequestController`: Xem đơn của mình
- `ManageRequestServletController`: Quản lý đơn

#### 3.3. Agenda Sub-package
- `UserListController`: Danh sách người dùng
- `UserWorkingDetailController`: Chi tiết công việc

### 4. View Package (Lớp View)
- **Mục đích**: Giao diện người dùng (JSP pages)
- **Cấu trúc**:
  - `auth/`: Trang xác thực (login.jsp, home.jsp)
  - `function/`: Trang chức năng (leaverequest.jsp, yourleavereq.jsp, etc.)
  - `manage/`: Trang quản lý (managerequest.jsp, userlist.jsp, etc.)

### 5. Database Package
- **Mục đích**: Cơ sở dữ liệu SQL Server
- **Các bảng chính**:
  - User, LeaveRequest, Department, Role, LeaveStatus
  - Feature, FeatureRole, UserRole

## Mối quan hệ phụ thuộc

1. **DAL → Data**: Lớp DAL sử dụng các lớp thực thể từ Data
2. **Controller → DAL**: Controller sử dụng DAL để truy cập dữ liệu
3. **Controller → Data**: Controller sử dụng trực tiếp các lớp thực thể
4. **View → Controller**: View gọi các Controller để xử lý
5. **DAL → Database**: DAL truy cập trực tiếp đến database

## Kiến trúc MVC
- **Model**: Data package + DAL package
- **View**: View package (JSP files)
- **Controller**: Controller package và các sub-packages

## Công nghệ sử dụng
- **Backend**: Java EE (Servlets, JSP)
- **Database**: SQL Server
- **Architecture Pattern**: MVC (Model-View-Controller)
- **Web Framework**: Java Servlet API

## Ghi chú
- Biểu đồ này được tạo dựa trên phân tích codebase thực tế của dự án
- Các mối quan hệ phụ thuộc được thể hiện bằng mũi tên đứt nét
- Màu sắc khác nhau để phân biệt các loại package khác nhau