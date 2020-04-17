package me.mattstudios.outerspace.config

import ch.jalu.configme.Comment
import ch.jalu.configme.SettingsHolder
import ch.jalu.configme.properties.Property
import ch.jalu.configme.properties.PropertyInitializer.newProperty

object Settings : SettingsHolder {

    @JvmField
    @Comment("Value to determine the likely hood of a meteor shower to happen every 5 minutes")
    val METEOR_CHANCE: Property<Int> = newProperty("meteor-chance", 20)

}