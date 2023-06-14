#!/bin/sh
trap "" HUP INT QUIT TSTP 

# stop ARP watchdog (will restart in wlan start, if so configured)
killall watch-arp.sh

# stop wifi driver
/etc/init.d/wlan stop

# start wifi driver
/etc/init.d/wlan start

#only do this if eth0 state is down (not connected to RJ45)
ETH0_UP_COUNT=`ip link | grep eth0 | grep "state UP" | wc -l`
if [ $ETH0_UP_COUNT -eq 0 ]; then
	# stop dhcp
	killall udhcpc 

	# make sure we reconnect to AP
	wpa_cli reassociate

	# restart dhcp
	udhcpc -R -a -p/var/run/udhcpc.eth1.pid -b --syslog -ieth1 -H `hostname` -s /etc/network/udhcpc_action
fi
