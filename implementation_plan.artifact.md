# Modernization of `new2` Package to Kotlin (2026 Style)

This plan outlines the steps to convert and optimize the legacy Java code in the `new2` package to modern, idiomatic Kotlin, ensuring it is clean, warning-free, and concise.

## Proposed Changes

### 1. Core Interfaces and Utilities

Grouped to ensure basic definitions are available for the View components.

#### [NEW] [IBubbleNavigation.kt](file:///D:/MyProjects/Library/Multi Bottoms/Multi Bottoms/multibottoms/src/main/java/io/selimdawa/multibottoms/new2/IBubbleNavigation.kt)
#### [NEW] [BubbleNavigationChangeListener.kt](file:///D:/MyProjects/Library/Multi Bottoms/Multi Bottoms/multibottoms/src/main/java/io/selimdawa/multibottoms/new2/listener/BubbleNavigationChangeListener.kt)
#### [NEW] [ViewUtils.kt](file:///D:/MyProjects/Library/Multi Bottoms/Multi Bottoms/multibottoms/src/main/java/io/selimdawa/multibottoms/new2/util/ViewUtils.kt)
#### [NEW] [BubbleToggleItem.kt](file:///D:/MyProjects/Library/Multi Bottoms/Multi Bottoms/multibottoms/src/main/java/io/selimdawa/multibottoms/new2/BubbleToggleItem.kt)

### 2. UI Components

Main View implementations refactored for conciseness and modern Android standards.

#### [NEW] [BubbleToggleView.kt](file:///D:/MyProjects/Library/Multi Bottoms/Multi Bottoms/multibottoms/src/main/java/io/selimdawa/multibottoms/new2/BubbleToggleView.kt)
#### [NEW] [BubbleNavigationLinearView.kt](file:///D:/MyProjects/Library/Multi Bottoms/Multi Bottoms/multibottoms/src/main/java/io/selimdawa/multibottoms/new2/BubbleNavigationLinearView.kt)
#### [NEW] [BubbleNavigationConstraintView.kt](file:///D:/MyProjects/Library/Multi Bottoms/Multi Bottoms/multibottoms/src/main/java/io/selimdawa/multibottoms/new2/BubbleNavigationConstraintView.kt)

### 3. Cleanup

Removing legacy Java files after successful Kotlin implementation.

#### [DELETE] [IBubbleNavigation.java](file:///D:/MyProjects/Library/Multi Bottoms/Multi Bottoms/multibottoms/src/main/java/io/selimdawa/multibottoms/new2/IBubbleNavigation.java)
#### [DELETE] [BubbleNavigationChangeListener.java](file:///D:/MyProjects/Library/Multi Bottoms/Multi Bottoms/multibottoms/src/main/java/io/selimdawa/multibottoms/new2/listener/BubbleNavigationChangeListener.java)
#### [DELETE] [ViewUtils.java](file:///D:/MyProjects/Library/Multi Bottoms/Multi Bottoms/multibottoms/src/main/java/io/selimdawa/multibottoms/new2/util/ViewUtils.java)
#### [DELETE] [BubbleToggleItem.java](file:///D:/MyProjects/Library/Multi Bottoms/Multi Bottoms/multibottoms/src/main/java/io/selimdawa/multibottoms/new2/BubbleToggleItem.java)
#### [DELETE] [BubbleToggleView.java](file:///D:/MyProjects/Library/Multi Bottoms/Multi Bottoms/multibottoms/src/main/java/io/selimdawa/multibottoms/new2/BubbleToggleView.java)
#### [DELETE] [BubbleNavigationLinearView.java](file:///D:/MyProjects/Library/Multi Bottoms/Multi Bottoms/multibottoms/src/main/java/io/selimdawa/multibottoms/new2/BubbleNavigationLinearView.java)
#### [DELETE] [BubbleNavigationConstraintView.java](file:///D:/MyProjects/Library/Multi Bottoms/Multi Bottoms/multibottoms/src/main/java/io/selimdawa/multibottoms/new2/BubbleNavigationConstraintView.java)

## Modernization Principles
- **Conciseness**: Using `data class` for models, `with` and `apply` for view configuration, and extension functions for utilities.
- **Null Safety**: Leveraging Kotlin's type system to eliminate redundant null checks.
- **Modern Android**: Using `View.post` only when necessary, favoring `doOnPreDraw` or similar if applicable, and updating deprecated methods (e.g., `setSingleLine`).
- **Performance**: Reducing object allocation (e.g., reusing `ConstraintSet` where possible).

## Verification Plan

### Automated Tests
- Run `gradle :multibottoms:assembleDebug` to ensure compilation.
- (Optional) Run UI tests if available in the `:app` module that use `new2`.

### Manual Verification
- Deploy the `:app` module to verify the Bubble Navigation continues to work as expected.
- Check for any lint warnings in the new Kotlin files.
