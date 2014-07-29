GameCore, a 2D game java library
================================

Versatile Java game engine with pluggable backends

This engine aims to be platform and backend independent, clean and simple.

It's a bootstrap for 2D java games.

*(You could probably do 3D in it as well, with the LWJGL backend and LWJGL functions. But it'd be ugly.)*


DEPENDENCIES
------------

If you intend to **build it from source**, you will need those Eclipse projects in your workspace:

- [MightyPork/mightyutils](https://github.com/MightyPork/mightyutils) - Game utils



BACKENDS
--------

Backends provide implementation to some abstract GameCore modules:

- [MightyPork/gamecore-lwjgl](https://github.com/MightyPork/gamecore-lwjgl) - LWJGL backend for GameCore



How to use it
=============

TODO: Better docs and demo

Basically, all you have to do is make an instance of `App`, with a backend of choice (only LWJGL now).


It's all about init tasks
-------------------------

You then add some `InitTask`s to the `App`, maybe some plugins, and `start()` it.

This can be done in the constructor, or somewhere else, that doesn't really matter.

All of the game configuration is done using the `InitTask`s. There is a bunch of init tasks already added, each task has a name and a list of tasks it depends upon. It also has a priority, which affects order (but dependencies must already be loaded).

You can add task to setup `WorkDir`, to define config file locations and contents, to set up window size, to load resources and so on.


Not really, it's all about EventBus
-----------------------------------

Once init tasks are initialized, the next phase comes. The `MainLoop` will be executed, providing delta timing to the whole game.

This is done via an `EventBus` system, the core of the whole engine. Everything is done using Events. Well, almost.

- Delta timing is via events.
- Shutdown is done via event.
- Resource loading is via events
- All game logic can be done via events
- User input is via events
- ...

So yeah, lot of stuff.


The GUI
-------

The MainLoop renders something called "main renderable", or UI. What you put there is really up to you, but typically it's a `SreenRegistry` with some `Screen`s in it.

The UI receives timing events, user input events etc, and can react to them.

There is a little library of UI layouts and components that can be used, or you may make your own with no big hassle.


Make use of MightyUtils!
------------------------

There is a HUGE amount of cool utils in the MightyUtils library. One of them is even the EventBus itself.

There is a magnificent **math library** with **dynamic constraints**, the base of the GUI system. There is even a custom "serialization" system called **ION**.

**Check it out!**

