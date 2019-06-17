import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Logic {
	private static Menu slide1;
	private static Map slide2;

	public static void main(String[] args) {
		// �� �����̵� ����. ó������ slide1�� ���̰� ��.
		slide1 = new Menu();
		slide2 = new Map();
		slide1.setVisible(true);
		slide2.setVisible(false);
	}

	public static void switchSlide() {
		// �����̵带 �ٲ㼭 ���̰� ��.
		slide1.setVisible(slide1.isVisible() ? false : true);
		slide2.setVisible(slide2.isVisible() ? false : true);
	}
}

class Menu extends JFrame implements ActionListener {
	// ����, ���� ���̿� ���� ���.
	public static final int WIDTH = 400;
	public static final int HEIGHT = 600;
	// ����.
	private int score; // ����.
	private JLabel title; // ���� ����.
	private JLabel scoreLabel; // ���� ǥ��â.
	private JLabel team; // �� �̸�
	private JPanel menuBar; // �޴��� ���� panel.
	private JPanel buttonPanel[]; // ��ư�� ���� panel, button�� ������ �ֱ����� ���.
	private JButton stageButton[]; // ��ư.

	public Menu() {
		// JFrame ����.
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("�׸�׸� ����");
		setLayout(new BorderLayout());

		// JLabel title ����, JFrame�� BorderLayout.NORTH�� add.
		title = new JLabel("�׸�׸� ����");
		add(title, BorderLayout.NORTH);

		/*
		 * JLabel scoreLabel ����, JFrame�� BorderLayout.SOUTH�� add. scoreLabel = new
		 * JLabel("Score : "); add(scoreLabel, BorderLayout.SOUTH);
		 */

		// JLabel team ����, JFrame�� BorderLayout.SOUTH�� add.
		team = new JLabel("JAVA ���α׷��� 2�� - �ֱ��, Ȳ����, ������, ������");
		add(team, BorderLayout.SOUTH);

		// JPanel menuBar ����, GridLayout(3,3).
		JPanel menuBar = new JPanel();
		menuBar.setLayout(new GridLayout(3, 3));

		stageButton = new JButton[3 * 3];
		for (int i = 1; i < 9; i++) {
			// JPanel buttonPanel[i]�� ��ü ����, BorderLayout.
			menuBar.add(stageButton[i] = new JButton("Stage" + i));
			// JButton stageButton[i]�� ��ü ����, BorderLayout.CENTER�� add.
			stageButton[i].addActionListener(this);
		}
		// menuBar�� JFramedml BorderLayout.CENTER�� add.
		this.add(menuBar, BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent e) {
		// slide2�� ��ȯ.
		Logic.switchSlide();
		// slide2�� stage ������ ������(setStage)
	}

	public void scoreAugment() {
		score++;
	}
}

class Map extends JFrame {
	// ����, ���� ���̿� ���� ���.
	public static final int WIDTH = 400;
	public static final int HEIGHT = 600;
	public static final int MAX_STAGE = 8;
	public static final int ROW = 5;
	public static final int COL = 5;

	// ����.
	private int answer[][][]; // �� ���������� ���� �ش��� ��� �ִ� �迭.
	private int user[][]; // ���������� ���� ������ ���� ��� �ִ� �迭.
	private int hint[][]; // �� ���� ǥ�õǴ� ��Ʈ���� ������ ��� �ִ� �迭.
	private int stage; // �� ������������ ����.
	private JLabel stageLabel; // ������� ���������� ǥ����.
	private JPanel mapPanel; // ���� â�� ��� Panel.
	private JPanel submitPanel; // submit ��ư�� ��� Panel.
	private JButton gridButton[][]; // ���� ��ư.
	private JButton submitButton; // ���� ��ư.

	public Map() {
		// JFrame ����.
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Visibility Demonstration");
		setLayout(new BorderLayout());

		gridButton = new JButton[ROW][];
		answer = new int[MAX_STAGE][ROW][COL]; // Answer ��ü ����. ���� ä�����մϴ�.
		user = new int[ROW][COL];
		hint = new int[MAX_STAGE][ROW + COL]; // hint ��ü ����. hint�� ������ ������ ���� ä�������� �˴ϴ�!
		/*
		 * �Ŀ�����Ʈ�� �ִ� ���� �������� ��ܿ� ǥ�õ� hint�� ���ʺ��� ���� �����ϰ�, ���Ŀ� ������ hint�� �����մϴ�. ����
		 * hint�迭�� col ���� ROW+COL�� �˴ϴ�. �̷� �������� �ϴ� ������ for���� �����ϱ� ���� �����Դϴ�. �ϳ��� hint����
		 * ���� ������ ���Ե� �� �ֽ��ϴ�. �̷� ��� ���ǻ� Ŭ������ ������� �ʰ� �ڸ����� �����Ͽ� ���ϴ�. ���� ��� hint[0][0]��
		 * ������ (1, 1)�̶�� �Ѵٸ� hint�迭���� �ڸ����� ������ 11�� �����մϴ�. �̸� ������ �Ŀ�����Ʈ�� �ʿ� ���� hint������
		 * �����Ѵٸ� hint[2]={31,12,12,31,11,4,12,11,2,5}�� �˴ϴ�.
		 * 
		 */
		for (int i = 0; i < ROW; i++) {
			gridButton[i] = new JButton[COL];
			for (int j = 0; j < COL; j++) {
				gridButton[i][j] = new JButton();
				gridButton[i][j].setActionCommand(i + "" + j);
			}
		}

		// JLabel stageLabel ����, JFrame�� BorderLayout.NORTH�� add.
		stageLabel = new JLabel("Stage");
		add(stageLabel, BorderLayout.NORTH);
		// JPanel submitPanel ����, BorderLayout.
		JPanel submitPanel = new JPanel();
		add(submitPanel, BorderLayout.NORTH);
		// JButton submitButton ����. submitPanel�� add.

		// submitPanel�� JFrame�� BorderLayout.SOUTH�� add.

		// JPanel menuBar ����, GridLayout(6,6).
		JPanel menuBar = new JPanel();
		menuBar.setLayout(new GridLayout(6, 6));

		int hintcnt = 0;
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				if (i == 0 && j == 0)
					menuBar.add(new JLabel(""));
				else if (i == 0 || j == 0) {
					JLabel hintLabel = new JLabel(hint[stage][hintcnt] / 100 + "\n" + (hint[stage][hintcnt] / 10) % 10
							+ "\n" + hint[stage][hintcnt] % 10);
					menuBar.add(hintLabel);
					hintcnt++;
				} else {
					menuBar.add(gridButton[i - 1][j - 1]);
				}

			}
		}

		add(menuBar, BorderLayout.CENTER);
	}

	private boolean compareMap() {
		// submit ��ư�� ������ answer�� ���ؼ� �������� �ƴ��� ��.
		// �����̶�� slide1�� scoreAugment()��
		return true;
	}

	public void setStage(int s) {
		// slide1���� 2�� �Ѿ�� �� stage������ ��������.
	}

	private class Grid implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// ��ư�� �������� ��, ��ư�� ����� �ٲ���.
			// ���� user�迭�� �� ������ �����ؾ� ��.
		}

	}

	private class Submit implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// submit ��ư�� ������ �� compareMap()�� �����ؼ�
			// slide1�� score�� �ٲ���.
		}

	}
}