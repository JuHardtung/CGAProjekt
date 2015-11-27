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

    private int location_lightPos;
	private int location_lightColDiffuse;
	private int location_lightColAmbient;
	private int location_lightColSpecular;

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

		// TODO: Aufgabe 5.1: Speicherplatz für uniform-Variablen holen (Licht und Material) und Adressen speichern

        location_lightPos = super.getUniformLocation("modelMatrix");
        location_lightColDiffuse = super.getUniformLocation("lightColorDiffuse>");
        location_lightColAmbient = super.getUniformLocation("lightColorAmbient");
        location_lightColSpecular = super.getUniformLocation("lightColorSpecular");
        
        location_matEmission = super.getUniformLocation("matEmission");
        location_matAmbient = super.getUniformLocation("matAmbient");
        location_matDiffuse = super.getUniformLocation("matDiffuse");
        location_matSpecular = super.getUniformLocation("matSpecular");
        location_matShininess = super.getUniformLocation("matShininess");
        
		// TODO: Aufgabe 5.3: Arrays mit MAX_LIGHTS initialisieren und für die Adressen der Variablen verwenden

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
		// TODO: Aufgabe 5.1: Werte des ersten Lichts in uniform-Variablen speichern
		PointLight light = lights.get(0);
		
		super.loadVector(location_lightPos, light.getLightPos());
		super.loadVector(location_lightColDiffuse, light.getLightColDiffuse());
		super.loadVector(location_lightColAmbient, light.getLightColAmbient());
		super.loadVector(location_lightColSpecular, light.getLightColSpecular());

		// TODO: Aufgabe 5.3: Werte aller Lichter in uniform-Arrays speichern
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
