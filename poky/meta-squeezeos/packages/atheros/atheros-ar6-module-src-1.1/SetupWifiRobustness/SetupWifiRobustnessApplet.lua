
-- stuff we use
local tostring, unpack, pairs = tostring, unpack, pairs

local oo                     = require("loop.simple")
local io                     = require("io")
local os                     = require("os")
local math                   = require("math")
local string                 = require("string")

local Applet                 = require("jive.Applet")
local System                 = require("jive.System")
local Checkbox               = require("jive.ui.Checkbox")
local Framework              = require("jive.ui.Framework")
local Icon                   = require("jive.ui.Icon")
local Label                  = require("jive.ui.Label")
local Popup                  = require("jive.ui.Popup")
local SimpleMenu             = require("jive.ui.SimpleMenu")
local Textarea               = require("jive.ui.Textarea")
local Timer                  = require("jive.ui.Timer")
local Window                 = require("jive.ui.Window")
local Networking             = require("jive.net.Networking")

local jnt                    = jnt

module(..., Framework.constants)
oo.class(_M, Applet)

function settingsShow(self, menuItem)
	local settingsChanged = false
	local gonlyEnabled = _fileMatch("/etc/wlan.conf", "^gonly=on")
	local arpwatchEnabled = _fileMatch("/etc/wlan.conf", "^arpwatch=on")

	local window = Window("help_list", menuItem.text, 'settingstitle')
	local menu = SimpleMenu("menu", {
					{
						text = self:string("ARPWATCH_ENABLE"),
						style = 'item_choice',
						check = Checkbox("checkbox",
								function(_, isSelected)
									settingsChanged = true
									if isSelected then
										log:warn("wlan.conf setting arpwatch=on");
										_fileSub("/etc/wlan.conf", "^arpwatch=off", "arpwatch=on")  
									else
										log:warn("wlan.conf setting arpwatch=off");
										_fileSub("/etc/wlan.conf", "^arpwatch=on", "arpwatch=off")
									end
								end,
								arpwatchEnabled
							),
						focusGained = function(event)
							self.howto = Textarea("help_text", self:string("ARPWATCH_HOWTO"))
							self.menu:setHeaderWidget(self.howto)
							self.menu:reLayout()
						end
					},
					{
						text = self:string("GONLY_ENABLE"),
						style = 'item_choice',
						check = Checkbox("checkbox",
								function(_, isSelected)
									settingsChanged = true
									if isSelected then
										log:warn("wlan.conf setting gonly=on");
										_fileSub("/etc/wlan.conf", "^gonly=off", "gonly=on")  
									else
										log:warn("wlan.conf setting gonly=off");
										_fileSub("/etc/wlan.conf", "^gonly=on", "gonly=off")
									end
								end,
								gonlyEnabled
							),
						focusGained = function(event)
							self.howto = Textarea("help_text", self:string("GONLY_HOWTO"))
							self.menu:setHeaderWidget(self.howto)
							self.menu:reLayout()
						end
					},
				})

	window:addWidget(menu)
	
	window:addListener(EVENT_WINDOW_INACTIVE, 
		function()
			if settingsChanged then
				os.execute("/lib/atheros/restart-wifi.sh &")
			end
		end
	)        

	self.window = window
	self.menu = menu
	self:_addHelpInfo()

	self:tieAndShowWindow(window)
	return window
end
function _addHelpInfo(self)
	self.howto = Textarea("help_text", self:string("GONLY_HOWTO"))
	self.menu:setHeaderWidget(self.howto)

	self.window:focusWidget(self.menu)
end

function _fileMatch(file, pattern)
	local fi, err = io.open(file, "r")
	
	if (err) then 
		return false
	end

	for line in fi:lines() do
		if string.match(line, pattern) then
			fi:close()
			return true
		end

	end
	fi:close()

	return false
end

function _fileSub(file, pattern, repl)
        local data = ""

        local fi = io.open(file, "r")
        for line in fi:lines() do
                line = string.gsub(line, pattern, repl)
                data = data .. line .. "\n"
        end
        fi:close()

        System:atomicWrite(file, data)
end

--[[

=head1 LICENSE

This file is licensed under BSD. Please see the LICENSE file for details.

=cut
--]]
