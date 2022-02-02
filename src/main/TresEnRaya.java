package main;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class TresEnRaya {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setTitle("Tres en Raya");
		frame.setBounds(300, 300, 450, 300);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);//aparición centrada
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		LaminaPrincipal laminaPrincipal = new LaminaPrincipal();
		frame.add(laminaPrincipal);
		frame.setVisible(true);
	}
}

class LaminaPrincipal extends JPanel {
	private static final long serialVersionUID = 1L;

	// Tamaño del tablero, puedes modificarlo y jugar a "lo que quieras en raya"
	// (Si lo subes mucho necesitaras mucha resolución y una lupa)
	static int tam = 3;

	// La clase juego controla la lógica del juego
	static Juego Juego;
	JPanel LaminaTresEnRaya = new JPanel(new GridLayout(tam, tam));
	JPanel LaminaArriba = new JPanel();

	public LaminaPrincipal() {
		setLayout(new BorderLayout());

		JButton comenzar = new JButton("Comenzar");
		comenzar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Resetea el juego
				LaminaTresEnRaya.removeAll();
				// Creación del tablero de botones
				BotonTresEnRaya tablero[][] = new BotonTresEnRaya[tam][tam];
				for (int x = 0; x <= tam - 1; x++) {
					for (int y = 0; y <= tam - 1; y++) {
						// Cada botón recibe su cordenada en el tablero para su uso posterior
						tablero[x][y] = new BotonTresEnRaya(x, y);
						LaminaTresEnRaya.add(tablero[x][y]);
					}
				}
				Juego = new Juego(tablero, tam);

				// Resetea el panel
				LaminaTresEnRaya.revalidate();
				LaminaTresEnRaya.repaint();
			}
		});
		LaminaArriba.add(generarTitulo());
		LaminaArriba.add(comenzar);

		add(LaminaTresEnRaya, BorderLayout.CENTER);
		add(LaminaArriba, BorderLayout.NORTH);
		add(generarFooter(), BorderLayout.SOUTH);
	}

	class BotonTresEnRaya extends JButton {
		private static final long serialVersionUID = -5243986073617792630L;
		private int jugador = 0;
		private int coordenadaX = 0;
		private int coordenadaY = 0;

		public BotonTresEnRaya(int x, int y) {
			this.coordenadaX = x;
			this.coordenadaY = y;
			addActionListener(new ActionListener() {
				@Override
				// Accciones que realizará cada botón que se pulse
				public void actionPerformed(ActionEvent e) {
					BotonTresEnRaya Btmp = (BotonTresEnRaya) e.getSource();
					// Recibe el icono y el jugador correspondiente al jugador del turno de ese
					// momento
					Btmp.setIcon(Juego.devolverIconoJugadorActual(Btmp.getHeight()));
					Btmp.setJugador(Juego.getJugadorActual());
					// lo deshabilitamos
					Btmp.setEnabled(false);
					// Este metodo comprueba si alguien gana y pasa a la siguiente jugada
					// Se le pasan coordenadas del botón actual, para que solo
					// compruebe las casillas relacionadas con la pulsada
					Juego.jugada(Btmp.coordenadaX, Btmp.coordenadaY);
				}
			});
		}

		public int getJugador() {
			return jugador;
		}

		public void setJugador(int jugador) {
			this.jugador = jugador;
		}
	}

	private static JLabel generarFooter() {
		JLabel JLFooter = new JLabel("Autor: 5yex.", SwingConstants.LEFT);
		JLFooter.setFont(new Font("arial", Font.ITALIC, 12));
		JLFooter.setBorder(new EmptyBorder(6, 8, 6, 0));
		JLFooter.setBackground(Color.BLACK);
		JLFooter.setOpaque(true);
		JLFooter.setForeground(Color.WHITE);
		return JLFooter;
	}

	private static JLabel generarTitulo() {
		JLabel JLtitulo = new JLabel("Bienvenido al Tres en Raya");
		JLtitulo.setFont(new Font("arial", Font.BOLD, 19));
		JLtitulo.setBorder(new EmptyBorder(10, 0, 10, 40));
		return JLtitulo;
	}
}
