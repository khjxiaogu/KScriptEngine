package com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.ast;
import java.util.ArrayList;
import java.util.List;

public class VarStmnt extends ASTList {
    public VarStmnt(List<ASTree> c) { 
    	super(c);
    	
    }
    public String name() { if(child(0) instanceof ASTLeaf)return ((ASTLeaf)child(0)).token().getText(); else return ((ASTLeaf)((BinaryExpr)child(0)).left()).toString();}
    public TypeTag type() { if(child(0) instanceof ASTLeaf&&child(1) instanceof TypeTag)return (TypeTag)child(1);else return new TypeTag(new ArrayList<ASTree>()); }
    public ASTree initializer() { if(child(0) instanceof ASTLeaf&&child(1) instanceof TypeTag)return child(2);else return (ASTLeaf)((BinaryExpr)child(0)).right(); }
    public String toString() {
        return "(var " + name() + " " + type() + "=" + initializer() + ")";
    }
}
