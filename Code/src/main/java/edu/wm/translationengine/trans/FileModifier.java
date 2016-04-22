package edu.wm.translationengine.trans;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Not an especially useful abstract class at the moment, but if we ever need something added across
 * the board, we have it. See FileModifierInterface for brief descriptions of methods.
 * @author mark
 *
 */

public abstract class FileModifier implements FileModifierInterface {

	public void setupFileImports() throws IOException {
	}

	public void setupTestMethodHeader() {
	}

	public void closeTestMethod() {
	}

}
