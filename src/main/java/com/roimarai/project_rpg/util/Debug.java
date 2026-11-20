package com.roimarai.project_rpg.util;

import com.roimarai.project_rpg.config.DebugMode;
import com.roimarai.project_rpg.config.GameConfig;

public final class Debug {
	private Debug() {}
	
	public static void warn(String msg) {
		log(msg, false);
	}
	
	public static void crucial(String msg) {
		log(msg, true);
	}
	
	private static void log(String msg, boolean isCrucial) {
		if (GameConfig.DEBUG_MODE == DebugMode.OFF) {
			return;
		}
		
		if (isCrucial) {
			System.out.println("Crucial Warning | " + msg);
		} else if (GameConfig.DEBUG_MODE == DebugMode.ALL) {
			System.out.println("Warning | " + msg);
		}
	}
}
