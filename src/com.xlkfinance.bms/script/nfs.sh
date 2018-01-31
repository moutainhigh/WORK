#!/bin/sh 

MOUNTPOINT="/app/apache-tomcat-7.0.59/webapps/BMS/nfs"
REMOTEPOINT="192.168.99.111:/home/nfsroot"
CHOSED=$1

doUMount(){
    umount $MOUNTPOINT
}

doMount(){
    mount -t nfs $REMOTEPOINT $MOUNTPOINT
}


if [ "$CHOSED" = "umount" ]; then
	doUMount
else
	doMount
fi