DESCRIPTION = "wpa-supplicant"
SECTION = "base"
LICENSE = "GNU GPL"

PR = "r2"

SRC_URI = "http://w1.fi/releases/wpa_supplicant-${PV}.tar.gz \
	   file://bogus-SSID-too-long_2.9.patch;patch=1;pnum=0 \
	   file://Revert-wext-Increase-scan-timeout_2.9.patch;patch=1;pnum=0 \
	   file://defconfig"

SRC_URI_append_baby = " \
	file://wifi_interface_up_baby \
	file://wifi_disconnect_baby \
	"

S = "${WORKDIR}/wpa_supplicant-${PV}/wpa_supplicant"

CFLAGS_prepend = '-DCONFIG_WPA_CLI_HISTORY_DIR=\\"/tmp\\"'

# With the csl2010q1 and high optimization it fails. 'arm' instruction set to be safe
ARM_INSTRUCTION_SET = "arm"
FULL_OPTIMIZATION = "-O1 -s"

inherit autotools

do_configure () {
	install -m 0644 ${WORKDIR}/defconfig ${S}/.config
}

do_compile() {
##	make clean
	oe_runmake all
}

do_install() {
	install -m 0755 -d ${D}${sbindir}

	# Install wpa_supplicant, wpa_cli
	install -m 0755 ${S}/wpa_supplicant ${D}${sbindir}/wpa_supplicant
	install -m 0755 ${S}/wpa_cli ${D}${sbindir}/wpa_cli
}

do_install_append_baby() {
	# network script - ensure interface (eth1) is raised back 'up' after 'ifdown'
	install -m 0755 -d ${D}${sysconfdir}/network/if-post-down.d
	install -m 0755 ${WORKDIR}/wifi_interface_up_baby ${D}${sysconfdir}/network/if-post-down.d/wifi_interface_up
	install -m 0755 -d ${D}${sysconfdir}/network/if-down.d
	install -m 0755 ${WORKDIR}/wifi_disconnect_baby ${D}${sysconfdir}/network/if-down.d/wifi_disconnect
}

PACKAGES = "wpa-supplicant-dbg wpa-supplicant"

FILES_wpa-supplicant = "${sbindir} ${sysconfdir}"
FILES_wpa-supplicant-dbg = "${sbindir}.debug"

