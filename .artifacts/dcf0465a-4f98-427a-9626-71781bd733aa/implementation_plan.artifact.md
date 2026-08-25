# Refactor `new3` (SmoothBottomBar) to 2026 Standards

Refactor the `new3` package to use modern Kotlin practices, remove all warnings, and simplify the codebase.

## User Review Required

> [!NOTE]
> The package `me.ibrahimsn.lib` in `BottomBarParser.kt` will be corrected to `io.selimdawa.multibottoms.new3`.
> Listeners will be converted to `fun interface` for better Kotlin interop (SAM conversion).

## Proposed Changes

### `multibottoms` Sub-project

#### [MODIFY] [BottomBarItem.kt](file:///D:/MyProjects/Library/Multi Bottoms/Multi Bottoms/multibottoms/src/main/java/io/selimdawa/multibottoms/new3/BottomBarItem.kt)
- Convert `var` to `val` where possible.
- Simplify structure.

#### [MODIFY] [BottomBarParser.kt](file:///D:/MyProjects/Library/Multi Bottoms/Multi Bottoms/multibottoms/src/main/java/io/selimdawa/multibottoms/new3/BottomBarParser.kt)
- Fix package name to `io.selimdawa.multibottoms.new3`.
- Use `with` or `apply` for cleaner parsing logic.
- Simplify attribute retrieval.

#### [MODIFY] [OnItemSelectedListener.kt](file:///D:/MyProjects/Library/Multi Bottoms/Multi Bottoms/multibottoms/src/main/java/io/selimdawa/multibottoms/new3/OnItemSelectedListener.kt)
- Convert to `fun interface`.

#### [MODIFY] [OnItemReselectedListener.kt](file:///D:/MyProjects/Library/Multi Bottoms/Multi Bottoms/multibottoms/src/main/java/io/selimdawa/multibottoms/new3/OnItemReselectedListener.kt)
- Convert to `fun interface`.

#### [MODIFY] [SmoothBottomBar.kt](file:///D:/MyProjects/Library/Multi Bottoms/Multi Bottoms/multibottoms/src/main/java/io/selimdawa/multibottoms/new3/SmoothBottomBar.kt)
- Fix all lint warnings (unused variables, redundant calls).
- Replace `Color.parseColor` with `toColorInt()`.
- Simplify property delegates or use custom setters more concisely.
- Use `String.toColorInt()` from KTX.
- Clean up the `onDraw` and `calculateItemBounds` logic.
- Remove redundant null checks and SDK version checks.

#### [MODIFY] [NavigationComponentHelper.kt](file:///D:/MyProjects/Library/Multi Bottoms/Multi Bottoms/multibottoms/src/main/java/io/selimdawa/multibottoms/new3/NavigationComponentHelper.kt)
- Simplify destination matching logic.
- Use Kotlin idioms for list iteration and property access.

#### [MODIFY] [ContextExt.kt](file:///D:/MyProjects/Library/Multi Bottoms/Multi Bottoms/multibottoms/src/main/java/io/selimdawa/multibottoms/new3/ext/ContextExt.kt)
- Make it even more concise.

## Verification Plan

### Automated Tests
- Build the project to ensure no compilation errors.
- Run `analyze_file` on `SmoothBottomBar.kt` again to verify warnings are gone.

### Manual Verification
- Deploy the app and verify the `SmoothBottomBar` (new3) still functions correctly (navigation, animations, selection).
