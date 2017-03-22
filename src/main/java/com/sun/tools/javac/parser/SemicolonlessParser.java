package com.sun.tools.javac.parser;

import static com.sun.tools.javac.parser.Tokens.TokenKind.SEMI;

public class SemicolonlessParser extends JavacParser {

    protected SemicolonlessParser(final SemicolonlessParserFactory parserFactory,
                                  final Lexer lexer,
                                  final boolean b,
                                  final boolean b1,
                                  final boolean b2) {
        super(parserFactory, lexer, b, b1, b2);
    }

    @Override
    public void accept(final Tokens.TokenKind tk) {
        if (token.kind == tk) {
            nextToken();
        } else if (tk == SEMI) {
        } else {
            super.accept(tk);
        }
    }
}
