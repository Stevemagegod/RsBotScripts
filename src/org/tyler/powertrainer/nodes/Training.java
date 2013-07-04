package org.tyler.powertrainer.nodes;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.tyler.powertrainer.mainclass.TPowerTrainer;

public class Training extends Node {

	public static int[] objectIds = null;
	public static String actionName = "";
	public static boolean prioritise = false;

	@Override
	public boolean activate() {
		return Inventory.getCount() != 28 && Players.getLocal() != null
				&& Players.getLocal().getAnimation() == -1;
	}

	@Override
	public void execute() {
		SceneObject object = prioritise	? getPriorityEntitie(objectIds) : SceneEntities.getNearest(objectIds);
		if (object == null) {
			return;
		}

		if (!object.isOnScreen()) {
			TPowerTrainer.status = "Walking to the entitie";
			Walking.walk(object);
		} else {
			TPowerTrainer.status = "Interacting with the entitie";
			if (interact(object, actionName)) {
				Timer time = new Timer(2000);
				while (time.isRunning() && Players.getLocal().getAnimation() == -1) {
					Task.sleep(200);
				}
			}
		}

	}
	private SceneObject getPriorityEntitie(int... ids) {
		if (ids == null) {
			return null;
		}
		for (SceneObject rock : SceneEntities.getLoaded(ids)) {
			if (rock != null) {
				return rock;
			}
		}
		return null;
	}

	private boolean interact(SceneObject object, String action) {
		Mouse.move(object.getCentralPoint());

		for (String actionName : Menu.getActions()) {
			if (actionName.contains(action)) {
				return Menu.select(action);
			}
		}
		return false;
	}
}
