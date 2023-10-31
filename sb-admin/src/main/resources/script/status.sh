PID=$(pgrep -f sbadmin-0.1-SNAPSHOT.jar)

if [ -z "$PID" ]
then
	echo Admin Server is not running
else
	echo Admin Server is running
fi


