# GoNews

GoNews is a native Android news reader built with Kotlin and Jetpack Compose. It shows the latest US top headlines from [NewsAPI](https://newsapi.org/) and lets you save articles for later. Saved articles are stored locally and available offline.

## Features

- **Top headlines**: fetches top US headlines from the NewsAPI `top-headlines` endpoint.
- **Swipe to save**: swipe an article on the Home screen to save it, with a toast as confirmation.
- **Saved tab**: lists the most recent saved articles (up to 50) from the local database. Swipe an article to remove it.
- **Detailed article card** and an info dialog.
- **Bottom navigation** between Home and Saved.

## Tech stack

| Area | Library |
| --- | --- |
| UI | Jetpack Compose, Material 3, Material Icons Extended |
| Architecture | MVVM (`ViewModel` + `StateFlow`), repository pattern |
| Networking | Retrofit 3 + Gson converter |
| Persistence | Room 2.8 (KSP annotation processing) |
| Images | Coil 3 (`coil-compose`, `coil-network-okhttp`) |
| Build | Gradle Kotlin DSL, version catalog (`gradle/libs.versions.toml`) |

Requirements: `minSdk 26`, `targetSdk`/`compileSdk 36`, Java 11.

## Project structure

```
app/src/main/java/com/cristobal/gonews/
├── MainActivity.kt          # Entry point, hosts the tab navigation
├── ApplicationContext.kt    # Application subclass exposing the app context
├── api_services/models/     # Retrofit client, API interface and response models
├── db/                      # Room DAOs, database and repositories
├── viewmodels/              # HomeScreenVM, SavedScreenVM
├── views/                   # Composables (Home, Saved, list items, tab bar)
├── ui/theme/                # Colors, typography, theme
└── utils/                   # Helpers
```

## Getting started

### 1. Get a NewsAPI key

Create a free account at [newsapi.org](https://newsapi.org/register) and copy your API key.

### 2. Configure the key

The key is read from the Gradle property `NEWS_API_KEY` and exposed as `BuildConfig.NEWS_API_KEY`. If the property is missing, the build falls back to `dummykey`, and API requests will fail.

Set it in your **user-level** `~/.gradle/gradle.properties` so it never ends up in version control:

```properties
NEWS_API_KEY=your_api_key_here
```

Alternatively, replace the `{{Your_Api_Key}}` placeholder in the project's `gradle.properties`. Do not commit a real key.

### 3. Build and run

Open the project in Android Studio and run the `app` configuration on a device or emulator (API 26+), or from the command line:

```bash
./gradlew installDebug
```

### Tests

```bash
./gradlew test                    # local unit tests
./gradlew connectedAndroidTest    # instrumented tests (needs a device/emulator)
```

## Notes

- The NewsAPI free developer plan has request limits and is intended for development use.
- The country is currently hard-coded to `us` in `HomeScreenVM`.
