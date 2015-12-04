package assignment06.renderEngine;

import assignment06.entities.Entity;
import org.lwjgl.BufferUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;


public class Loader {
	
	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	private List<Integer> textures = new ArrayList<Integer>();

    public RawModel loadToVAO(float[] positions,  float[] textureCoords, float[] normals,int[] indices){
        // create VAO and assign data
        int vaoID = createVAO();
        createIndexBuffer(indices);
        createVertexBuffer(0,3,positions);
        createVertexBuffer(1,3,normals);
        createVertexBuffer(2,2,textureCoords);
        unbindVAO();

        // save VAO in RawModel
        return new RawModel(vaoID,indices.length);
    }

	public int loadTexture(String fileName) throws Exception{
		
		int textureID = 0;		


		InputStream in = new FileInputStream("res/assignment06/meshes/"+fileName+".png");
		try {			
			PNGDecoder decoder = new PNGDecoder(in);
			ByteBuffer buf = ByteBuffer.allocateDirect(4*decoder.getWidth()*decoder.getHeight());
			decoder.decode(buf, decoder.getWidth()*4, Format.RGBA);
			buf.flip();

			// create texture, activate and upload texture //
			textureID = glGenTextures();
			glActiveTexture(GL_TEXTURE0);
			glBindTexture(GL_TEXTURE_2D, textureID);		   
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);
			glGenerateMipmap(GL_TEXTURE_2D);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			
		} finally {
			in.close();
		}


		textures.add(textureID);

		return textureID;
	}


	public void cleanUp(){
		for(int vao:vaos){
			glDeleteVertexArrays(vao);
		}
		for(int vbo:vbos){
			glDeleteBuffers(vbo);
		}
		for(int texture:textures){
			glDeleteTextures(texture);
		}
	}

	private int createVAO(){
		int vaoID = glGenVertexArrays();
		vaos.add(vaoID);
		glBindVertexArray(vaoID);
		return vaoID;
	}

	private void createVertexBuffer(int attributeNumber,int coordinateSize, float[] data){
		// generate and save new ID for the vertex buffer object
		int vboID = glGenBuffers();
		vbos.add(vboID);

		// activate buffer and upload data
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

		// tell OpenGL how to interpret the data
		glVertexAttribPointer(attributeNumber,coordinateSize,GL_FLOAT,false,0,0);

		// unbind buffer
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	private void unbindVAO(){
		glBindVertexArray(0);
	}

	private void createIndexBuffer(int[] indices){
		// generate and save new ID for the index buffer object
		int vboID = glGenBuffers();
		vbos.add(vboID);

		// activate buffer and upload data
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
	}

	private IntBuffer storeDataInIntBuffer(int[] data){
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	private FloatBuffer storeDataInFloatBuffer(float[] data){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	public Entity loadEntity(String filename) {
		RawModel model = OBJLoader.loadObjModel(filename, this);
		Entity entity = new Entity(model);
		return entity;
	}

}
