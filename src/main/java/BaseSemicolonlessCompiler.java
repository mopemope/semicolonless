import com.sun.tools.javac.api.JavacTaskImpl;
import com.sun.tools.javac.parser.SemicolonlessParserFactory;
import com.sun.tools.javac.util.Context;

import javax.annotation.processing.Processor;
import javax.lang.model.SourceVersion;
import javax.tools.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Set;

public class BaseSemicolonlessCompiler implements JavaCompiler{
    private final JavaCompiler javaCompiler;

    public BaseSemicolonlessCompiler(){
        this.javaCompiler = ToolProvider.getSystemJavaCompiler();
    }

    @Override
    public CompilationTask getTask(Writer out, JavaFileManager fileManager, DiagnosticListener<? super JavaFileObject> diagnosticListener, Iterable<String> options, Iterable<String> classes, Iterable<? extends JavaFileObject> compilationUnits) {
        final JavacTaskImpl task = (JavacTaskImpl) javaCompiler.getTask(out, fileManager, diagnosticListener, options, classes, compilationUnits);
        replaceParser(task);
        return new CompilationTask() {
            @Override
            public void setProcessors(Iterable<? extends Processor> processors) {
                task.setProcessors(processors);
            }

            @Override
            public void setLocale(Locale locale) {
                task.setLocale(locale);
            }

            @Override
            public Boolean call() {
                return task.call();
            }
        };
    }

    @Override
    public StandardJavaFileManager getStandardFileManager(DiagnosticListener<? super JavaFileObject> diagnosticListener, Locale locale, Charset charset) {
        return this.javaCompiler.getStandardFileManager(diagnosticListener, locale, charset);
    }

    @Override
    public int isSupportedOption(String option) {
        return 0;
    }

    @Override
    public int run(InputStream in, OutputStream out, OutputStream err, String... arguments) {
        return this.javaCompiler.run(in, out, err, arguments);
    }

    @Override
    public Set<SourceVersion> getSourceVersions() {
        return this.javaCompiler.getSourceVersions();
    }

    private void replaceParser(final JavacTaskImpl compilerTask) {
        final Context context = compilerTask.getContext();
        SemicolonlessParserFactory.instance(context);
    }

}
