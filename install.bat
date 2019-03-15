@echo off
xcopy  nativedist\*   platforms\android\app\src\main\assets\app /y /s /e
xcopy  nativedist\*   platforms\ios\app /y /s /e
pause



