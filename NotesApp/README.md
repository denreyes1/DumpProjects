# NotesApp

A simple Android notes application built with Kotlin and Jetpack Compose.

## Overview

NotesApp lets you create and manage notes with a clean, Material Design 3 interface. Notes are displayed in a scrollable list with timestamps and can be deleted individually.

## Features

- **Add notes** – Type and add new notes with a single tap
- **View notes** – Scrollable list showing note content, ID, and timestamp
- **Delete notes** – Remove individual notes via the delete button
- **Empty state** – Clear feedback when no notes exist

## Tech Stack

- **Kotlin** – Primary language
- **Jetpack Compose** – Modern declarative UI
- **Material 3** – UI components and theming
- **ViewModel** – State management for notes
- **Edge-to-edge** – Full-screen layout with system bar styling

## Requirements

- Android SDK 29+ (minSdk)
- Target SDK 36
- Java 11

## Project Structure

```
NotesApp/
├── app/
│   └── src/main/java/com/denreyes/notesapp/
│       ├── MainActivity.kt      # Main UI and Compose screens
│       ├── data/
│       │   └── Note.kt         # Note data model
│       ├── viewmodel/
│       │   └── NotesViewModel.kt # Notes state management
│       └── ui/theme/           # Theme, colors, typography
└── README.md
```
