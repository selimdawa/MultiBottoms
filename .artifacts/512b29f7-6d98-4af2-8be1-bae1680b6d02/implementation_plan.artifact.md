# Fix Crash in new3.MainActivity

The crash in `new3.MainActivity` is primarily caused by incorrect fragment class names in the navigation graph and an attempt to setup the Action Bar in a theme that doesn't have one.

## User Review Required

> [!IMPORTANT]
> The `nav_graph.xml` was pointing to fragment classes in the `me.ibrahimsn.smoothbottombar` package, which do not exist in your project. I will update these to point to `com.flatcode.multibottoms.new3`.

> [!NOTE]
> I will also remove the `setupActionBarWithNavController` call in `MainActivity` because your current theme (`Theme.Material3.DayNight.NoActionBar`) does not include an Action Bar, which would cause a crash.

## Proposed Changes

### App Module

#### [MODIFY] [nav_graph.xml](file:///D:/MyProjects/Library/Multi Bottoms/Multi Bottoms/app/src/main/res/navigation/nav_graph.xml)
- Update fragment `android:name` attributes to use the correct package `com.flatcode.multibottoms.new3`.

#### [MODIFY] [MainActivity.kt](file:///D:/MyProjects/Library/Multi Bottoms/Multi Bottoms/app/src/main/java/com/flatcode/multibottoms/new3/MainActivity.kt)
- Remove `setupActionBarWithNavController(navController)` to prevent `IllegalStateException` with `NoActionBar` theme.
- Use a safer way to retrieve `NavController`.

### Library Module

#### [MODIFY] [SmoothBottomBar.kt](file:///D:/MyProjects/Library/Multi Bottoms/Multi Bottoms/multibottoms/src/main/java/io/selimdawa/multibottoms/new3/SmoothBottomBar.kt)
- Add a safety check in the `itemMenuRes` setter to prevent inflating an invalid menu ID (-1), which can happen during initialization.

## Verification Plan

### Automated Tests
- I will attempt a Gradle build to ensure there are no compilation errors after the changes.

### Manual Verification
- The user should run the app and navigate to the third option (new3) in `HomeActivity`. `MainActivity` should now open without crashing and display the fragments correctly.
