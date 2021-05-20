DESCRIPTION = "The Fraunhofer FDK AAC Codec Library."
SECTION = "libs"
LICENSE = "Apache 2.0"

PR="r1"

SRC_URI = "${RALPHY_SQUEEZEOS}/${PN}-${PV}-801f67f.tar.gz"

S="${WORKDIR}/${PN}-${PV}"

ARM_INSTRUCTION_SET = "arm"

inherit autotools

EXTRA_OECONF = "--enable-static=no --enable-shared=yes"

do_stage() {
	autotools_stage_all
}

PACKAGES += "libfdk-aac"

FILES_libfdk-aac = "${libdir}/libfdk-aac.so.*"
