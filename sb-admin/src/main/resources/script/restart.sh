PID=$(pgrep -f sbadmin-0.1-SNAPSHOT.jar)

if [ -z "$PID" ]
then
	echo SB-Admin is not running
else
    kill $PID
	echo SB-Admin is stopping ...
	echo
fi

PID=$(pgrep -f sbadmin-0.1-SNAPSHOT.jar)
while [ "$PID" ]
do
	sleep 2s
	PID=$(pgrep -f sbadmin-0.1-SNAPSHOT.jar)
done

nohup java -jar sbadmin-0.1-SNAPSHOT.jar > /dev/null 2>&1 &
echo Admin Server started ...

./vlog.sh

