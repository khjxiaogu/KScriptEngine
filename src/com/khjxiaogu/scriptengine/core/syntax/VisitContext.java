package com.khjxiaogu.scriptengine.core.syntax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VisitContext {
	public static final int NOT_LOCAL_BLOCK=1;
	private List<String> parentMap;
	private int flag;
	private int start;
	private VisitContext(List<String> parentMap, int flag, int start) {
		super();
		this.parentMap = parentMap;
		this.flag = flag;
		this.start = start;
	}
	public int allocLocal(String token) {
		int alc=parentMap.size()-1;
		parentMap.add(token);
		return alc;
	}
	public void allocLocals(String[] tokens) {
		for(String token:tokens)
			parentMap.add(token);
	}
	public void allocLocal(Iterable<String> tokens) {
		for(String token:tokens)
			parentMap.add(token);
	}
	public static VisitContext create() {
		return new VisitContext(new ArrayList<>(),0,0);
	}
	public static VisitContext create(int flag) {
		return new VisitContext(new ArrayList<>(),flag,0);
	}
	public static VisitContext create(String[] symbols) {
		return new VisitContext(new ArrayList<>(Arrays.asList(symbols)),0,0);
	}
	public VisitContext child() {
		int off = parentMap.size();
		List<String> curmap = new ArrayList<>(parentMap);
		return new VisitContext(curmap,flag,off);
	}
	public VisitContext child(int flag) {
		int off = parentMap.size();
		List<String> curmap = new ArrayList<>(parentMap);
		return new VisitContext(curmap,flag,off);
	}
	public int getCurrentSize() {
		return parentMap.size()-start;
	}
	public String[] getSymbols() {
		return parentMap.toArray(new String[parentMap.size()]);
	}
	public int findLocal(String token) {
		return parentMap.lastIndexOf(token);
	}
	public int getFlag() {
		return flag;
	}
	public boolean hasFlag(int flag) {
		return (this.flag&flag)>0;
	}
	public int getOffset() {
		return start;
	}
}
