package pj;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Logicu {
	private static Menu slide1;
	private static Map slide2;

	public static void main(String[] args) {
		// 두 슬라이드 생성. 처음에는 slide1을 보이게 함.
		slide1 = new Menu();
		slide2 = new Map();
		slide1.setVisible(true);
		slide2.setVisible(false);
	}

	public static void switchSlide() {
		// 슬라이드를 바꿔서 보이게 함.
		slide1.setVisible(slide1.isVisible() ? false : true);
		slide2.setVisible(slide2.isVisible() ? false : true);
	}
}

class Menu extends JFrame implements ActionListener {
	// 가로, 세로 길이에 대한 상수.
	public static final int WIDTH = 400;
	public static final int HEIGHT = 600;
	// 변수.
	private int score; // 점수.
	private JLabel title; // 게임 제목.
	private JLabel scoreLabel; // 점수 표시창.
	private JLabel team; // 조 이름
	private JPanel menuBar; // 메뉴를 담을 panel.
	private JPanel buttonPanel[]; // 버튼을 담을 panel, button에 마진을 주기위해 사용.
	private JButton stageButton[]; // 버튼.

	public Menu() {
		// JFrame 설정.
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("네모네모 로직");
		setLayout(new BorderLayout());

		// JLabel title 생성, JFrame의 BorderLayout.NORTH에 add.
		title = new JLabel("네모네모 로직");
		add(title, BorderLayout.NORTH);

		/*
		 * JLabel scoreLabel 생성, JFrame의 BorderLayout.SOUTH에 add. scoreLabel = new
		 * JLabel("Score : "); add(scoreLabel, BorderLayout.SOUTH);
		 */

		// JLabel team 생성, JFrame의 BorderLayout.SOUTH에 add.
		team = new JLabel("JAVA 프로그래밍 2조 - 최기락, 황정인, 김현유, 박종원");
		add(team, BorderLayout.SOUTH);

		// JPanel menuBar 생성, GridLayout(3,3).
		JPanel menuBar = new JPanel();
		menuBar.setLayout(new GridLayout(3, 3));

		stageButton = new JButton[3 * 3];
		for (int i = 1; i < 9; i++) {
			// JPanel buttonPanel[i]의 객체 생성, BorderLayout.
			menuBar.add(stageButton[i] = new JButton("Stage" + i));
			// JButton stageButton[i]의 객체 생성, BorderLayout.CENTER에 add.
			stageButton[i].addActionListener(this);
		}
		// menuBar을 JFramedml BorderLayout.CENTER에 add.
		this.add(menuBar, BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent e) {
		// slide2로 전환.
		Logicu.switchSlide();
		// slide2의 stage 정보를 정해줌(setStage)
	}

	public void scoreAugment() {
		score++;
	}
}

class Map extends JFrame {
	// 가로, 세로 길이에 대한 상수.
	public static final int WIDTH = 400;
	public static final int HEIGHT = 600;
	public static final int MAX_STAGE = 8;
	public static final int ROW = 5;
	public static final int COL = 5;

	// 변수.
	private int answer[][][]; // 각 스테이지에 대한 해답을 담고 있는 배열.
	private int user[][]; // 스테이지에 대한 유저의 답을 담고 있는 배열.
	private int hint[][]; // 맵 옆에 표시되는 힌트들의 정보를 담고 있는 배열.
	private int stage; // 몇 스테이지인지 저장.
	private JLabel stageLabel; // 제목란에 스테이지를 표시함.
	private JPanel mapPanel; // 게임 창을 담는 Panel.
	private JPanel submitPanel; // submit 버튼을 담는 Panel.
	private JPanel buttonPanel;
	private JButton gridButton[][]; // 게임 버튼.
	private JButton submitButton; // 제출 버튼.

