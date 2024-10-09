#!/bin/sh

#Stop existing server with a HUP signal
PID=`ps ax | grep [w]eblogic.Server | awk '{print $1; }'`
if [ -z "$PID" ]; then
    echo "Not running"
else
    echo "Stopping process, enter root password"
    su -c "echo \"$PID\" | xargs kill -s 1"
fi  

# Start server with an expect script
cd  /usr/local/bea/user_projects/domains/ISFDomain 
echo "Restarting, enter root password"
su -c 'screen -d -m ./startWebLogic.sh'
echo "Weblogic Server started"

