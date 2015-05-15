package com.endoplasm.engine;

import static org.lwjgl.opengl.EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.GL_FRAMEBUFFER_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.GL_RENDERBUFFER_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glBindFramebufferEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glBindRenderbufferEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glFramebufferRenderbufferEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glFramebufferTexture2DEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glGenFramebuffersEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glGenRenderbuffersEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glRenderbufferStorageEXT;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_INT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;

import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GLContext;

public class FBO {

	// FBO info
	public int WIDTH;
	public int HEIGHT;
	public boolean Init = false;
	public int colorTextureID;
	public int framebufferID;
	public int depthRenderBufferID;
	
	public void init(int WIDTH, int HEIGHT){
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		
		if (!GLContext.getCapabilities().GL_EXT_framebuffer_object) {
			Endogen.exceptionhandler.ErrorMessage("This Computer does not support OpenGL FBOs");
		} else {

			System.out.println("FBO is supported");

			// init our fbo
			if (!Init) {
				framebufferID = glGenFramebuffersEXT();
				colorTextureID = glGenTextures();
				depthRenderBufferID = glGenRenderbuffersEXT();
				Init = true;
			}

			System.out.println("Frame buffer ID = " + framebufferID);

			glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, framebufferID);

			// initialize color texture
			glBindTexture(GL_TEXTURE_2D, colorTextureID);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, WIDTH, HEIGHT, 0, GL_RGBA, GL_INT, (java.nio.ByteBuffer) null);
			glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT, GL_COLOR_ATTACHMENT0_EXT, GL_TEXTURE_2D, colorTextureID, 0);

			// initialize depth renderbuffer
			glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, depthRenderBufferID);
			glRenderbufferStorageEXT(GL_RENDERBUFFER_EXT, GL14.GL_DEPTH_COMPONENT24, WIDTH, HEIGHT);
			glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT, GL_DEPTH_ATTACHMENT_EXT, GL_RENDERBUFFER_EXT, depthRenderBufferID);

			glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
		}
	}
	
	public void setAsRenderTarget(){
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, framebufferID);
	}
	
	public static void clear(float r, float g, float b, float a){
		glClearColor(r, g, b, a);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

}