	public Map() {
		// JFrame 설정.
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Visibility Demonstration");
		setLayout(new BorderLayout());

		gridButton = new JButton[ROW][];
		answer = new int[MAX_STAGE][ROW][COL]; // Answer 객체 생성. 내용 채워야합니다.
		user = new int[ROW][COL];
		hint = new int[MAX_STAGE][ROW + COL]; // hint 객체 생성. hint의 정보는 다음과 같이 채워넣으면 됩니다!
		/*
		 * 파워포인트에 있는 맵을 기준으로 상단에 표시된 hint를 왼쪽부터 먼저 저장하고, 이후에 좌측의 hint를 저장합니다. 따라서
		 * hint배열의 col 수는 ROW+COL이 됩니다. 이런 방향으로 하는 이유는 for문에 대응하기 쉽기 때문입니다. 하나의 hint에는
		 * 여러 정보가 포함될 수 있습니다. 이런 경우 편의상 클래스를 사용하지 않고 자릿수로 구분하여 씁니다. 예를 들어 hint[0][0]의
		 * 정보가 (1, 1)이라고 한다면 hint배열에는 자릿수로 구분해 11을 저장합니다. 이를 적용해 파워포인트의 맵에 대한 hint정보를
		 * 저장한다면 hint[2]={31,12,12,31,11,4,12,11,2,5}가 됩니다.
		 * 
		 */
		for (int i = 0; i < ROW; i++) {
			gridButton[i] = new JButton[COL];
			for (int j = 0; j < COL; j++) {
				gridButton[i][j] = new JButton();
				gridButton[i][j].setActionCommand(i + "" + j);
				gridButton[i][j].addActionListener(new Grid());
			}
		}

		// JLabel stageLabel 생성, JFrame의 BorderLayout.NORTH에 add.
		stageLabel = new JLabel("Stage");
		add(stageLabel, BorderLayout.NORTH);
		// JPanel submitPanel 생성, BorderLayout.
		JPanel submitPanel = new JPanel();
		add(submitPanel, BorderLayout.NORTH);
		// JButton submitButton 생성. submitPanel에 add.
		JButton submitButton= new JButton("Submit");
		submitButton.addActionListener(new Submit());
		submitPanel.add(submitButton);

		// submitPanel을 JFrame의 BorderLayout.SOUTH에 add.
		add(submitPanel, BorderLayout.SOUTH);

		// JPanel menuBar 생성, GridLayout(6,6).
		JPanel menuBar = new JPanel();
		menuBar.setLayout(new GridLayout(6, 6));

		for(int i=0;i<MAX_STAGE;i++) {
			for(int j=0;j<ROW;j++) {
				for(int k=0;k<COL;k++)
					answer[i][j][k]=0;
			}
		}
		
		answer[0][1][1]=1;
		answer[0][1][3]=1;
		answer[0][2][0]=1;
		answer[0][2][4]=1;
		answer[0][3][1]=1;
		answer[0][3][3]=1;
		answer[0][4][2]=1;
		//정답 배열을 만들어 두고, 입력받는 정답배열도 만들어야하지 않는가.
		
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
		}//여기서 쓰인 힌트가 의미하는 바가 무엇인가? 
		
		/*buttonPanel=new JPanel();
		buttonPanel.setLayout(new GridLayout(6,6));
		
		for(int i=0;i<ROW;i++) {
			buttonLabel[i]=new JLabel[COL];
			for(int j=0;j<COL;j++) {
				buttonLabel[i][j]=new JLabel();
				buttonLabel[i][j].add(gridButton[i][j]);
				buttonPanel.add(buttonLabel[i][j]);
			}
		}
		
		
		add(buttonPanel,BorderLayout.CENTER);
		*/
		add(menuBar, BorderLayout.CENTER);
	}

	private boolean compareMap() {
		// submit 버튼을 누르면 answer과 비교해서 정답인지 아닌지 비교.
		// 정답이라면 slide1의 scoreAugment()실
		return true;
	}

	public void setStage(int s) {
		// slide1에서 2로 넘어올 때 stage정보를 설정해줌.
	}

	private class Grid implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// 버튼을 눌러줬을 때, 버튼의 배경을 바꿔줌.
			gridButton[i][j].setBackground(Color.BLUE);
			//버튼을 누를때 i랑 j값을 받아와서 해당하는 버튼의 색갈을 바꿀수 있게 해야함.
			// 또한 user배열에 이 정보를 저장해야 함.
			
		}
	}

	private class Submit implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// submit 버튼을 눌렀을 때 compareMap()을 실행해서
			// slide1의 score을 바꿔줌.
			if(compareMap())
				score++;//점수를 맨처음 초기화면에서 부터 이 맵까지 사용할 수 있는 전역변수가 필요.
		}

	}
}
