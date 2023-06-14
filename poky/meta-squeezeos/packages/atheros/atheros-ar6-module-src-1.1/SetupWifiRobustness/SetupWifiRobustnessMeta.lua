
local oo            = require("loop.simple")

local AppletMeta    = require("jive.AppletMeta")

local appletManager = appletManager
local jiveMain      = jiveMain


module(...)
oo.class(_M, AppletMeta)


function jiveVersion(meta)
	return 1, 1
end


function registerApplet(meta)
	jiveMain:addItem(meta:menuItem('appletSetupWifiRobustness', 'networkSettings', "WIFI_ROBUSTNESS", function(applet, ...) applet:settingsShow(...) end, 110))
end


--[[

=head1 LICENSE

This file is licensed under BSD. Please see the LICENSE file for details.

=cut
--]]

