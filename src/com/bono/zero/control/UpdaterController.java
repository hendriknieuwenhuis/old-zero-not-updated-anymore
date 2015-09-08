package com.bono.zero.control;

import com.bono.zero.model.Command;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>Title: UpdaterController.java</p>
 *
 * <p>Description: UpdaterController.java is the controller for the
 * updater that updates the models.
 * The updater is the inner class <code>ModelUpdater</code>.</p>
 * Created by hennihardliver on 30/05/14.
 */
public class UpdaterController {

    private final String PLAYER   = "changed: player";
    private final String PLAYLIST = "changed: playlist";
    private final String MIXER    = "changed: mixer";
    private final String OPTIONS  = "changed: options";
    private final String DATABASE = "changed: database";

    private final int PLAYLIST_UPDATE  = 0;
    private final int STATUS_UPDATE    = 1;
    private final int DIRECTORY_UPDATE = 2;

    private ExecutorService executor;

    private Controller controller;

    public UpdaterController(Controller controller) {
        this.controller = controller;
        executor = Executors.newFixedThreadPool(10);
    }

    /**
     * Initializing the model values by calling the server for
     * an update of the current playlist, status and the
     * directory structure os the "music" folder.
     * This method is called only on start of the application
     * for now.
     */
    public void initModels() {
        executor.execute(new ModelUpdater(PLAYLIST_UPDATE));
        executor.execute(new ModelUpdater(STATUS_UPDATE));
        executor.execute(new ModelUpdater(DIRECTORY_UPDATE));
    }

    /*
	 * Method that sends for an update of the monitored
	 * change in server state. All different kind of
	 * updates are seperate threads so they do not
	 * block each other when being executed simultaneous.
	 */
    public void update(String read) {
        if (read.equals(PLAYLIST)) {
            executor.execute(new ModelUpdater(PLAYLIST_UPDATE));
            executor.execute(new ModelUpdater(STATUS_UPDATE));
        } else if (read.equals(DATABASE)) {
            executor.execute(new ModelUpdater(DIRECTORY_UPDATE));
        } else {
            executor.execute(new ModelUpdater(STATUS_UPDATE));
        }
    }

    private class ModelUpdater implements Runnable {

        private int update;   // the update to be made.

        public ModelUpdater(int update) {
            this.update = update;
        }

        @Override
        public void run() {
            switch (update) {
                case PLAYLIST_UPDATE:
                    try {
                        controller.getPlaylist().populatePlaylist(controller.getServer().sendCommand(new Command("playlistinfo")));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case STATUS_UPDATE:
                    try {
                        controller.getServerStatus().setStatus(controller.getServer().sendCommand(new Command("status")));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case DIRECTORY_UPDATE:
                    try {
                        //controller.getDirectory().setDirectory(controller.getServer().sendCommand(new Command("listall")));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("Update does not exist!");
                    break;
            }

        }
    }
}
