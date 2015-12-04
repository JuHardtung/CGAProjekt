#version 330 core

in vec3 surfaceNormal;
in vec3 toCameraVector;
in vec3 toLightVector;

// TODO: Nehmen Sie die Texturkoordinaten in Empfang
in vec2 textureCoords;


out vec4 out_Color;

uniform sampler2D tex1;

uniform vec3 lightColAmbient;
uniform vec3 lightColDiffuse;
uniform vec3 lightColSpecular;

uniform float matShininess;

uniform vec3 matEmission;
uniform vec3 matAmbient;
uniform vec3 matSpecular;

vec4 shade(vec3 toLightVector, vec3 unitNormal, vec3 unitToCameraVector,vec3 matDiffuse, vec3 lightColAmbient, vec3 lightColDiffuse, vec3 lightColSpecular)
{
    vec3 unitLightVector = normalize(toLightVector);
    vec3 lightDirection = -unitLightVector;
    vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);

    float specularFactor = dot(reflectedLightDirection, unitToCameraVector);
    specularFactor = max(specularFactor, 0.0);
    float dampedFactor = pow (specularFactor, matShininess);
    vec3 finalSpecular = dampedFactor * lightColSpecular;

    float nDotl = dot(unitNormal, unitLightVector);
    float brightness = max(nDotl, 0.0);
    vec3 diffuse = brightness * lightColDiffuse;

	return vec4(matEmission + lightColAmbient * matAmbient  + matDiffuse * diffuse + matSpecular * finalSpecular, 1.0);
}

void main(void){

    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitToCameraVector = normalize(toCameraVector);
    
    // TODO: Ersetzen Sie die folgende Zeile, um die Texturfarbe für diesen Texel abzufragen und als diffuse Materialfarbe zu verwenden
    vec3 diffuse = texture(tex1, textureCoords).xyz;
    
    
    out_Color = shade(toLightVector, unitNormal, unitToCameraVector, diffuse, lightColAmbient, lightColDiffuse, lightColSpecular);
}