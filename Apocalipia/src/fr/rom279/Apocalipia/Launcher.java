package fr.rom279.Apocalipia;


import java.io.File;

import fr.theshark34.openauth.AuthPoints;
import fr.theshark34.openauth.AuthenticationException;
import fr.theshark34.openauth.Authenticator;
import fr.theshark34.openauth.model.AuthAgent;
import fr.theshark34.openauth.model.response.AuthResponse;
import fr.theshark34.openlauncherlib.launcher.AuthInfos;
import fr.theshark34.openlauncherlib.launcher.GameInfos;
import fr.theshark34.openlauncherlib.launcher.GameTweak;
import fr.theshark34.openlauncherlib.launcher.GameType;
import fr.theshark34.openlauncherlib.launcher.GameVersion;
import fr.theshark34.supdate.BarAPI;
import fr.theshark34.supdate.SUpdate;
import fr.theshark34.swinger.Swinger;


public class Launcher {

    public static final GameVersion AP_VERSION = new GameVersion("1.7.10", GameType.V1_7_10);
    public static final GameInfos AP_INFOS = new GameInfos ("Apocalipia",AP_VERSION, true , new GameTweak[]{GameTweak.FORGE});
    public static final File AP_DIR = AP_INFOS.getGameDir();

    private static AuthInfos authInfos;
    private static Thread updateThread;
    
    public static void auth(String username) throws AuthenticationException {
    	Authenticator authenticator = new Authenticator(Authenticator.MOJANG_AUTH_URL, AuthPoints.NORMAL_AUTH_POINTS);
    	AuthResponse response = authenticator.authenticate(AuthAgent.MINECRAFT, username, null, null);
    	authInfos = new AuthInfos(response.getSelectedProfile().getName(), response.getAccessToken(), response.getSelectedProfile().getId());}

	public static void auth(String text, String text2) {
		// TODO Auto-generated method stub
		
	}

	public static void update() throws Exception {
		SUpdate su = new SUpdate("", AP_DIR);
		
		updateThread = new Thread() {
			private int val;
			private int max;
			
			@Override
			public void run() {
				while(this.isInterrupted()) {
					val = (int) (BarAPI.getNumberOfTotalDownloadedBytes() / 1000);
					max = (int) (BarAPI.getNumberOfTotalBytesToDownload() / 1000);
					
					LauncherFrame.getInstance().getLauncherPanel().getProgressBar().setMaximum(max);
					LauncherFrame.getInstance().getLauncherPanel().getProgressBar().setMaximum(val);
					
					LauncherFrame.getInstance().getLauncherPanel().setInfoText("Telechargement des fichiers " +
						BarAPI.getNumberOfDownloadedFiles() + "/" + BarAPI.getNumberOfFileToDownload() +
							Swinger.percentage(val, max) + "%");
				}
			}
		};
		updateThread.start();
		
		su.start();
		updateThread.interrupt();
	}
	
	public static void interruptThread() {
		updateThread.interrupt();
		
	}

	public static void interruptThread1() {
		// TODO Auto-generated method stub
		
	}
}