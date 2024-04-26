import org.gradle.api.artifacts.transform.InputArtifact
import org.gradle.api.artifacts.transform.TransformAction
import org.gradle.api.artifacts.transform.TransformOutputs
import org.gradle.api.artifacts.transform.TransformParameters
import org.gradle.api.file.ArchiveOperations
import org.gradle.api.file.FileSystemLocation
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Classpath
import javax.inject.Inject

abstract class ExtractorTransformer : TransformAction<TransformParameters.None> {

    @get:Inject
    abstract val archiveOperations: ArchiveOperations

    @get:Inject
    abstract val fileSystemOperations: FileSystemOperations

    @get:InputArtifact
    @get:Classpath
    abstract val inputArtifact: Provider<FileSystemLocation>

    override fun transform(outputs: TransformOutputs) {
        val inputArchive = inputArtifact.get().asFile
        val outputDirectory = outputs.dir(inputArchive.nameWithoutExtension)
        val fileTree = archiveOperations.zipTree(inputArchive)

        println("ExtractorTransformer")
        println("  inputArchive = $inputArchive")
        println("  outputDirectory = $outputDirectory")

        fileSystemOperations.copy {
            includeEmptyDirs = false
            from(fileTree)
            into(outputDirectory)
        }
    }
}
