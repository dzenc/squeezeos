DESCRIPTION = "The Fraunhofer FDK AAC Codec Library."
SECTION = "libs"
LICENSE = "Apache 2.0"

PR="r0"

SRC_URI = "${RALPHY_SQUEEZEOS}/${PN}-${PV}-072f2cd.tar.gz \
           file://remove-encoders.patch;patch=1 \
"
S="${WORKDIR}/${PN}-${PV}"

ARM_INSTRUCTION_SET = "arm"

# Optimize more
FULL_OPTIMIZATION = "-fexpensive-optimizations -fomit-frame-pointer -frename-registers -O2 -ggdb -feliminate-unused-debug-types"

inherit autotools

EXTRA_OECONF = "--enable-static=no --enable-shared=yes"

do_stage() {
	autotools_stage_all
}

PACKAGES += "libfdk-aac"

FILES_libfdk-aac = "${libdir}/libfdk-aac.so.*"
