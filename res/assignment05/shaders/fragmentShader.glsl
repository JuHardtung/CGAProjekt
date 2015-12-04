#version 330 core



// TODO: Aufgabe 5.2: Legen Sie die uniform-Variablen für Licht- und Materialeigenschaften an.

uniform vec3 lightColorDiffuse[8];
uniform vec3 lightColorAmbient[8];
uniform vec3 lightColorSpecular[8];

uniform vec3 matEmission;
uniform vec3 matAmbient;
uniform vec3 matDiffuse;
uniform vec3 matSpecular;
uniform float matShininess;

in vec3 toLight[8];
in vec3 surfaceNormal;
in vec3 toCamera;

out vec4 out_Color;


// TODO: Aufgabe 5.2: Berechnen Sie die out_Color nach dem Phong-Modell. Sie benötigen dazu die in-Variablen für die 3 Vektoren aus dem Vertexshader und uniform-Variablen für
// TODO: Aufgabe 5.2: alle Material- und Lichteigenschaften.

// TODO: Aufgabe 5.3: Schreiben Sie am Besten eine Funktion, die die Lichtberechnung vornimmt und summieren Sie in einer Schleife die Ergebnisse auf out_Color.
// TODO: Aufgabe 5.3: Erweitern Sie die Berechnung so, dass 8 Lichtquellen verarbeitet werden können. Die uniform-Variablen der Lichtquellen und die in-Variable des Vektors zur Lichtquelle
// TODO: Aufgabe 5.3: müssen zu Arrays gemacht werden.

vec4 getColorByLight(int index){
		vec3 normalizedNormal = normalize(surfaceNormal);
		vec3 normalizedToCamera = normalize(toCamera);
		//Pro Lichtquelle	
		vec3 normalizedToLight = normalize(toLight[index]);
		vec3 normalizedH = normalize(normalizedToCamera + normalizedToLight);
		
		//Winkelberechnung
		float cosAlpha =  max(0.0, dot(normalizedNormal,normalizedToLight));
		float cosBeta = max(0.0, dot(normalizedNormal,normalizedH)); 
        float cosBetaK = pow(cosBeta, matShininess);
        
        //Berechnung der Farbanteile
        vec3 partAmbient = matAmbient * lightColorAmbient[index];
		vec3 partDiffuse = matDiffuse * cosAlpha * lightColorDiffuse[index];
		vec3 partSpecular = matSpecular * cosBetaK * lightColorSpecular[index];
		
		vec3 result = partAmbient + partDiffuse + partSpecular;
		return vec4(result, 1.0f);
}
void main(void){
		for(int i=0; i<8; i++){
			out_Color += getColorByLight(i);
		}
		
}

