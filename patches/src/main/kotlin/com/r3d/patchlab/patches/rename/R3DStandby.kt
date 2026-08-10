@file:Suppress("DEPRECATION")

package com.r3d.patchlab.patches.rename

import app.morphe.patcher.patch.resourcePatch

@Suppress("unused")
val renameStandByPatch = resourcePatch(
    name = "Rename StandBy",
    description = "Changes the launcher name to R3D StandBy.",
    default = true
) {
    compatibleWith("br.com.zetabit.ios_standby")

    execute {
        document("res/values/strings.xml").use { document ->
            val strings = document.getElementsByTagName("string")

            for (i in 0 until strings.length) {
                val node = strings.item(i)
                val name = node.attributes
                    ?.getNamedItem("name")
                    ?.nodeValue

                if (name == "app_name_launcher") {
                    node.textContent = "R3D StandBy"
                    break
                }
            }
        }
    }
}