package assignment06.shaders;

import assignment06.entities.Material;
import assignment06.entities.PointLight;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL20.glUniform1i;

public class StaticShaderProgram extends ShaderProgram {
	
	private static final String VERTEX_FILE   = "res/assignment06/shaders/vertexShader.glsl";
	private static final String FRAGMENT_FILE = "res/assignment06/shaders/fragmentShader.glsl";

	private int location_modelMatrix;
    private int location_viewMatrix;
    private int location_projectionMatrix;

    private int location_lightPos;
	private int location_lightColDiffuse;
	private int location_lightColAmbient;
	private int location_lightColSpecular;

	private int location_matEmission;
	private int location_matAmbient;
	private int location_matSpecular;
	private int location_matShininess;

	private int location_textureSampler;

	public StaticShaderProgram() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "normal");
		super.bindAttribute(2, "textureCoords");
	}

	@Override
	protected void getAllUniformLocations() {
		location_modelMatrix = super.getUniformLocation("modelMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");

		location_lightPos = super.getUniformLocation("lightPos");
		location_lightColAmbient = super.getUniformLocation("lightColAmbient");
		location_lightColDiffuse = super.getUniformLocation("lightColDiffuse");
		location_lightColSpecular = super.getUniformLocation("lightColSpecular");

		location_matEmission = super.getUniformLocation("matEmission");
		location_matAmbient = super.getUniformLocation("matAmbient");
		location_matSpecular = super.getUniformLocation("matSpecular");
		location_matShininess = super.getUniformLocation("matShininess");

		location_textureSampler = super.getUniformLocation("textureSampler");
	}

	public void loadMaterial(Material mat){
		super.loadVector(location_matEmission, mat.getEmission());
		super.loadVector(location_matAmbient, mat.getAmbient());
		super.loadVector(location_matSpecular, mat.getSpecular());
		super.loadFloat(location_matShininess, mat.getShininess());
	}

	public void loadLight(PointLight light) {
		super.loadVector(location_lightPos, light.getLightPos());
		super.loadVector(location_lightColAmbient, light.getLightColAmbient());
		super.loadVector(location_lightColDiffuse, light.getLightColDiffuse());
		super.loadVector(location_lightColSpecular, light.getLightColSpecular());
	}

	public void loadTexture(){
		glUniform1i(location_textureSampler, 0);
	}

	public void loadModelMatrix(Matrix4f matrix){
		super.loadMatrix(location_modelMatrix, matrix);
	}

    public void loadViewMatrix (Matrix4f view){
        super.loadMatrix(location_viewMatrix, view);
    }
	
	public void loadProjectionMatrix(Matrix4f projection){
		super.loadMatrix(location_projectionMatrix, projection);
	}

}
