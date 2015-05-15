package com.endoplasm.engine;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;

public class Shader {

	// code from http://lwjgl.org/wiki/index.php?title=GLSL_Shaders_with_LWJGL

	/*
	 * if the shaders are setup ok we can use shaders, otherwise we just use
	 * default settings
	 */
	public boolean useShader;

	/*
	 * program shader, to which is attached a vertex and fragment shaders. They
	 * are set to 0 as a check because GL will assign unique int values to each
	 */
	public int program = 0;

	public Shader() {
		int vertShader = 0, fragShader = 0;

		try {
			vertShader = createShader("/Resources/Shaders/Vertex.shader", ARBVertexShader.GL_VERTEX_SHADER_ARB);
			fragShader = createShader("/Resources/Shaders/Fragment.shader", ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
		} catch (Exception exc) {
			exc.printStackTrace();
			Endogen.exceptionhandler.ErrorMessage(exc.getMessage());
			System.exit(-1);
			return;
		} finally {
			if (vertShader == 0 || fragShader == 0) return;
		}

		program = ARBShaderObjects.glCreateProgramObjectARB();

		if (program == 0) return;

		/*
		 * if the vertex and fragment shaders setup sucessfully, attach them to
		 * the shader program, link the sahder program (into the GL context I
		 * suppose), and validate
		 */
		ARBShaderObjects.glAttachObjectARB(program, vertShader);
		ARBShaderObjects.glAttachObjectARB(program, fragShader);

		ARBShaderObjects.glLinkProgramARB(program);
		if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_FALSE) {
			System.err.println(getLogInfo(program));
			return;
		}

		ARBShaderObjects.glValidateProgramARB(program);
		if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE) {
			System.err.println(getLogInfo(program));
			return;
		}

		useShader = true;
	}

	/*
	 * With the exception of syntax, setting up vertex and fragment shaders is
	 * the same.
	 * 
	 * @param the name and path to the vertex shader
	 */
	private int createShader(String filename, int shaderType) throws Exception {
		System.out.println("Loading shader " + filename);
		int shader = 0;
		try {
			shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);

			if (shader == 0) return 0;

			ARBShaderObjects.glShaderSourceARB(shader, readFileAsString(filename));
			ARBShaderObjects.glCompileShaderARB(shader);

			if (ARBShaderObjects.glGetObjectParameteriARB(shader, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE) throw new RuntimeException("Error creating shader: " + getLogInfo(shader));

			return shader;
		} catch (Exception exc) {
			ARBShaderObjects.glDeleteObjectARB(shader);
			throw exc;
		}
	}

	private static String getLogInfo(int obj) {
		return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
	}

	private String readFileAsString(String filename) throws Exception {
		StringBuilder source = new StringBuilder();
		
		InputStream in = Shader.class.getResourceAsStream(filename);

		Exception exception = null;

		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

			Exception innerExc = null;
			try {
				String line;
				while ((line = reader.readLine()) != null)
					source.append(line).append('\n');
			} catch (Exception exc) {
				exception = exc;
			} finally {
				try {
					reader.close();
				} catch (Exception exc) {
					if (innerExc == null)
						innerExc = exc;
					else
						exc.printStackTrace();
				}
			}

			if (innerExc != null) throw innerExc;
		} catch (Exception exc) {
			exception = exc;
		} finally {

			if (exception != null) throw exception;
		}

		return source.toString();
	}
	
	public void kill(){
		ARBShaderObjects.glDeleteObjectARB(program);
		System.out.println("OpenGL shader program deleted");
	}
}
