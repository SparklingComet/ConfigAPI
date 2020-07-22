# ConfigAPI
Create custom config files for your bukkit plugins through this small API.

Due to the insane amount of questions of the type "How do I create 'custom' configuration files?", I have decided to write a small class which you can easily embed in your own project, and which adds a small API which creates all the "custom" config files and directories you need. You are free to generate your own javadocs for it or download the HTML files from this repository for them.

To write/read from the config file, use ``YamlConfig.getConfig().set(String, Object)`` and ``YamlConfig.getConfig().get(String)``.

The original thread can be found on [SpigotMC](https://www.spigotmc.org/threads/api-multiple-configuration-files-and-directories.202492/) and the online api docs can either be downloaded [here](https://www.github.com/SparklingComet/ConfigAPI/tree/master/doc) or viewed online [on this page](https://SparklingComet.github.io/ConfigAPI/doc/).

*Current version:* ***2.0.1_2**
