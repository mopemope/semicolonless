import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SemicolonlessCompiler {

    public CompileResult compile(final List<File> sourceFileList, final File output) throws IOException {
        final JavaCompiler compiler = new BaseSemicolonlessCompiler();
        try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, StandardCharsets.UTF_8)) {
            final Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(sourceFileList);
            final DiagnosticCollector<JavaFileObject> diagnosticCollector = new DiagnosticCollector<>();
            final List<String> compileOptions = Arrays.asList(
                    "-g", "-deprecation",
                    "-d", output.getCanonicalPath(),
                    "-source", "1.8",
                    "-target", "1.8",
                    "-encoding", "UTF-8"
            );

            final JavaCompiler.CompilationTask task = compiler.getTask(null,
                    fileManager,
                    diagnosticCollector,
                    compileOptions,
                    null,
                    compilationUnits);
            final boolean result = task.call();
            return new CompileResult(result, diagnosticCollector.getDiagnostics());
        }
    }

    public class CompileResult {

        private final boolean success;
        private List<Diagnostic<? extends JavaFileObject>> diagnostics = new ArrayList<>();

        CompileResult(final boolean success,
                      final List<Diagnostic<? extends JavaFileObject>> diagnostics) {
            this.success = success;
            this.diagnostics = new ArrayList<>(diagnostics);
        }

        public boolean isSuccess() {
            return success;
        }

        public List<Diagnostic<? extends JavaFileObject>> getDiagnostics() {
            return diagnostics;
        }
    }
}
