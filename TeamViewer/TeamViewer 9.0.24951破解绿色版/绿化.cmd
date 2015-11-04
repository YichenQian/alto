@Echo Off
Title TeamViewer9 残留清理&绿化
Pushd %~dp0
sc stop TeamViewer9
sc delete TeamViewer9
reg delete HKEY_CURRENT_USER\Software\TeamViewer\Version9 /f>NUL 2>NUL
reg delete HKEY_LOCAL_MACHINE\SOFTWARE\TeamViewer\Version9 /f>NUL 2>NUL
reg delete HKEY_LOCAL_MACHINE\SOFTWARE\Wow6432Node\TeamViewer\Version9 /f>NUL 2>NUL
msg  %username% /time:10  卸载完成！感谢使用！
exit
