package com.endoplasm.engine;

import static org.lwjgl.opengl.GL11.*;

public class Render2d {
	
	public static void Limb(Vertex2f pos, Vertex2f[] nodes, float[] widths, Vertex2f[] UVs, TextureAsset tex, float[] colour){
	
	}
	
	public static void uniTriangle(float c1x, float c1y, float c2x, float c2y, float c3x, float c3y, float[] colour, TextureAsset tex, float texScale){
		glPushMatrix();
		{
			Render.setColor(colour);
			tex.TEX.bind();
			glBegin(GL_TRIANGLES);
			{
				glTexCoord2f(c1x / texScale, c1y / texScale);
				glVertex2f(c1x, c1y);
				glTexCoord2f(c2x / texScale, c2y / texScale);
				glVertex2f(c2x, c2y);
				glTexCoord2f(c3x / texScale, c3y / texScale);
				glVertex2f(c3x, c3y);
			}
			glEnd();
		}
		glPopMatrix();
	}
	
	public static void uniSquare(float x, float y, float width, float height, float[] colour, TextureAsset tex, float texScale) {
		glPushMatrix();
		{
			Render.setColor(colour);
			tex.TEX.bind();
			glBegin(GL_QUADS);
			{
				glTexCoord2f(x / texScale, y / texScale);
				glVertex2f(x, y);
				
				glTexCoord2f(x / texScale, (y + height) / texScale);
				glVertex2f(x, y + height);
				
				glTexCoord2f((x + width) / texScale, (y + height) / texScale);
				glVertex2f(x + width, y + height);
				
				glTexCoord2f((x + width) / texScale, y / texScale);
				glVertex2f(x + width, y);
			}
			glEnd();
		}
		glPopMatrix();
	}

	public static void square(float x, float y, float width, float height, float[] colour, TextureAsset tex) {
		glPushMatrix();
		{
			Render.setColor(colour);
			glTranslatef(x, y, 0f);
			tex.TEX.bind();
			glBegin(GL_QUADS);
			{
				glTexCoord2f(0, 1);
				glVertex2f(0, 0);
				glTexCoord2f(0, 0);
				glVertex2f(0, height);
				glTexCoord2f(1, 0);
				glVertex2f(width, height);
				glTexCoord2f(1, 1);
				glVertex2f(width, 0);
			}
			glEnd();
		}
		glPopMatrix();
	}
	
	public static void squareRot(float x, float y, float x1, float y1, float x2, float y2, float rotation, float[] colour, TextureAsset tex) {
		glPushMatrix();
		glTranslatef(x, y, 0);
		float dx = x1+x2;
		float dy = y1+y2;
		square(x1, y1, dx, dy, colour, tex);
		glPopMatrix();
		glPushMatrix();
		{
			Render.setColor(colour);
			glTranslatef(x, y, 0f);
			glRotatef(rotation, 0, 0, 1);
			tex.TEX.bind();
			glBegin(GL_QUADS);
			{
				glTexCoord2f(0, 1);
				glVertex2f(x1, y1);
				glTexCoord2f(0, 0);
				glVertex2f(x1, y2);
				glTexCoord2f(1, 0);
				glVertex2f(x2, y2);
				glTexCoord2f(1, 1);
				glVertex2f(x2, y1);
			}
			glEnd();
		}
		glPopMatrix();
	}
	
	public static void DrawCircle(Vertex2f pos, float radius, int num_segments, float rotation, float r, float g, float b, float a) {
		glPushMatrix();
		glColor4f(r, g, b, a);
		glRotatef(rotation, 0,0,1);
		DrawCircle(pos, radius, num_segments);
		glPopMatrix();
	}
	
	public static void DrawCircle(Vertex2f pos, float r, int num_segments) {
		Endogen.SystemAssets.mask.BLANK.TEX.bind();
		float theta = 2f * 3.1415926f / (num_segments);
		float tangetial_factor = (float) Math.tan(theta);// calculate the tangential factor
		float radial_factor = (float) Math.cos(theta);// calculate the radial factor
		float x = r;// we start at angle = 0

		float y = 0;
		glPushMatrix();
		glTranslatef(pos.getX(), pos.getY(), 0f);
		glBegin(GL_TRIANGLE_FAN);
		glVertex2f(0, 0);
		glColor4f(0, 0, 0, 1);
		
		for (int ii = 0; ii < num_segments; ii++) {
			glVertex2f(x, y);// output vertex
			float tx = -y;
			float ty = x;
			// add the tangential vector
			x += tx * tangetial_factor;
			y += ty * tangetial_factor;
			// correct using the radial factor
			x *= radial_factor;
			y *= radial_factor;
		}
		glVertex2f(x, y);
		glEnd();
		glPopMatrix();
	}

}
