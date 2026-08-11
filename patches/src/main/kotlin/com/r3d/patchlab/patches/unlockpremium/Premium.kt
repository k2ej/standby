@file:Suppress("DEPRECATION")

package com.r3d.patchlab.patches.unlockpremium

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.patch.ApkFileType
import app.morphe.patcher.patch.Compatibility
import com.r3d.patchlab.patches.pairip.disableLicenseCheckPatch

@Suppress("unused")
val forceTrueInXf8Patch = bytecodePatch(
    name = "Unlock Premium",
    description = "Unlock Premium Features."
) {
    dependsOn(disableLicenseCheckPatch)

    compatibleWith(
        Compatibility(
            packageName = "br.com.zetabit.ios_standby",
            name = "StandBy Mode",
            apkFileType = ApkFileType.APKM
        )
    )
    execute {
        val fingerprint = Fingerprint(
            definingClass = "Lxf8;",
            name = "invokeSuspend",
            returnType = "Ljava/lang/Object;",
            parameters = listOf("Ljava/lang/Object;")
        )

        fingerprint.methodOrNull?.apply {
            // Completely replace the method body → always return true
            addInstructions(
                0,
                """
                    const/4 v0, 0x1
                    invoke-static {v0}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;
                    move-result-object v0
                    return-object v0
                """.trimIndent()
            )
        } ?: throw Exception("Could not find xf8.invokeSuspend")
    }
}