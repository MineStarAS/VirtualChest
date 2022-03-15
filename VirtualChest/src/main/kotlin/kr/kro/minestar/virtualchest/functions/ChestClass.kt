package kr.kro.minestar.virtualchest.functions

import kr.kro.minestar.virtualchest.functions.chest.Chest
import org.bukkit.entity.Player
import org.reflections.Reflections
import org.reflections.scanners.SubTypesScanner
import java.util.stream.Collectors

object ChestClass {
    val openedChest = hashSetOf<Chest>()

    val materialChestList = classList("kr.kro.minestar.virtualchest.functions.material.chest")

    val playerChest = hashMapOf<Player,HashMap<String, MaterialChest>>()


    fun classList(packageName: String): MutableSet<Class<out Any>> {
        val reflections = Reflections(packageName, SubTypesScanner(false))
        return reflections.getSubTypesOf(Any::class.java).stream().collect(Collectors.toSet())
    }
}