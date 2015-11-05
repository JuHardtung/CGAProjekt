package assignment03.entities;

import assignment03.renderEngine.RawModel;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Entity {

    private RawModel model;
	private Matrix4f modelMatrix = new Matrix4f();

	public Entity(RawModel model) {
		this.model = new RawModel(model);
	}

	public RawModel getModel() {
		return model;
	}

	public Matrix4f getModelMatrix() {
		return modelMatrix;
	}

	public void setModelMatrix(Matrix4f m){
		this.modelMatrix = new Matrix4f(m);
	}
}