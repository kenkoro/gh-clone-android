# Backlog
- [x] feat: authentication w/ personal access token (PAT)
- [x] feat: pat local storing
- [x] feat: repo list functionality (fetching)
- [x] test(overdue): main activity behavior
- [x] test(overdue): auth fragment behavior
- [x] test(overdue): repo list fragment behavior
- [x] test(overdue): key value storage
- [x] test(overdue): get repos in the app repository
- [x] test(overdue): auth in the app repository
- [x] fix: rename fragments by conventions
- [ ] ~~fix(repos-list): a bundle instead of a fragment result~~
  - Fragment Result API seems to fit in pretty fine
- [ ] ~~test: move to `runTest` instead of creating your own test dispatcher~~
  - in AndroidX tests (mostly the presentation layers) we use a custom test dispatcher in
  order to mock a repo's default dispatcher
- [ ] feat: log out from user's account
- [ ] feat: repo details fragment
- [ ] feat: get repo, get repo's readme in the app repository
  - come up w/ a solution, tests, fragment behavior, refactoring