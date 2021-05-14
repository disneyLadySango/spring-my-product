#!/bin/bash
java=/usr/java/default
prog=memo
prog_version=0.0.1
jarprog=target/${prog}-${prog_version}.jar

pidfile=/var/run/${prog}.pid
logfile=/var/log/${prog}.log
lockfile=/var/lock/subsys/${prog}

RETVAL=0

# System configuration
unset TMPDIR

if [ -f /etc/sysconfig/${prog} ]; then
   . /etc/sysconfig/${prog}
fi

# Source function library
if [ -f /etc/init.d/functions ] ; then
    . /etc/init.d/functions
elif [ -f /etc/rc.d/init.d/functions ] ; then
    . /etc/rc.d/init.d/functions
else
    exit 1
fi

start() {
    # Profile setting
    if [ "$1" = "" ]; then
        echo "[error] 起動プロファイルが指定されていません."
        exit
    fi
    javaoptions=-Dspring.profiles.active=$1
    cmdline="${java}/bin/java -jar ${javaoptions} ${jarprog}"

    echo -n $"Starting ${prog} services: "
    daemon --pidfile=${pidfile} "${cmdline} &"
    RETVAL=$?
    pgrep -f "${jarprog}" > ${pidfile}
    echo
    [ $RETVAL -eq 0 ] && touch ${lockfile} || RETVAL=1
    return $RETVAL
}

stop() {
    echo -n $"Stopping ${prog} service: "
    killproc -p ${pidfile} ${prog}
    RETVAL=$?
    echo
    [ $RETVAL -eq 0 ] && rm -f ${lockfile} ${pidfile}
    return $RETVAL
}

case "$1" in
     start)
          start $2
          ;;
     stop)
          stop
          ;;
     status)
          status -p ${pidfile} ${prog}
          RETVAL=$?
          ;;
     restart)
          stop
          start $2
          ;;
     *)
     echo $"Usage: ${prog} {start|stop|status|restart}: "
     RETVAL=2
esac

exit $RETVAL



