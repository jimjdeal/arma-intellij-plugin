package com.kaylerrenslow.armaplugin.lang.sqf.psi;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

/**
 * @author Kayler
 * @since 05/23/2017
 */
public abstract class SQFAddExpression extends ASTWrapperPsiElement implements SQFExpression, SQFBinaryExpression {
	public SQFAddExpression(@NotNull ASTNode node) {
		super(node);
	}

	@NotNull
	@Override
	public SQFExpression getLeft() {
		return null;
	}

	@NotNull
	@Override
	public SQFExpression getRight() {
		return null;
	}
}