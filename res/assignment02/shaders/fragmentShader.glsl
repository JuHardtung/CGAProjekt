#version 400 core

in vec3 colour;

out vec4 out_Color;

void main(void){
	//out_Color = vec4(colour,1.0);
	out_Color = vec4(0.5 * normalize(colour) + vec3(0.5, 0.5, 0.5), 1);

}