# Quizzy - The Ultimate Tech Quiz App 🧠💻

Quizzy is a modern, AI-enhanced Android application designed for tech enthusiasts and students to test their knowledge across various computer science domains. From Core Java to Operating Systems, Quizzy provides a seamless and engaging learning experience.

---

## 🚀 Features

- **Multi-Category Quizzes**: Specialized quizzes in technology domains including:
  - Java Programming
  - Database Management Systems (DBMS)
  - Operating Systems (OS)
  - Computer Networks (CN)
- **AI ChatBot Assistant**: Powered by Google's Gemini API, providing instant explanations and learning support.
- **Secure Authentication**: Robust user sign-in and sign-up powered by Firebase Auth.
- **Offline Support**: Local database integration with Room for persisting quiz data and user progress.
- **Modern UI/UX**: Built entirely with Jetpack Compose and Material 3 for a fluid, responsive interface.
- **Real-time Sync**: Firebase Realtime Database for syncing progress across devices.

---

## 🛠️ Tech Stack

- **Language**: [Kotlin](https://kotlinlang.org/)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/compose) (Material 3)
- **Architecture**: Clean Architecture with MVVM pattern
- **Dependency Injection**: [Hilt](https://dagger.dev/hilt/)
- **Networking**: [Ktor Client](https://ktor.io/)
- **Local Database**: [Room](https://developer.android.com/training/data-storage/room)
- **Backend/Services**: 
  - [Firebase Authentication](https://firebase.google.com/docs/auth)
  - [Firebase Realtime Database](https://firebase.google.com/docs/database)
- **AI Integration**: [Google Gemini API](https://ai.google.dev/)
- **Navigation**: [Jetpack Navigation Compose](https://developer.android.com/jetpack/compose/navigation)
- **Image Loading**: [Coil](https://coil-kt.github.io/coil/) (if used, common in such apps)
- **Serialization**: [Kotlinx Serialization](https://kotlinlang.org/docs/serialization.html)

---

## 📁 Project Structure

The project follows the **Clean Architecture** principles, divided into the following layers:

- `data/`: Contains Repository implementations, Data Sources (Local Room DB, Remote Ktor API), Mappers, and API Models.
- `domain/`: Contains Business Logic, Use Cases, and Repository Interfaces (Pure Kotlin).
- `presentation/`: Contains UI components, Compose Screens, ViewModels, and Navigation Logic.
- `di/`: Hilt modules for dependency injection.

---

## ⚙️ Setup & Installation

### Prerequisites
- Android Studio **Ladybug** (or later)
- JDK 11+
- Android SDK 36 (Compile SDK)
- A Firebase Project

### Steps to Run
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-username/quizzy.git
   ```
2. **Firebase Setup**:
   - Create a project on the [Firebase Console](https://console.firebase.google.com/).
   - Add an Android App with the package name `com.mayur.quizzy`.
   - Download `google-services.json` and place it in the `app/` directory.
3. **API Configuration**:
   - Create a `local.properties` file in the root directory (if not exists).
   - Add your Gemini API Key:
     ```properties
     MY_API_KEY=your_gemini_api_key_here
     ```
4. **Build and Run**:
   - Sync the project with Gradle files.
   - Run the app on an emulator or physical device.

---

## 📸 Screenshots

| Login Screen | Home Screen | Quiz Screen | AI ChatBot |
| :---: | :---: | :---: | :---: |
| *[Add Screenshot]* | *[Add Screenshot]* | *[Add Screenshot]* | *[Add Screenshot]* |

---

## 📦 Key Dependencies

- **Compose**: `androidx.compose.ui`, `androidx.compose.material3`
- **Networking**: `io.ktor:ktor-client-android`, `io.ktor:ktor-client-content-negotiation`
- **Database**: `androidx.room:room-runtime`, `androidx.room:room-ktx`
- **DI**: `com.google.dagger:hilt-android`
- **Firebase**: `com.google.firebase:firebase-auth-ktx`, `com.google.firebase:firebase-database-ktx`
- **Serialization**: `org.jetbrains.kotlinx:kotlinx-serialization-json`

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---
*Made with ❤️ by Mayur*
