PID=$(pgrep -f sbadmin-0.1-SNAPSHOT.jar)

if [ -z "$PID" ]
then
	echo Admin Server is not running
else
    	kill $PID
	echo Admin Server is stopping ...
fi


