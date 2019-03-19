@echo off

xcopy  nativedist\*   platforms\android\app\src\main\assets\app /y /s /e
xcopy  nativedist\*   platforms\android\app\src\main\assets\app /y /s /e
xcopy  nativedist\image*   platforms\ios\app\image /y /s /e
pause



