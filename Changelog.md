# Changelog

## Version v0.5.0 (2024-07-03)

### Features

- Add endpoint for getting paginated player list (cbb1a041)
- Add service for a paginated player list (2c31d746)

### Tests

- Create unit test for player list GET (4176345d)

### Chores and tidying

- Save more terminal commands used during development (b94ac92e)

## Version v0.4.0 (2024-06-26)

### Features

- added endpoint for player detail (afb89270)
- added mapper to PlayerDTO (5b1ab99e)
- added a service for retrieving product detail given an id (e2ad0026)
- added more error constants for product details (ad139c23)
- added DTO for product details response (11a8ce8a)

### Tests

- added unit test for the new endpoint (5eae8de9)

### Chores and tidying

- added more commands in bash history (992c208c)

## Version v0.3.0 (2024-06-24)

### Features

- added endpoint to player delete api (8aee7a20)
- added mapper to PlayerDeleteResponseDTO (74106355)
- added service to delete players (2fa62750)
- added dao method to delete player by ids (7f0a2e07)
- added new success for player delete api (1407be56)
- added DTO for player delete api (a34504b1)

### Tests

- added unit test for player delete api (d91e0a95)

### Chores and tidying

- added commands used to implement delete api (70bc0c70)

## Version v0.2.0 (2024-06-22)

### Features

- added configs for unit test (b9966f10)
- added mapper utilities (e8dd1c70)
- added service for player to create (04eb46c3)
- added repository for player (f630d528)
- added exception for generic cases (416da19f)
- added enum for error and success response (bb2e48aa)
- added controller for player api (f9808656)
- added entity for player (833008da)
- added DTO for player create API (98eb2721)
- added config for database (e9d37fd1)
- added config for global exception handler (5e9b080f)

### Fixes

- fixed HISTFILE location in bashrc override (2c6f77cb)

### Tests

- added unit test for player create (77881975)

### Refactoring

- changed ddl auto to none (6bad4e11)
- deleted boilerplates (4728b6bf)

### Chores and tidying

- added my local bash history commands (266b811a)
- added more extension in devcontainer (aaec634a)

## Version v0.1.0 (2024-06-17)

### Features

- added a release bot for main branch (70c050c1)
- added sample test class (2cf3003e)
- integrated JPA for player entity (e9c99d07)
- added MainClass to run Spring (f5aabbce)
- added gradle for this project (d4406574)
- added resource properties for this project (fb624435)
- added more stuff in gitignore (902d30e6)
- fixing bash_history (ca45b202)
- added gitignore (6a880916)
- added docker compose for local (54538497)
- added docker file for local spring (4c6577fd)
- added gitattribute for LF changes (b03e237c)
- added devcontainer.json (07fe6148)
- added local docker compose manifest file (c25b639b)
- added docker image manifest files for spring and mysql (1f0ed7b4)
- added env for the docker image (50bd0979)
- added bash feature in .devcontainer (dbe287a6)

### Fixes

- changed the token used by release bot to PAT var (d022cae0)
- corrected a typo command in release bot manifest file (1f9f7f48)

### Chores and tidying

- renamed the pull request templates dir (7b5ce247)
- added merge request templates (d973cfab)
- caching bash commands i made (218ef6f0)

### Other

- Initial commit (19899db2)

