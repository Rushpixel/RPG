package com.endoplasm.engine;

import static org.lwjgl.opengl.EXTFramebufferObject.GL_FRAMEBUFFER_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glBindFramebufferEXT;
import static org.lwjgl.opengl.ARBImaging.*;
import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;

/**
 * This class is used to render the endogen lighting system. Copyright EndoPlasm Gaming ©2013 Edited 3 Apr 2015
 */
public class Lighting2d {

	/**
	 * 
	 */
	public Lighting2d() {
		DiffuseFBO.init(Endogen.WIDTH, Endogen.HEIGHT);
		LightingFBO.init(Endogen.WIDTH, Endogen.HEIGHT);
		LIgnoringFBO.init(Endogen.WIDTH, Endogen.HEIGHT);
	}

	// Ambient light colours
	private float Ambient_R = 1;
	private float Ambient_G = 1;
	private float Ambient_B = 1;

	// Diffuse layer FBO
	public FBO DiffuseFBO = new FBO();

	// Lighting layer FBO
	public FBO LightingFBO = new FBO();

	// Light Ignoring layer FBO
	public FBO LIgnoringFBO = new FBO();

	// Output FBO
	public FBO outputFBO = new FBO();

	/**
	 * reset ambience Init FBOs if necessary (if game was resized)
	 */
	private void Step0() {
		Ambient_R = 1;
		Ambient_G = 1;
		Ambient_B = 1;
		if (DiffuseFBO.WIDTH != Endogen.WIDTH || DiffuseFBO.HEIGHT != Endogen.HEIGHT) {
			DiffuseFBO.init(Endogen.WIDTH, Endogen.HEIGHT);
			LightingFBO.init(Endogen.WIDTH, Endogen.HEIGHT);
			LIgnoringFBO.init(Endogen.WIDTH, Endogen.HEIGHT);
			outputFBO.init(Endogen.WIDTH, Endogen.HEIGHT);
		}
		light_points.clear();
		light_rays.clear();
	}

	/**
	 * Use step 0 to clear stuff
	 * set render target to diffuse layer
	 */
	public void Step1(Vertex2f centre, float horSize) {
		Step0();
		DiffuseFBO.setAsRenderTarget();
		FBO.clear(0, 0, 0, 1);
		
		GL11.glPushMatrix();
		setCamera(centre, horSize);
		
		//Game.setSHADERMODE(0);
	}

	/**
	 * Render lighting layer 
	 * Set render target to light ignoring layer
	 */
	public void Step2(Vertex2f centre, float horSize) {
		GL11.glPopMatrix();
		LightingFBO.setAsRenderTarget();
		FBO.clear(Ambient_R, Ambient_G, Ambient_B, 1);
		glViewport(0, 0, Endogen.WIDTH, Endogen.HEIGHT);
		GL11.glPushMatrix();
		setCamera(centre, horSize);
		//Game.setSHADERMODE(0);
		
		// render lights in an additive way
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE);
		glBlendEquation(GL_FUNC_ADD);
		{
			for(LightSource_Point l: light_points) l.render();
			for(LightSource_Ray l: light_rays) l.render();
		}
		GL11.glPopMatrix();
		
