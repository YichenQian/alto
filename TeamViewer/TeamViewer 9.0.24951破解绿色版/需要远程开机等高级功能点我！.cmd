@Echo Off
Title TeamViewer9 服务注册
Pushd %~dp0
start /wait /B "" "%~dp0TeamViewer_Service.exe" -install
msg  %username% /time:10  服务注册完成！感谢使用！
exit
