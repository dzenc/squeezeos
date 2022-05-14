DESCRIPTION = "System watchdog daemon"
LICENSE = "GPL"
PR = "r6"

SRC_URI = " \
	https://mucross.com/downloads/tonga-linux/sources/GPL/watchdog/${PN}-${PV}.tar.gz \
	file://memory-buffers-cache.patch;patch=1 \
	file://watchdog-semaphore.patch;patch=1 \
	file://startup-delay.patch;patch=1 \
	"

inherit autotools

FILES_${PN} = "${sbindir}/watchdog"
