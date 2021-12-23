# Project-1-Reimbursement-App-

# Project Description
- The Expense Reimbursement System (ERS) will manage the process of reimbursing employees for expenses incurred while on company time
- Users can login and will be sent to different homepages based on their user role (Employee or Finance Manager), which have their own defined roles
  - Employees in the company can login and submit requests for reimbursement and view their past tickets and pending requests
  - Finance managers can log in and view all reimbursement requests and have the ability to approve and deny requests for expense reimbursement.

# Technologies Used
- DBeaver (PostgreSQL)
- Javalin
- HTML/CSS/JavaScript
- Logback
- JUnit 5
- Mockito
- Selenium
- JDBC

# Features
- Employee's:
  - Has the ability to login and log out
  - Can fill out information to submit a reimbursement request for Finance Managers to approve or deny
  - Can search reimbursements that belong to them
  - Can view the results of any of their ubmitted reimbursment request
- Finance Managers:
  - Can approve or deny any submitted reimbursement request
  - Can search and view reimbursement requests submitted by any employee
  - Can filter submitted reimbursements based on the status (Approved, Denied, or PENDING)

# To-Do List
- Incorporating Selenium Testing
- Upload receipt images
- Submitting a new Reimbursement as an Employee
- Denying or Rejecting reimbursement requests as a Finance Manager
