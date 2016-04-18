package edu.semeru.wm.qextractor.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

public class StatementVisitor extends ASTVisitor {

	List<Statement> statements = new ArrayList<Statement>();
	

	
	
	
	public boolean visit(ExpressionStatement node) {
		statements.add(node);
		return true;
	}
	
	public boolean visit(VariableDeclarationStatement node) {
		statements.add(node);
		return true;
	}
	
	
	public boolean visit(ForStatement node) {
		statements.add(node);
		return true;
	}
	
	public boolean visit(IfStatement node) {
		statements.add(node);
		return true;
	}
	
	public boolean visit(WhileStatement node) {
		statements.add(node);
		return true;
	}
	
	public boolean visit(TypeDeclarationStatement node) {
		statements.add(node);
		return true;
	}

	public boolean visit(ReturnStatement node) {
		statements.add(node);
		return true;
	}
	
	public List<Statement> getStatements() {
		return statements;
	}
	
	
}
