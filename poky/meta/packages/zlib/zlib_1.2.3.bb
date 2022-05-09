DESCRIPTION = "Zlib Compression Library"
SECTION = "libs"
PRIORITY = "required"
HOMEPAGE = "http://www.gzip.org/zlib/"
LICENSE = "zlib"
PR = "r7"

SRC_URI = "https://zlib.net/fossils/zlib-1.2.3.tar.gz \
		file://1.2.3.3.dfsg.patch.gz;patch=1 \
		file://visibility.patch;patch=1 \
		file://autotools.patch;patch=1 "

DEPENDS = "libtool-cross"

inherit autotools_stage

BBCLASSEXTEND = "native sdk"
