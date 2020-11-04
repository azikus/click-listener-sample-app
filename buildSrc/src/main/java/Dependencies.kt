object Versions {
    const val gradle = "4.1.0"
    const val kotlin = "1.4.10"
    const val coroutines = "1.3.9"

    const val androidXCore = "1.3.2"
    const val androidXAppCompat = "1.2.0"
    const val androidXConstraintLayout = "2.0.4"
    const val androidXLifecycle = "2.2.0"
    const val androidXFragment = "1.2.5"
    const val androidXMaterial = "1.2.1"
    const val androidXHilt = "2.28-alpha"
    const val androidXHiltViewModel = "1.0.0-alpha01"

    const val ktor = "1.4.1"
    const val timber = "4.7.1"

    const val junit = "4.12"
    const val mockito = "2.21.0"
}

object Dependencies {
    val kotlin: List<Dependency> = listOf(
        Dependency.implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"),
        Dependency.implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}")
    )
    val kotlinCoroutinesAndroid = listOf(
        Dependency.implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}")
    )
    val androidX = listOf(
        Dependency.implementation("androidx.core:core-ktx:${Versions.androidXCore}"),
        Dependency.implementation("androidx.appcompat:appcompat:${Versions.androidXAppCompat}"),
        Dependency.implementation("androidx.constraintlayout:constraintlayout:${Versions.androidXConstraintLayout}"),
        Dependency.implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.androidXLifecycle}"),
        Dependency.implementation("androidx.fragment:fragment-ktx:${Versions.androidXFragment}"),
        Dependency.implementation("com.google.android.material:material:${Versions.androidXMaterial}"),
        Dependency.implementation("com.google.dagger:hilt-android:${Versions.androidXHilt}"),
        Dependency.kapt("com.google.dagger:hilt-android-compiler:${Versions.androidXHilt}"),
        Dependency.implementation("androidx.hilt:hilt-lifecycle-viewmodel:${Versions.androidXHiltViewModel}"),
        Dependency.kapt("androidx.hilt:hilt-compiler:${Versions.androidXHiltViewModel}")
    )
    val ktor = listOf(
        Dependency.implementation("io.ktor:ktor-client-android:${Versions.ktor}"),
        Dependency.implementation("io.ktor:ktor-client-cio:${Versions.ktor}"),
        Dependency.implementation("io.ktor:ktor-client-serialization-jvm:${Versions.ktor}"),
        Dependency.implementation("io.ktor:ktor-client-logging-jvm:${Versions.ktor}")
    )
    val timber = listOf(
        Dependency.implementation("com.jakewharton.timber:timber:${Versions.timber}")
    )
    val unitTest = listOf(
        Dependency.testImplementation("junit:junit:${Versions.junit}"),
        Dependency.testImplementation("org.mockito:mockito-core:${Versions.mockito}"),
        Dependency.testImplementation("org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}")
    )
    val androidTest = listOf(
        Dependency.androidTestImplementation("androidx.test.ext:junit:1.1.2"),
        Dependency.androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    )
}
