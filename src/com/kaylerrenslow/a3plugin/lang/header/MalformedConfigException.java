package com.kaylerrenslow.a3plugin.lang.header;

import com.kaylerrenslow.a3plugin.lang.header.psi.HeaderFile;

/**
 * Created on 03/31/2016.
 */
public class MalformedConfigException extends Exception {

	public MalformedConfigException(HeaderFile file, String message) {
		super(file.getName() + " is incorrectly formatted. Message: " + message);
	}
}