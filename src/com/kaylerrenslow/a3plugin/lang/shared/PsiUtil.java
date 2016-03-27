package com.kaylerrenslow.a3plugin.lang.shared;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kayler
 *         Created on 01/02/2016.
 */
public class PsiUtil{

	public static ASTNode getNextSiblingNotWhitespace(ASTNode node){
		ASTNode sibling = node.getTreeNext();
		while(sibling != null){
			if(sibling.getElementType() == TokenType.WHITE_SPACE){
				sibling = sibling.getTreeNext();
			}else{
				break;
			}
		}
		return sibling;
	}

	public static ASTNode getPrevSiblingNotWhitespace(ASTNode node){
		ASTNode sibling = node.getTreePrev();
		while(sibling != null){
			if(sibling.getElementType() == TokenType.WHITE_SPACE){
				sibling = sibling.getTreePrev();
			}else{
				break;
			}
		}
		return sibling;
	}

	/** Checks if the given node is a descendant of the given IElementType.<br>
	 *  If textContent is not null, this method will also check if the ancestor is of correct type and ancestor's text is equal to textContent.
	 * @param node node to check if has a ancestor of IElementType type
	 * @param type IElementType to check
	 * @param textContent null if to disregard text of ancestor, otherwise check if ancestor's text is equal to textContent
	 * @return true if node has ancestor of IElementType type and ancestor's text matches textContent. If textContent is null, text can be anything for ancestor.
	 */
	public static boolean isDescendantOf(ASTNode node, IElementType type, String textContent){
		return getAncestorWithType(node, type, textContent) != null;
	}

	/** Checks if the given node is an ancestor of the given IElementType. If it is, this method will return that ancestor. Otherwise, it will return null.<br>
	 *  If textContent is not null, this method will also check if the ancestor is of correct type and ancestor's text is equal to textContent.
	 * @param node node to check if has a parent of IElementType type
	 * @param type IElementType to check
	 * @param textContent null if to disregard text of ancestor, otherwise check if ancestor's text is equal to textContent
	 * @return node's ancestor if ancestor is of IElementType type if node's ancestor's text matches textContent. If textContent is null, text can be anything for ancestor.
	 */
	public static ASTNode getAncestorWithType(ASTNode node, IElementType type, String textContent){
		ASTNode parent = node.getTreeParent();
		boolean isChild = false;
		while(parent != null && !isChild){
			parent = parent.getTreeParent();
			if(parent == null){
				break;
			}
			isChild = parent.getElementType() == type && (textContent == null || parent.getText().equals(textContent));
		}
		return parent;
	}

	/**
	 * Checks if IElementType of both nodes are the same. Returns false if either are null.
	 *
	 * @param node1 ASTNode
	 * @param node2 ASTNode
	 * @return true if node1's element type is node2's element type, false otherwise
	 */
	public static boolean isSameElementType(ASTNode node1, ASTNode node2) {
		return node1 != null && node2 != null && node1.getElementType() == node1.getElementType();
	}

	/**
	 * Checks if IElementType of both elements are the same. Returns false if either are null.
	 *
	 * @param psiElement1 PsiElement
	 * @param psiElement2 PsiElement
	 * @return true if psiElement's element type is node2's element type, false otherwise
	 */
	public static boolean isSameElementType(PsiElement psiElement1, PsiElement psiElement2) {
		return isSameElementType(psiElement1.getNode(), psiElement2.getNode());
	}

	/**
	 * Checks if the given ASTNode is of IElementType et
	 *
	 * @param node ASTNode (if null, returns false)
	 * @param et   IElement type
	 * @return true if node is of type et, false otherwise
	 */
	public static boolean isOfElementType(ASTNode node, IElementType et) {
		return node != null && node.getElementType() == et;
	}

	/**
	 * Checks if the given PsiElement is of IElementType et
	 *
	 * @param pe PsiElement (if null, returns false)
	 * @param et IElement type
	 * @return true if pe is of type et, false otherwise
	 */
	public static boolean isOfElementType(PsiElement pe, IElementType et) {
		return pe != null && isOfElementType(pe.getNode(), et);
	}

	/**
	 * Traverses the entire AST tree of the given PsiFile and returns the first ASTNode that matches IElementType type
	 *
	 * @param file    PsiFile to traverse
	 * @param type    IElement the type to find in the AST tree
	 * @param content text to match inside the node, or null if doesn't matter
	 * @return ASTNode that is the first of type, or null if none was found
	 */
	public static ASTNode findFirstElement(PsiFile file, IElementType type, String content) {
		ASTNode[] children = file.getNode().getChildren(null);
		ASTNode ret;
		for (ASTNode child : children){
			ret = findFirstElement(child, type, content);
			if (ret != null){
				return ret;
			}
		}
		return null;
	}