		// set Light ignoring layer as render target
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glBlendEquation(GL_FUNC_ADD);	
		LIgnoringFBO.setAsRenderTarget();
		FBO.clear(0, 0, 0, 0);
		glViewport(0, 0, Endogen.WIDTH, Endogen.HEIGHT);
		GL11.glPushMatrix();
		setCamera(centre, horSize);
		//Game.setSHADERMODE(1);
		//Game.setAmbienceColour(Ambient_R, Ambient_G, Ambient_B);
	}

	/**
	 * Combine layers into output
	 */
	public void Step3() {
		GL11.glPopMatrix();
		//Game.setSHADERMODE(0);
		setToMainFBO();
		FBO.clear(1, 1, 1, 1);
		//glViewport(0, 0, Endogen.WIDTH, Endogen.HEIGHT);
		GL11.glPushMatrix();
		
		// combine Diffuse layer and lighting in a multiplying kinda way
		glEnable(GL_BLEND);
		GL14.glBlendFuncSeparate(GL_DST_COLOR, GL_ZERO, GL_ONE, GL_ONE);
		GL20.glBlendEquationSeparate(GL_FUNC_ADD, GL_MAX);
		glBindTexture(GL_TEXTURE_2D, LightingFBO.colorTextureID);
		Manager.square(0, 0, Endogen.WIDTH, Endogen.HEIGHT, 1, 1, 1, 1);
		glBindTexture(GL_TEXTURE_2D, DiffuseFBO.colorTextureID);
		Manager.square(0, 0, Endogen.WIDTH, Endogen.HEIGHT, 1, 1, 1, 1);
		
		// add Light Ignoring Layer on top, cause why not?
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glBlendEquation(GL_FUNC_ADD);
		glBindTexture(GL_TEXTURE_2D, LIgnoringFBO.colorTextureID);
		Manager.square(0, 0, Endogen.WIDTH, Endogen.HEIGHT, 1, 1, 1, 1);
		GL11.glPopMatrix();
		
		// set back to main FBO, and render onto it output
//		setToMainFBO();
//		GL11.glPushMatrix();
//		glBindTexture(GL_TEXTURE_2D, outputFBO.colorTextureID);
//		Manager.square(0, 0, Endogen.WIDTH, Endogen.HEIGHT, 1, 1, 1, 1);
//		GL11.glPopMatrix();
	}

	/**
	 * set the render target to Endogen's main FBO
	 */
	public static void setToMainFBO() {
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, Endogen.framebufferID);
	}
	
	
	// camera stuff;
	public Vertex2f centre = new Vertex2f(0, 0);
	public float scale = 1;
	public float horSize = 350;
	public float verSize = 0;
	private void setCamera(Vertex2f centre, float horSize){
		this.horSize = horSize;
		this.centre = centre;
		scale = Endogen.WIDTH / horSize;
		verSize = (Endogen.HEIGHT / scale);
		glScalef(scale, scale, 0);
		glTranslatef(-centre.getX() + horSize / 2, -centre.getY() + verSize / 2, 0);
	}

	/**
	 * Set ambient light colour
	 * 
	 * @param r
	 *            Red light component between 0-1
	 * @param g
	 *            Green light component between 0-1
	 * @param b
	 *            Blue light component between 0-1
	 */
	public void setAmbientLight(float r, float g, float b) {
		Ambient_R = r;
		Ambient_B = b;
		Ambient_G = g;
	}

	/**
	 * Nested class used to store data for point lights in the scene Copyright EndoPlasm Gaming ©2013 Edited 3 Apr 2015
	 */
	private class LightSource_Point {
		public Vertex2f pos;
		public float radius;
		public float r, g, b;
		
		public void render() {
			Render2d.DrawCircle(pos, radius, 64, 0, r, g, b, 1);
		}
	}

	public ArrayList<LightSource_Point> light_points = new ArrayList<LightSource_Point>();

	/**
	 * Adds new LightSource_Point to the scene
	 */
	public void addNew_LightSourcePoint(Vertex2f pos, float radius, float r, float g, float b) {
		LightSource_Point p = new LightSource_Point();
		p.pos = pos;
		p.radius = radius;
		p.r = r;
		p.g = g;
		p.b = b;
		light_points.add(p);
	}

	/**
	 * Nested class used to staore data for Ray lights in the scene Copyright EndoPlasm Gaming ©2013 Edited 3 Apr 2015
	 */
	private class LightSource_Ray {
		public void render() {

		}
	}

	public ArrayList<LightSource_Ray> light_rays = new ArrayList<LightSource_Ray>();

	/**
	 * Adds new LightSource_Ray to the scene
	 */
	public void addNew_LightSourceRay() {

	}

}
