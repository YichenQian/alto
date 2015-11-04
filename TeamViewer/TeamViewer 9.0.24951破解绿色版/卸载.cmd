@Echo Off
Title TeamViewer9卸载工具
Pushd %~dp0
sc stop TeamViewer9
sc delete TeamViewer9
reg delete HKEY_CURRENT_USER\Software\TeamViewer\Version9 /f>NUL 2>NUL
reg delete HKEY_LOCAL_MACHINE\SOFTWARE\TeamViewer\Version9 /f>NUL 2>NUL
reg delete HKEY_LOCAL_MACHINE\SOFTWARE\Wow6432Node\TeamViewer\Version9 /f>NUL 2>NUL
Rd /s /q "%AppData%\TeamViewer" >nul 2>nul
msg  %username% /time:10  卸载完成！感谢使用！
exit
