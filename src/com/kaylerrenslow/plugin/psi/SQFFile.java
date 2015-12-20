package com.kaylerrenslow.plugin.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.kaylerrenslow.plugin.SQFFileType;
import com.kaylerrenslow.plugin.SQFLanguage;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Created by Kayler on 10/31/2015.
 */
public class SQFFile extends PsiFileBase{
	public SQFFile(FileViewProvider viewProvider) {
		super(viewProvider, SQFLanguage.INSTANCE);
	}

	@NotNull
	@Override
	public FileType getFileType(){
		return SQFFileType.INSTANCE;
	}

	/*
	@Override
	public String toString(){
		return "SQF File";
	}
	*/

	@Override
	public Icon getIcon(int flags) {
		return super.getIcon(flags);
	}
}
