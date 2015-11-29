package assignment05.shaders;

import assignment05.entities.Material;
import assignment05.entities.PointLight;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;

public class StaticShaderProgram extends ShaderProgram {
	
	private static final String VERTEX_FILE = "res/assignment05/shaders/vertexShader.glsl";
	private static final String FRAGMENT_FILE = "res/assignment05/shaders/fragmentShader.glsl";
	private static final int MAX_LIGHTS = 8;

	private int location_modelMatrix;
    private int location_viewMatrix;
    private int location_projectionMatrix;

    private int[] location_lightPos;
	private int[] location_lightColDiffuse;
	private int[] location_lightColAmbient;
	private int[] location_lightColSpecular;

	private int location_matEmission;
	private int location_matAmbient;
	private int location_matDiffuse;
	private int location_matSpecular;
	private int location_matShininess;

	public StaticShaderProgram() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "normal");
	}

	@Override
	protected void getAllUniformLocations() {
		location_modelMatrix = super.getUniformLocation("modelMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");

		location_lightPos = new int[MAX_LIGHTS];
		location_lightColAmbient = new int[MAX_LIGHTS];
		location_lightColDiffuse = new int[MAX_LIGHTS];
		location_lightColSpecular = new int[MAX_LIGHTS];

		for (int i = 0; i < MAX_LIGHTS; i++) {
			location_lightPos[i] = super.getUniformLocation("lightPositions[" + i + "]");
			location_lightColAmbient[i] = super.getUniformLocation("lightColorAmbient[" + i + "]");
			location_lightColDiffuse[i] = super.getUniformLocation("lightColorDiffuse[" + i + "]");
			location_lightColSpecular[i] = super.getUniformLocation("lightColorSpecular[" + i + "]");
		}
		location_matEmission = super.getUniformLocation("matEmission");
		location_matAmbient = super.getUniformLocation("matAmbient");
		location_matDiffuse = super.getUniformLocation("matDiffuse");
		location_matSpecular = super.getUniformLocation("matSpecular");
		location_matShininess = super.getUniformLocation("matShininess");

	}

	public void loadMaterial(Material mat){
		// TODO: Aufgabe 5.1: Werte der Materialeigenschaften in uniform-Variablen speichern
	
		super.loadVector(location_matEmission, mat.getEmission());
		super.loadVector(location_matAmbient, mat.getAmbient());
		super.loadVector(location_matDiffuse, mat.getDiffuse());
		super.loadVector(location_matSpecular, mat.getSpecular());
		super.loadFloat(location_matShininess, mat.getShininess());

	}
	
	

	public void loadLights(ArrayList<PointLight> lights) {
		for (int i = 0; i < MAX_LIGHTS; i++) {
			if (i < lights.size() && lights.get(i).isEnabled()) {
				super.loadVector(location_lightPos[i], lights.get(i).getLightPos());
				super.loadVector(location_lightColAmbient[i], lights.get(i).getLightColAmbient());
				super.loadVector(location_lightColDiffuse[i], lights.get(i).getLightColDiffuse());
				super.loadVector(location_lightColSpecular[i], lights.get(i).getLightColSpecular());
			} else {
				super.loadVector(location_lightPos[i], new Vector3f(0, 0, 0));
				super.loadVector(location_lightColAmbient[i], new Vector3f(0, 0, 0));
				super.loadVector(location_lightColDiffuse[i], new Vector3f(0, 0, 0));
				super.loadVector(location_lightColSpecular[i], new Vector3f(0, 0, 0));
			}
		}
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