	/**
	 * Traverses the entire AST tree of the given ASTNode and returns the first ASTNode that matches IElementType type
	 *
	 * @param node    ASTNode to traverse
	 * @param type    IElement the type to find in the AST tree
	 * @param content text to match inside the node, or null if doesn't matter
	 * @return ASTNode that is the first of type, or null if none was found
	 */
	public static ASTNode findFirstElement(ASTNode node, IElementType type, String content) {
		if (isOfElementType(node, type)){
			if (content != null && node.getText().equals(content)){
				return node;
			}else if (content != null){
				return null;
			}
			return node;
		}
		ASTNode[] children = node.getChildren(null);
		for (ASTNode child : children){
			findFirstElement(child, type, content);
		}
		return null;
	}


	/**
	 * Traverses the entire AST tree of the given PsiElement and adds all ASTNodes that match the type of toFind to a list
	 *
	 * @param element   PsiElement to traverse
	 * @param toFind IElement the type to find in the AST tree
	 * @param cursor the node that is already discovered since the user's mouse is over it (can be null)
	 * @return ArrayList containing all ASTNodes that mach the IElementType toFind
	 */
	public static ArrayList<ASTNode> findDescendantElements(PsiElement element, IElementType toFind, ASTNode cursor) {
		return findDescendantElements(element, toFind, cursor, null);
	}

	/**
	 * Traverses the entire AST tree of the given PsiElement and adds all ASTNodes that match the type of toFind to a list and the ASTNode's text equals textContent
	 *
	 * @param element   PsiElement to traverse
	 * @param toFind IElement the type to find in the AST tree
	 * @param cursor the node that is already discovered since the user's mouse is over it (can be null)
	 * @param textContent text to look for in ASTNode (null if doesn't matter)
	 * @return ArrayList containing all ASTNodes that mach the IElementType toFind
	 */
	public static ArrayList<ASTNode> findDescendantElements(PsiElement element, IElementType toFind, ASTNode cursor, String textContent) {
		ArrayList<ASTNode> list = new ArrayList<>();
		traverseElement(list, element, toFind, cursor, textContent);
		return list;
	}

	/**
	 * Traverses the entire AST tree of the given PsiElement and adds all ASTNodes that match the type of toFind to a list
	 *
	 * @param list   list to add each ASTNode to that matches toFind's type
	 * @param element   PsiElement to traverse
	 * @param toFind IElement the type to find in the AST tree
	 * @param cursor the node that is already discovered since the user's mouse is over it (can be null)
	 * @param textContent text to look for in ASTNode (null if doesn't matter)
	 */
	private static void traverseElement(ArrayList<ASTNode> list, PsiElement element, IElementType toFind, ASTNode cursor, String textContent) {
		ASTNode[] children = element.getNode().getChildren(null);
		for (ASTNode node : children){
			traverseASTNode(list, toFind, cursor, node, textContent);
		}
	}

	/**
	 * Traverses all children of discoveredElement and adds ASTNode's that mach type of toFind to list
	 *
	 * @param list              list of all elements of type toFind
	 * @param toFind            element type to find
	 * @param cursor            the ASTNode that has the mouse over it (can be null)
	 * @param discoveredElement previously discovered ASTNode
	 * @param textContent text to look for in ASTNode (null if doesn't matter)
	 */
	private static void traverseASTNode(ArrayList<ASTNode> list, IElementType toFind, ASTNode cursor, ASTNode discoveredElement, String textContent) {
		if(textContent == null){
			elementDiscovered(list, toFind, cursor, discoveredElement);
		}else if(discoveredElement.getText().equals(textContent)){
			elementDiscovered(list, toFind, cursor, discoveredElement);
		}
		ASTNode[] children = discoveredElement.getChildren(null);
		for (ASTNode node : children){
			traverseASTNode(list, toFind, cursor, node, textContent);
		}
	}

	private static void elementDiscovered(ArrayList<ASTNode> elements, IElementType toFind, ASTNode cursor, ASTNode discoveredElement) {
		if (PsiUtil.isOfElementType(discoveredElement, toFind)){
			addToList(elements, discoveredElement, cursor);
		}
	}

	private static void addToList(List<ASTNode> list, ASTNode n, ASTNode cursor) {
		if (n == null){
			return;
		}
		if (n == cursor){
			return;
		}
		list.add(n);
	}

}