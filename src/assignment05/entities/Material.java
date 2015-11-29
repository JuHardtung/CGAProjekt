package assignment05.entities;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.joml.Vector3f;


/**
 * Created by bryan on 11.11.2015.
 */
public class Material {
	private final float STEP = 0.01f;
	private final NumberFormat DEFAULT = new DecimalFormat("0.00");
    private Vector3f emission = new Vector3f(0, 0, 0);
    private Vector3f ambient = new Vector3f(1, 1, 1);
    private Vector3f diffuse = new Vector3f(1, 1, 1);
    private Vector3f specular = new Vector3f(1, 1, 1);

    private float shininess = 1.0f;

    public Material(Vector3f emission, Vector3f ambient, Vector3f diffuse, Vector3f specular, float shininess) {
        this.emission = emission;
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
        this.shininess = shininess;
    }

    public Vector3f getEmission() {
        return emission;
    }

    public void setEmission(Vector3f emission) {
        this.emission = emission;
    }

    public Vector3f getAmbient() {
        return ambient;
    }

    public void setAmbient(Vector3f ambient) {
        this.ambient = ambient;
    }

    public Vector3f getDiffuse() {
        return diffuse;
    }

    public void setDiffuse(Vector3f diffuse) {
        this.diffuse = diffuse;
    }

    public Vector3f getSpecular() {
        return specular;
    }

    public void setSpecular(Vector3f specular) {
        this.specular = specular;
    }

    public float getShininess() {
        return shininess;
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
    }
    
    
	@Override
	public String toString() {
		return "Material [emission=" + emission.toString(DEFAULT) + ", ambient=" + ambient.toString(DEFAULT) + ", diffuse=" + diffuse.toString(DEFAULT) + ", specular="
				+ specular.toString(DEFAULT) + ", shininess=" + DEFAULT.format(shininess) + "]";
	}
	
	public void increaseEmission(){
		this.emission = increaseColor(this.emission, STEP * 5);
	}
	
	public void decreaseEmission(){
		this.emission = increaseColor(this.emission, STEP * -5);
	}
	
	public void increaseAmbient(){
		this.ambient = increaseColor(this.ambient, STEP * 5);
	}
	
	public void decreaseAmbient(){
		this.ambient = increaseColor(this.ambient, STEP * -5);
	}
	
	public void increaseDiffuse(){
		this.diffuse = increaseColor(this.diffuse, STEP * 5);
	}
	
	public void decreaseDiffuse(){
		this.diffuse = increaseColor(this.diffuse, STEP * -5);
	}
	
	public void increaseSpecular(){
		this.specular = increaseColor(this.specular, STEP * 5);
	}
	
	public void decreaseSpecular(){
		this.specular = increaseColor(this.specular, STEP * -5);
	}
	
	public void increaseShininess(){
		this.shininess = clamp(0.0f, 100.0f,this.shininess + STEP * 10 * 5);
	}
	
	public void decreaseShininess(){
		this.shininess = clamp(0.0f, 100.0f,this.shininess + STEP * 10 * -5);
	}
	
	private Vector3f increaseColor(Vector3f v, float amount) {
		float x = clamp(0.0f,1.0f,v.x+amount);
		float y = clamp(0.0f,1.0f,v.x+amount);
		float z = clamp(0.0f,1.0f,v.x+amount);

		return new Vector3f(x, y, z);
	}
	
    private float clamp(float lower, float upper, float value){
    	if(value < lower){
    		return lower;
    	}
    	if(value>upper){
    		return upper;
    	}
    	return value;
    }
    
}
