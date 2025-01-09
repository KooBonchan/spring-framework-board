# Board Project - Spring 5 Legacy

[![Project Stack](https://skillicons.dev/icons?i=spring,html,bootstrap)](https://skillicons.dev)

![oracle](https://img.shields.io/badge/Oracle-F80000?style=for-the-badge&logo=oracle&logoColor=black)

---

## Achievements

### Security

- Member authentication using Spring Security
- Connecting Spring Security with Legacy member info database
- Remember-me
- Remove JSESSION_ID when log-out
- Change encrypt method from manual SHA256 to Security Default BCrypt

### Board CRUD

- Basic CRUD for board
- File upload, download, update
- Block non-image file upload using MIME-type
- Sync with spring security, resolving conflict between multipart/form-data and CSRF token

### Board View

- Paging
- Filtering
- UI: show from list if board contains an image
- UI: show from list if board contains a comment

### Layout

- Bootstrap
- Extract common layout and header

## System Requirement

- Spring 5.0.8, Spring Security 5.0.7
- JUnit 4.13
- MyBatis
- HikariCP 3.4.5
- Lombok

## Demo Images

[DemoImage1](assets/데모%201.png)
[DemoImage2](assets/데모%202.png)
[DemoImage3](assets/데모%203.png)
[DemoImage4](assets/데모%204.png)
