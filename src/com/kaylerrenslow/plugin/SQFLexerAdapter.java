package com.kaylerrenslow.plugin;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

/**
 * Created by Kayler on 10/31/2015.
 */
public class SQFLexerAdapter extends FlexAdapter{
	public SQFLexerAdapter() {
		super(new SQFLexer((Reader) null));
	}
}
