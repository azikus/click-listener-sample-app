import org.gradle.api.artifacts.dsl.DependencyHandler

class Dependency private constructor(
    val configuration: String,
    val dependency: String
) {
    companion object {
        fun androidTestImplementation(dependency: String) = Dependency("androidTestImplementation", dependency)
        fun implementation(dependency: String) = Dependency("implementation", dependency)
        fun kapt(dependency: String) = Dependency("kapt", dependency)
        fun testImplementation(dependency: String) = Dependency("testImplementation", dependency)
        fun compileOnly(dependency: String) = Dependency("compileOnly", dependency)
    }
}

fun DependencyHandler.add(dependency: Dependency) =
    add(dependency.configuration, dependency.dependency)

fun DependencyHandler.addAll(dependencies: List<Dependency>) =
    dependencies.forEach { add(it) }
