UI Layer Overview
This file documents the new UI layer and the recent fixes that were done after integration feedback. It is not meant to replace the main project README. Think of it as a practical "what changed, why, and where" note for the interface work.

Tech Stack (Quick Recap)
The app is still hybrid for now:
- Java Activities for core flow and emulator integration
- Jetpack Compose (via ComposeView) for the modern UI layer
- Kotlin + Material3 for UI components
- LiveData + Coroutines for state and async UI updates
- Coil for image loading and local cache handling

Data and small persistent state are stored in SharedPreferences (last played game, playtime/session data, UI preferences).

Recent Integration Fixes
These are the latest interventions requested during merge review.

1) Theme behavior (System / Light / Dark)
What was wrong:
The UI had a fixed theme and no longer followed system dark/light preferences.

What was done:
- Added a dedicated theme manager with three modes: System, Light, Dark.
- Restored follow-system behavior as the default.
- Added a user-facing selector in General settings so users can override system behavior if they want.
- Introduced a proper light variant of the existing visual identity (same direction, brighter surfaces, adjusted text contrast).

Why this approach:
It keeps the current visual style, avoids duplicated UI logic, and gives full control without fragmenting the design.

2) Game selection mismatch (wrong game opens on click)
What was wrong:
In some views, the clicked item could resolve to the wrong game due to index drift/mismatch.

What was done:
- Replaced index-based game callbacks with a stable GameRef identifier passed from Compose to Activity.
- GameRef uses real game identity fields (serial / isoUri / ebootPath).
- Added robust lookup logic in MainActivity to resolve the correct adapter position from GameRef.

Why this approach:
Index-based wiring is fragile in dynamic UIs (filters, reordering, inserted cards). Stable identity makes behavior deterministic and is the right base for a fully native Compose UI later.

3) Bottom menu clipping + small tap areas in option lists
What was wrong:
- Bottom sheet content could feel cut near the lower area on some devices.
- In selection dialogs, only text felt reliably clickable (too narrow tap target).

What was done:
- Introduced reusable surface components:
  - ApsActionSheet for bottom-sheet action menus
  - ApsSelectionDialog for option picking dialogs
- Bottom sheet now uses a scrollable list with navigation bar inset padding.
- Option rows are now full-width clickable cards with proper minimum touch height.

Why this approach:
This is a structural fix, not a one-off patch. We now have reusable interaction primitives we can apply everywhere as we continue migrating from XML-era patterns to native Compose screens.

Current UI Goals
The short-to-mid term objective is to stop treating this as a visual layer over legacy code and move toward a native Compose UI architecture end to end.

Specifically:
- Keep behavior stable with identity-driven interactions (no positional assumptions)
- Standardize overlays (sheets/dialogs) through reusable components
- Keep theme/state centralized and predictable
- Improve responsiveness across device sizes and navigation modes

Configuration Note
TheGamesDB API key is still required for online metadata/cover retrieval.
If missing, the app falls back to cached assets/placeholders.

Code Map
Main UI screen:
- app/src/main/java/aenu/aps3e/ui/MainScreen.kt

UI reusable surface components:
- app/src/main/java/aenu/aps3e/ui/components/ApsSurfaceComponents.kt

Theme management:
- app/src/main/java/aenu/aps3e/AppThemeManager.kt
- app/src/main/java/aenu/aps3e/ui/Aps3eTheme.kt

Bridge and state resolution on Activity side:
- app/src/main/java/aenu/aps3e/MainActivity.java
- app/src/main/java/aenu/aps3e/Emulator.java

Settings screen integration:
- app/src/main/java/aenu/aps3e/SettingsScreen.kt
