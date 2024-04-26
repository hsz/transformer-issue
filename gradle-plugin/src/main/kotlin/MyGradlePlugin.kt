import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.attributes.Attribute
import org.gradle.api.internal.tasks.JvmConstants
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.registerTransform

class MyGradlePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extracted = Attribute.of("extracted", Boolean::class.javaObjectType)
        val collected = Attribute.of("collected", Boolean::class.javaObjectType)

        project.dependencies.attributesSchema {
            attribute(collected)
            attribute(extracted)
        }

        project.dependencies.artifactTypes.maybeCreate("zip")
            .attributes
            .attribute(extracted, false)
            .attribute(collected, false)

        val fooDependencyConfiguration = project.configurations.create("fooDependency") {
            isVisible = false
            isCanBeConsumed = false
            isCanBeResolved = true
        }
        val fooConfiguration = project.configurations.create("foo") {
            isVisible = false
            isCanBeConsumed = false
            isCanBeResolved = true

            attributes {
                attribute(extracted, true)
            }

            extendsFrom(fooDependencyConfiguration)
        }

        project.configurations[JvmConstants.COMPILE_ONLY_CONFIGURATION_NAME].extendsFrom(
            fooConfiguration,
        )

        project.configurations[JvmConstants.COMPILE_CLASSPATH_CONFIGURATION_NAME]
            .attributes
            .attribute(extracted, true)
            .attribute(collected, true)

        project.dependencies.registerTransform(ExtractorTransformer::class) {
            from.attribute(extracted, false)
            to.attribute(extracted, true)
        }
        project.dependencies.registerTransform(CollectorTransformer::class) {
            from.attribute(collected, false)
            to.attribute(collected, true)
        }
    }
}
