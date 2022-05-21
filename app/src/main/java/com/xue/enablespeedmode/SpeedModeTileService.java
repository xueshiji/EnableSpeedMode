package com.xue.enablespeedmode;

import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.util.Log;

import com.topjohnwu.superuser.Shell;

public class SpeedModeTileService extends TileService {
    static {
        // Set settings before the main shell can be created
        Shell.enableVerboseLogging = BuildConfig.DEBUG;
        Shell.setDefaultBuilder(Shell.Builder.create()
                .setFlags(Shell.FLAG_REDIRECT_STDERR)
                .setTimeout(10)
        );
    }

    private Thread updateTread = new Thread(() -> {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            update();
        }
    });

    @Override
    public void onTileAdded() {
        super.onTileAdded();
        update();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void update() {
        //Boolean appGrantedRoot = Shell.isAppGrantedRoot();
        Tile qsTile = getQsTile();
//        if (appGrantedRoot == null || !appGrantedRoot) {
//            qsTile.setState(Tile.STATE_UNAVAILABLE);
//        } else {
        boolean speedModeEnable = SpeedModeUtil.isSpeedModeEnable(this);
        qsTile.setState(speedModeEnable ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
        //}
        qsTile.updateTile();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        update();
        if (updateTread.getState().equals(Thread.State.NEW)) {
            updateTread.start();
        }
    }

    @Override
    public void onClick() {
        super.onClick();
        Log.d("quick settings", "SpeedModeTileService onClick ++++++++++++++");
        boolean speedModeEnable = SpeedModeUtil.isSpeedModeEnable(this);
        SpeedModeUtil.setSpeedMode(this, !speedModeEnable, false, false);
        update();
    }
}
