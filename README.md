## Welcome to Controller Remap (Beta)

This is a mod for Minecraft Java Edition that allows you to use a PS4 or Xbox One controller or even a custom controller with [custom mappings!](#mappings)

## Download
This mod is for 1.13 and 1.14
The reason is because of what new updated libraries 1.13+ bring that allow this to even be possible. GLFW allows controllers to be used is only included in LWJGL3 which is only available Minecraft 1.13+

Version releases can be found here: [https://github.com/Fernthedev/controller-remap/releases](https://github.com/Fernthedev/controller-remap/releases)

## Features

- Native support for Xbox One and/or PS4 controller (Only 1 controller at once)
- Support for mappings for [custom controllers](#mappings)
- Controls are based on the console versions of Minecraft.
- Settings (can be found by pressing down d-pad arrow or by going to options) 

  - Sensitivity settings
  - Deadzone (left/right joystick)
  - Mapping chooser
  - Drop Speed (in ticks) How long is the drop button (B) needed to be held for it to start dropping items
  - Scroll Speed (in ticks) How long is the scroll button (Bumper Left/Right) needed to be held for it to start scrolling.
  
- Toggle Sprint (D-Pad right)
- Auto Attack (hold right trigger) with adjustable delay in settings


### Known Bugs or missing features 

- ~~You can only place blocks with left trigger, features such as shields or bows do not work. A workaround is being worked on, no promises though.~~ This was fixed in version [1.2.2](https://github.com/Fernthedev/controller-remap/releases/tag/1.2.2)
- ~~You can't navigate through menus or inventory. I have no idea how to implement this yet, until then just attempt to use your mouse.~~ This was fixed in version [1.2.4](https://github.com/Fernthedev/controller-remap/releases/tag/1.2.4)
- The camera randomly moves slower/faster with same sensitivity and/or it stutters. This is an issue with how it's been implemented since it is basically frame bound. This allows it to look smooth in over 100 fps but incosistent with different frame rates. ~~A workaround for this is being worked on, but the result is very slow and stuttery for now.~~
- Vehicles such as boats do not move with controller. This a issue and is being looked into, for now use WASD.

### Mappings

Mappings are JSON files that tell the mod what buttons are on your controller through their respective IDs. The default mappings (and where they should be stored) can be found in the config folder, inside of the mappings folder. There is a template file (which is the same as the Xbox One mapping) for use as a base for creating your controller mapping. This is how it looks:

Format:

Button :: ButtonID (GLFW/LWJGL button ID)

You can get the button ids using [this (not created by me)](https://github.com/Fernthedev/controller-remap/releases/download/1.2.2nonver/ControllerTest.jar)
```json
{
  "buttonMapping": {
    "A": 0,
    "B": 1,
    "X": 2,
    "Y": 3,
    "BUMPER_LEFT": 4,
    "BUMPER_RIGHT": 5,
    "EXTRA_BUTTON": 6,
    "START_BUTTON": 7,
    "LEFT_STICKER": 8,
    "RIGHT_STICKER": 9,
    "DPAD_UP": 10,
    "DPAD_RIGHT": 11,
    "DPAD_DOWN": 12,
    "DPAD_LEFT": 13
  },
  "axesMapping": {
    "HORIZONTAL_LEFT_STICKER": 0,
    "VERTICAL_LEFT_STICKER": 1,
    "VERTICAL_RIGHT_STICKER": 2,
    "HORIZONTAL_RIGHT_STICKER": 3,
    "LEFT_TRIGGER": 4,
    "RIGHT_TRIGGER": 5
  },
  "name": "XboxOne"
}
```

### Support or Contact
Create an issue for bug reports, feature requests or help in general.
