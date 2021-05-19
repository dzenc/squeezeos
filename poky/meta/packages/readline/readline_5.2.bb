DESCRIPTION = "The GNU Readline library provides a set of functions for use by applications that allow users to edit \
command lines as they are typed in. Both Emacs and vi editing modes are available. The Readline library includes  \
additional functions to maintain a list of previously-entered command lines, to recall and perhaps reedit those   \
lines, and perform csh-like history expansion on previous commands."
SECTION = "libs"
PRIORITY = "optional"
LICENSE = "GPLv2"
DEPENDS += "ncurses"
RPROVIDES_${PN} += "readline"
LEAD_SONAME = "libreadline.so"
PR = "r5"

SRC_URI = "ftp://ftp.gnu.org/gnu/readline/readline-${PV}.tar.gz \
           file://configure_fix.patch;patch=1 \
           file://readline52-001.patch;patch=1 \
           file://readline52-002.patch;patch=1 \
           file://readline52-003.patch;patch=1 \
           file://readline52-004.patch;patch=1 \
           file://readline52-005.patch;patch=1 \
           file://readline52-006.patch;patch=1 \
           file://readline52-007.patch;patch=1 \
           file://readline52-008.patch;patch=1 \
           file://readline52-009.patch;patch=1 \
           file://readline52-010.patch;patch=1 \
           file://readline52-011.patch;patch=1 \
           file://readline52-012.patch;patch=1 \
           file://readline52-013.patch;patch=1 \
           file://readline52-014.patch;patch=1 \
           file://acinclude.m4"

S = "${WORKDIR}/readline-${PV}"

inherit autotools_stage

# Avoid autoconf 2.62+ cannot run test program while cross compiling mbstate_t configure error.
export bash_cv_have_mbstate_t=yes

do_configure () {
	install -m 0644 ${WORKDIR}/acinclude.m4 ${S}/
	autotools_do_configure
}

do_install () {
	autotools_do_install
	# Make install doesn't properly install these
	oe_libinstall -so -C shlib libhistory ${D}${libdir}
	oe_libinstall -so -C shlib libreadline ${D}${libdir}
}
