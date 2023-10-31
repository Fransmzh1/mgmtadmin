PID=$(pgrep -f sbadmin-0.1-SNAPSHOT.jar)

if [ -z "$PID" ] 
then
	nohup java -jar sbadmin-0.1-SNAPSHOT.jar > /dev/null 2>&1 &
	echo Admin Server started ...
else
	echo Admin Server  is already running
fi

./vlog.sh

