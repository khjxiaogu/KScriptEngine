package com.khjxiaogu.scriptengine.core.test;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

public class CodeDialog {
	JSplitPane mpane;
	JTextArea area;

	public CodeDialog() {
		// TODO Auto-generated constructor stub
		area = new JTextArea(15, 20);
		JScrollPane pane = new JScrollPane(area);
		// pane.setPreferredSize(new Dimension(200,200));
		JTextArea oarea = new JTextArea(5, 20);
		JScrollPane opane = new JScrollPane(oarea);
		// opane.setPreferredSize(new Dimension(200,200));
		mpane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		mpane.setResizeWeight(1.0);
		mpane.setDividerLocation(0.7);
		mpane.add(pane);
		mpane.add(opane);
		area.setText("enter your code here...");
		// oarea.setText("here is the console\n");
		PrintStream con = new PrintStream(new TextAreaOutputStream(oarea));
		System.setOut(con);
	}

	private String buffer = null;
	private int pos = 0;

	public int read(char[] cbuf, int off, int len) throws IOException {
		if (buffer == null) {
			String in = showDialog();
			if (in == null) {
				return -1;
			} else {
				print(in);
				buffer = in + "\n";
				pos = 0;
			}
		}

		int size = 0;
		int length = buffer.length();
		while (pos < length && size < len) {
			cbuf[off + size++] = buffer.charAt(pos++);
		}
		if (pos == length) {
			buffer = null;
		}
		return size;
	}

	protected void print(String s) {
		System.out.println(s);
	}

	public void close() throws IOException {
	}

	protected String showDialog() {

		int result = JOptionPane.showOptionDialog(null, mpane, "KJS Engine Emulator", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, null, null);
		String ret = area.getText();
		// area.setText("");
		if (result == JOptionPane.OK_OPTION) {
			return ret;
		} else {
			return null;
		}
	}

	public static Reader file() throws FileNotFoundException {
		JFileChooser chooser = new JFileChooser();
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			return new BufferedReader(new FileReader(chooser.getSelectedFile()));
		} else {
			throw new FileNotFoundException("no file specified");
		}
	}
}

class TextAreaOutputStream extends OutputStream {

// *************************************************************************************************
// INSTANCE MEMBERS
// *************************************************************************************************

	private byte[] oneByte; // array for write(int val);
	private Appender appender; // most recent action

	public TextAreaOutputStream(JTextArea txtara) {
		this(txtara, 1000);
	}

	public TextAreaOutputStream(JTextArea txtara, int maxlin) {
		if (maxlin < 1) {
			throw new IllegalArgumentException(
					"TextAreaOutputStream maximum lines must be positive (value=" + maxlin + ")");
		}
		oneByte = new byte[1];
		appender = new Appender(txtara, maxlin);
	}

	/** Clear the current console text area. */
	public synchronized void clear() {
		if (appender != null) {
			appender.clear();
		}
	}

	@Override
	public synchronized void close() {
		appender = null;
	}

	@Override
	public synchronized void flush() {
	}

	@Override
	public synchronized void write(int val) {
		oneByte[0] = (byte) val;
		write(oneByte, 0, 1);
	}

	@Override
	public synchronized void write(byte[] ba) {
		write(ba, 0, ba.length);
	}

	@Override
	public synchronized void write(byte[] ba, int str, int len) {
		if (appender != null) {
			appender.append(bytesToString(ba, str, len));
		}
	}

	static private String bytesToString(byte[] ba, int str, int len) {
		try {
			return new String(ba, str, len, "UTF-8");
		} catch (UnsupportedEncodingException thr) {
			return new String(ba, str, len);
		} // all JVMs are required to support UTF-8
	}

// *************************************************************************************************
// STATIC MEMBERS
// *************************************************************************************************

	static class Appender implements Runnable {
		private final JTextArea textArea;
		private final int maxLines; // maximum lines allowed in text area
		private final LinkedList<Integer> lengths; // length of lines within text area
		private final List<String> values; // values waiting to be appended

		private int curLength; // length of current line
		private boolean clear;
		private boolean queue;

		Appender(JTextArea txtara, int maxlin) {
			textArea = txtara;
			maxLines = maxlin;
			lengths = new LinkedList<>();
			values = new ArrayList<>();

			curLength = 0;
			clear = false;
			queue = true;
		}

		synchronized void append(String val) {
			values.add(val);
			if (queue) {
				queue = false;
				EventQueue.invokeLater(this);
			}
		}

		synchronized void clear() {
			clear = true;
			curLength = 0;
			lengths.clear();
			values.clear();
			if (queue) {
				queue = false;
				EventQueue.invokeLater(this);
			}
		}

// MUST BE THE ONLY METHOD THAT TOUCHES textArea!
		@Override
		public synchronized void run() {
			if (clear) {
				textArea.setText("");
			}
			for (String val : values) {
				curLength += val.length();
				if (val.endsWith(EOL1) || val.endsWith(EOL2)) {
					if (lengths.size() >= maxLines) {
						textArea.replaceRange("", 0, lengths.removeFirst());
					}
					lengths.addLast(curLength);
					curLength = 0;
				}
				textArea.append(val);
			}
			values.clear();
			clear = false;
			queue = true;
		}

		static private final String EOL1 = "\n";
		static private final String EOL2 = System.getProperty("line.separator", EOL1);
	}

} /* END PUBLIC CLASS */
