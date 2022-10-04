DESCRIPTION = "stress"
SECTION = "bin"
LICENSE = "GPL"

PR = "r0"

SRC_URI="http://repository.timesys.com/buildsources/s/${PN}/${PN}-${PV}/${PN}-${PV}.tar.gz"

S = "${WORKDIR}/stress-${PV}"

inherit autotools

PACKAGES = "stress"
FILES_${PN} = "${bindir}/*"
