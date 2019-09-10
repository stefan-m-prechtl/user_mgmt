# REXT User Management (Java EE 8)
Einfacher REST-WS für die Verwaltung von Benutzern (User)

## Infrastruktur
Über Docker (siehe Ordner docker) werden verwendet:
- Payara 5 (full)
- MySQL 8

## Build
Build erfolgt über Gradle mit Tasks für
- Start/Stop Infrastruktur
- Deployment des WAR in den autodeploy Ordner

## URL (mit Docker-Container)
- http://localhost:8080/user_mgmt/rest/users
- http://localhost:8080/user_mgmt/rest/ping

