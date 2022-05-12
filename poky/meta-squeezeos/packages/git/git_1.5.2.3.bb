DESCRIPTION = "The git revision control system used by the Linux kernel developers"
SECTION = "console/utils"
LICENSE = "GPL"
PR="r4"

inherit autotools

SRC_URI = "https://git.kernel.org/pub/scm/git/git.git/snapshot/git-${PV}.tar.gz \
	   file://autotools.patch;patch=1 \
	  "

S = "${WORKDIR}/git-${PV}"

DEPENDS = "openssl curl zlib expat"
RDEPENDS = "perl perl-module-file-path cpio findutils sed"

EXTRA_OEMAKE = "NO_TCLTK=1"

do_install() {
	oe_runmake install DESTDIR="${D}" bindir="${bindir}" \
		template_dir="${datadir}/git-core/templates" \
		GIT_PYTHON_DIR="${datadir}/git-core/python"
}

FILES_${PN} += "${datadir}/git-core"

