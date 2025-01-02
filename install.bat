@echo off
echo Installing coreutils...

REM Create installation directory
if not exist "C:\Program Files\coreutils" mkdir "C:\Program Files\coreutils"

REM Copy files
copy /Y "%~dp0\build\libs\coreutils.jar" "C:\Program Files\coreutils"
copy /Y "%~dp0\coreutils.bat" "C:\Program Files\coreutils"

REM Add to PATH (requires admin privileges)
setx PATH "%PATH%;C:\Program Files\coreutils" /M

echo.
echo Installation complete!
echo Please restart your terminal for the changes to take effect.
echo You can now use 'coreutils' from any directory.
