## A sample-dample app
*Inspired by learning*
<br />
<div align="center">
  <img src="https://cdn3.emoji.gg/emojis/1158-blackcatroll.gif" alt="kitty should be here" />
</div>

### Structure
```text
└── githubClone
    ├── GithubCloneApp.kt
    ├── MainActivity.kt      <- one main activity
    ├── core                 <- common logic used across 'features'
    │   ├── data
    │   ├── domain
    │   └── navigation
    ├── di                   <- di configuration
    └── reposViewer          <- feature 'viewer of repos'
        ├── data
        ├── domain           <- models, functionality logic
        └── presentation
```

App features, and other stuff is in [backlog]().
