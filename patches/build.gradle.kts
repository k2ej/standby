group = "com.r3d.patchlab"

patches {
    about {
        name = "R3D PatchLab"
        description = "Custom patches for Android apps"
        source = "git@github.com:xxxR3Dxxx/R3D-PatchLab.git"
        author = "R3D"
        contact = "https://github.com/xxxR3Dxxx"
        website = "https://github.com/xxxR3Dxxx/R3D-PatchLab"
        license = "GPLv3"
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xcontext-parameters")
    }
}

// Separate configuration so gson is available at runtime for the
// generatePatchesList task but never bundled into the APK.
val patchListGeneratorClasspath = configurations.create("patchListGeneratorClasspath")

dependencies {
    compileOnly(libs.gson)
    patchListGeneratorClasspath(libs.gson)
}

tasks {
    register<JavaExec>("generatePatchesList") {
        description = "Build patch with patch list"

        dependsOn(build)

        classpath = sourceSets["main"].runtimeClasspath + patchListGeneratorClasspath
        mainClass.set("util.PatchListGeneratorKt")
    }

    // Used by gradle-semantic-release-plugin.
    publish {
        dependsOn("generatePatchesList")
    }
}
