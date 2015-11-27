#version 330 core

out vec4 out_Color;


// TODO: Aufgabe 5.2: Legen Sie die uniform-Variablen für Licht- und Materialeigenschaften an.
// TODO: Aufgabe 5.2: Berechnen Sie die out_Color nach dem Phong-Modell. Sie benötigen dazu die in-Variablen für die 3 Vektoren aus dem Vertexshader und uniform-Variablen für
// TODO: Aufgabe 5.2: alle Material- und Lichteigenschaften.

// TODO: Aufgabe 5.3: Erweitern Sie die Berechnung so, dass 8 Lichtquellen verarbeitet werden können. Die uniform-Variablen der Lichtquellen und die in-Variable des Vektors zur Lichtquelle
// TODO: Aufgabe 5.3: müssen zu Arrays gemacht werden.
// TODO: Aufgabe 5.3: Schreiben Sie am Besten eine Funktion, die die Lichtberechnung vornimmt und summieren Sie in einer Schleife die Ergebnisse auf out_Color.

void main(void){
        out_Color = vec4(1,1,1,1);
}