package com.sun.tools.javac.parser;


import com.sun.tools.javac.util.Context;

public class SemicolonlessParserFactory extends ParserFactory {

    protected SemicolonlessParserFactory(final Context context) {
        super(context);
    }

    public static SemicolonlessParserFactory instance(final Context context) {
        SemicolonlessParserFactory instance = (SemicolonlessParserFactory) context.get(parserFactoryKey);
        if (instance == null) {
            instance = new SemicolonlessParserFactory(context);
        }
        return instance;
    }

    @Override
    public JavacParser newParser(final CharSequence input,
                                 final boolean keepDocComments,
                                 final boolean keepEndPos,
                                 final boolean keepLineMap) {
        final Lexer lexer = scannerFactory.newScanner(input, keepDocComments);
        return new SemicolonlessParser(this, lexer, keepDocComments, keepLineMap, keepEndPos);
    }

}
