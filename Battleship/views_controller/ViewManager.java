package views_controller;

import javafx.scene.Node;
import model.ClientHandler;
import model.ServerHandler;
import multiplayer_views.MPGameView;
import multiplayer_views.MPShipSelectionView;

public class ViewManager {
	public DebugView debugView;
	private StartScreenView startView;
	private ShipSelectionView shipSelectionView;
	private DefaultGameView defaultGameView;
	private PirateGameView pirateGameView;
	private MultiplayerSetupView multiplayerSetupView;
	private GameEndView gameEndView;
	private InfoView infoView;
	private BattleshipGUI battleshipGUI;
	private MPShipSelectionView mpShipSelectionView;
	private MPGameView mpGameView;
	// Other views...

	private int boardSize = 10;
	
	private boolean pirateUnlocked;

	public ViewManager(BattleshipGUI battleshipGUI) {
		this.battleshipGUI = battleshipGUI;
		pirateUnlocked = false;
		initializeViews();
	}

	private void initializeViews() {
		// Initialize all the views
		startView = new StartScreenView(this);
		defaultGameView = new DefaultGameView(this);
		pirateGameView = new PirateGameView(this);
		shipSelectionView = new ShipSelectionView(this);
		multiplayerSetupView = new MultiplayerSetupView(this);
		gameEndView = new GameEndView(this);
		infoView = new InfoView(this);
		debugView = new DebugView(this);
		mpShipSelectionView = new MPShipSelectionView(this);
		mpGameView = new MPGameView(this);
		// Initialize other views...
	}

	public void changeView(Node newView) {
		battleshipGUI.setViewTo(newView);
	}
	
	public void setPirateUnlocked(boolean lock) {
		pirateUnlocked = lock;
	}
	
	public boolean getPirateUnlocked() {
		return pirateUnlocked;
	}

	public int getBoardSize() {
		return boardSize;
	}

	public void setBoardSize(int x) {
		boardSize = x;
	}

	// Methods to access different views if needed
	public StartScreenView getStartView() {
		return startView;
	}

	public ShipSelectionView getShipSelectionView() {
		return shipSelectionView;
	}

	public MultiplayerSetupView getMultiplayerSetupView() {
		return multiplayerSetupView;
	}

	public InfoView getInfoView() {
		return infoView;
	}

	public DefaultGameView getDefaultView() {
		return defaultGameView;
	}
	
	public PirateGameView getPirateView() {
		return pirateGameView;
	}

	public DebugView getDebugView() {
		return debugView;
	}

	public GameEndView getGameEndView() {
		return gameEndView;
	}

	public MPShipSelectionView getMPShipSelectionView() {
		return mpShipSelectionView;
	}
	
	public MPGameView getMPGameView() {
		return mpGameView;
	}

//	@Override
//	public void onObjectReceived(Object object) {
//		if (object == ServerHandler.ServerComms.PLAYER_1) {
//			System.out.println("baller1");
//			changeView(shipSelectionView);
//			//vm.changeView(vm.getMPShipSelectionView());
//			
//		}
//		if (object == ServerHandler.ServerComms.PLAYER_2) {
//			System.out.println("baller2");
//			changeView(shipSelectionView);
//			//vm.changeView(vm.getMPShipSelectionView());
//		}
//		
//	}
}
