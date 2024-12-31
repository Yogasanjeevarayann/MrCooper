# Jetpack Compose App Implementation

## Overview

This project is a Android application built with **Jetpack Compose**, ensuring dynamic validation, seamless interaction, and persistent data storage. It leverages modern Android development practices and tools for optimal performance, maintainability, and user experience.
![logo_mrcooper](https://github.com/user-attachments/assets/0b27e24a-06e8-46f7-ae70-2f5eec29e224)


## Key Features

### 1. **Jetpack Compose**
- Fully implemented UI using Jetpack Compose.
- Dynamic validation and type restrictions for a seamless user experience.
- Persistent data handling for uninterrupted usage.

### 2. **StateFlow**
- Replaced LiveData with StateFlow for improved performance and reactivity.

### 3. **Concurrent API Calls**
- Utilized `awaitAll` for deferred results, optimizing API call execution by running them concurrently.

### 4. **Dependency Injection with Hilt**
- Integrated Hilt for clean and modular dependency injection.
- Room and Retrofit dependencies provided via Hilt for better scalability.

### 5. **Secure API Key Management**
- API keys stored securely in the `gradle.properties` file.
- Accessed using `BuildConfig` to prevent hardcoding and enhance security.

### 6. **SplashScreen API**
- Integrated SplashScreen API for a polished startup experience.

### 7. **Smooth Screen Transitions**
- Leveraged Jetpack Compose navigation animations for intuitive screen transitions.

### 8. **SOLID Principles**
- Adhered to SOLID principles for scalable, maintainable, and testable code.

### 9. **Organized Folder Structure**
- Followed a standard project structure to ensure reliability and ease of navigation.

### 10. **String Localization**
- All hardcoded strings moved to `strings.xml` for localization readiness.

### 11. **Database Cleanup**
- Ensured that all data is appropriately flushed when the app is closed.

## Project Deliverables

- **APK**: Ready-to-install application.
- **Source Code**: Full project implementation.
- **Video Reference**: Demonstration of the app functionality.

[**Google Drive Link**](https://drive.google.com/drive/folders/1UyVtGpcm9nzuOsED0c7XPssa62t3_eLA?usp=sharing)

## Tools and Technologies

- **Jetpack Compose** for UI development.
- **StateFlow** for reactive state management.
- **Hilt** for dependency injection.
- **Room** for local database management.
- **Retrofit** for network operations.
- **SplashScreen API** for improved startup experience.
- **Dokka** Code designed with capable to generate dokka docs [Java doc]

## Testing and Validation

- The implementation has been thoroughly tested for:
  - Dynamic validation.
  - Data persistence.
  - Smooth navigation and transitions.
  - API performance and security.



![img1](https://github.com/user-attachments/assets/0566fc75-2c2e-4d16-884e-d26890a9e5da)
![img2](https://github.com/user-attachments/assets/a18428a4-77f0-4839-a392-80dbb07ffe78)
![img3](https://github.com/user-attachments/assets/5cf9b1ba-6ad3-4129-801f-85bcc0a0100f)
![img4](https://github.com/user-attachments/assets/c88c602d-73ec-4007-a3c2-fe940c914780)
![image_2024_12_28T07_22_05_222Z](https://github.com/user-attachments/assets/0d84860a-efe0-4b31-95c6-f18ab9d89bf7)





## How to Run the Project

1. Clone the repository.
2. Add your API key to the `gradle.properties` file.
3. Build and run the app using Android Studio.


---

For further inquiries or contributions, please feel free to reach out.
[**Porject Showcase**](https://yogzzin.netlify.app/)
