import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) throws IOException {

        final List<File> target = Arrays.stream(args)
                .map(File::new)
                .collect(Collectors.toList());
        final SemicolonlessCompiler compiler = new SemicolonlessCompiler();
        final File out = new File(System.getProperty("java.io.tmpdir"), "out");
        out.mkdirs();
        final SemicolonlessCompiler.CompileResult result = compiler.compile(target, out);
    }


}
