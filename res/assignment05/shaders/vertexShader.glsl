#version 330 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normal;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

// TODO: Aufgabe 5.2: Legen Sie eine uniform-Variable für die Lichtposition an. Achten Sie darauf, die gleichen Namen wie beim Anlegen im StaticShaderProgram zu verwenden
// TODO: Aufgabe 5.2: Berechnen Sie je einen Vektor der zur Lichtquelle zeigt, zur Kamera zeigt und die Flächennormale. Hinweis: Die Flächennormale muss transformiert werden.
// TODO: Aufgabe 5.2: Geben Sie die drei Vektoren als out-Variable an den FragmentShader weiter.

// TODO: Aufgabe 5.3: Erweitern Sie die uniform-Variable für die Lichtposition zu einem Array der Größe 8. Erweitern Sie auch die out-Variable für den Vektor zur Lichtquelle
// TODO: Aufgabe 5.3: und führen Sie die Berechnung für alle 8 Lichtquellen in einer Schleife durch.


void main(void){
    vec4 worldPosition = modelMatrix * vec4(position, 1.0);
	gl_Position = projectionMatrix * viewMatrix  * worldPosition;
}