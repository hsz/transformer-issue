import org.gradle.api.artifacts.transform.InputArtifact
import org.gradle.api.artifacts.transform.TransformAction
import org.gradle.api.artifacts.transform.TransformOutputs
import org.gradle.api.artifacts.transform.TransformParameters
import org.gradle.api.file.FileSystemLocation
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Classpath

abstract class CollectorTransformer : TransformAction<TransformParameters.None> {

    @get:InputArtifact
    @get:Classpath
    abstract val inputArtifact: Provider<FileSystemLocation>

    override fun transform(outputs: TransformOutputs) {
        val inputArchive = inputArtifact.get().asFile

        println("CollectorTransformer")
        println("  inputArchive = $inputArchive")

        inputArchive
            .resolve("lib")
            .listFiles { file -> file.extension == "jar" }
            .orEmpty()
            .forEach {
                println("  lib = $it")
                outputs.file(it)
            }
    }
}
