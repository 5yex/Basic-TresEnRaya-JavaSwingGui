package main;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import main.LaminaPrincipal.BotonTresEnRaya;

//Esta clase lleva toda la lógica del juego.

public class Juego {
	private int tam;
	private int jugadorActual = 1;
	private int numeroJugada = 0;
	private BotonTresEnRaya tablero[][];

	public int getJugadorActual() {
		return jugadorActual;
	}

	public Juego(BotonTresEnRaya[][] tablero, int tam) {
		this.jugadorActual = 1 + JOptionPane.showOptionDialog(null, "Selecciona un jugador", "Tres en Raya",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				new Object[] { "Jugador 1", "Jugador 2" }, "Jugador 1");
		this.tablero = tablero;
		this.tam = tam;
	}

	// Comrpueba si ha ganado un jugar o es empate, y si no pasa a la siguiente
	// jugada
	public void jugada(int CoordenadaX, int CoordenadaY) {
		numeroJugada++;
		if (comprobarGanador(CoordenadaX, CoordenadaY)) {
			bloquearTablero();
			JOptionPane.showMessageDialog(null, "Ha ganado el jugador " + jugadorActual);
		} else {
			if (numeroJugada == tam * tam) {
				JOptionPane.showMessageDialog(null, "El juego ha acabado en empate");
			} else {
				siguienteJugada();
			}
		}
	}

	// Este metodo comprueba si ha ganado el jugador de la casilla pulsada
	private boolean comprobarGanador(int X, int Y) {
		// Cancela la comprobación si no lleva los suficientes turnos como para que haya
		// un ganador
		if (numeroJugada < (tam + tam) - 1) {
			return false;
		}
		// Comprueba si en la fila y columna, únicamente correspodientes a la casilla
		// pulsada hay ganador
		if (comprobarFila(X, jugadorActual)) {
			return true;
		}
		if (comprobarColumna(Y, jugadorActual)) {
			return true;
		}
		// Comprueba la diagonal principal,solo si la casilla pulsada esta dentro de
		// ella
		if (X == Y) {
			if (comprobarDiagonalPrincipal(jugadorActual)) {
				return true;
			}
		}
		// Comprueba la diagonal secundaria, solo si la casilla pulsada esta dentro de
		// ella
		if ((X + Y) == (tam - 1)) {
			if (comprobarDiagonalSecundaria(jugadorActual)) {
				return true;
			}
		}
		return false;
	}

	// Pasa la imagen a la casilla pulsada según el jugador.
	// Además ajusta la imagen a la casilla, según su altura.
	public ImageIcon devolverIconoJugadorActual(int AlturaBoton) {
		if (jugadorActual == 1) {
			return new ImageIcon(new ImageIcon("src/main/jugador1.png").getImage().getScaledInstance(AlturaBoton, AlturaBoton,
					java.awt.Image.SCALE_SMOOTH));
		} else {
			return new ImageIcon(new ImageIcon("src/main/jugador2.png").getImage().getScaledInstance(AlturaBoton, AlturaBoton,
					java.awt.Image.SCALE_SMOOTH));
		}

	}

	public boolean comprobarFila(int x, int JugadorActual) {
		int detectados = 0;
		for (int y = 0; y <= tam - 1; y++) {
			if (tablero[x][y].getJugador() == JugadorActual) {
				detectados++;
			}
		}
		if (detectados == tam) {
			return true;
		}
		return false;
	}

	public boolean comprobarColumna(int y, int JugadorActual) {
		int detectados = 0;
		for (int x = 0; x <= tam - 1; x++) {
			if (tablero[x][y].getJugador() == JugadorActual) {
				detectados++;
			}
		}
		if (detectados == tam) {
			return true;
		}
		return false;
	}

	public boolean comprobarDiagonalPrincipal(int JugadorActual) {
		int detectados = 0;
		for (int xy = 0; xy <= tam - 1; xy++) {
			if (tablero[xy][xy].getJugador() == JugadorActual) {
				detectados++;
			}
		}
		if (detectados == tam) {
			return true;
		}
		return false;
	}

	public boolean comprobarDiagonalSecundaria(int JugadorActual) {
		int detectados = 0;
		int x = 0;
		int y = tam - 1;
		while (x < tam) {
			if (tablero[x][y].getJugador() == JugadorActual) {
				detectados++;
			}
			x++;
			y--;
		}
		if (detectados == tam) {
			return true;
		}
		return false;
	}

	// Pasa de turno
	private void siguienteJugada() {
		if (jugadorActual == 1) {
			jugadorActual = 2;
		} else {
			jugadorActual = 1;
		}
	}

	// recorre todo el tablero para bloquear las casillas no tocadas;
	private void bloquearTablero() {
		for (int x = 0; x <= tam - 1; x++) {
			for (int y = 0; y <= tam - 1; y++) {
				if (tablero[x][y].isEnabled() == true) {
					tablero[x][y].setEnabled(false);
				}
			}
		}
	}
}