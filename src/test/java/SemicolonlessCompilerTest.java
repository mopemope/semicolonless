import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SemicolonlessCompilerTest {


    @Test
    public void testCompile() throws IOException {

        final File out = new File(System.getProperty("java.io.tmpdir"), "out");
        out.mkdirs();
        final SemicolonlessCompiler compiler = new SemicolonlessCompiler();
        final List<File> targets = new ArrayList<>();
        final File file = new File("./src/test/resources/Hello.java").getCanonicalFile();
        assertTrue(file.exists());
        targets.add(file);
        final SemicolonlessCompiler.CompileResult compileResult = compiler.compile(targets, out);
        assertEquals(true, compileResult.isSuccess());
    }
}