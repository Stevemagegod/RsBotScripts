package org.tyler.powertrainer.nodes;

import java.util.ArrayList;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.node.Item;
import org.tyler.powertrainer.mainclass.TPowerTrainer;

public class Droping extends Node {
	public static String toolName = "";
	@Override
	public boolean activate() {
		return Players.getLocal() != null && Inventory.isFull();
	}

	@Override
	public void execute() {
		final int pickId = getItemId(toolName) != null	? getItemId(toolName)[0]: 0;
		TPowerTrainer.status = "droping everything";
		
		for (Item item : Inventory.getItems()) {
			if (item.getId() == pickId)
				continue;
			if (item.getWidgetChild().interact("drop", item.getName())) {
				Timer timing = new Timer(2000);
				while (timing.isRunning() && item.getWidgetChild().validate()) {
					Task.sleep(200);
				}
			}
		}
	}

	public int[] getItemId(final String... names) {
		if (Inventory.getCount() == 0) {
			return null;
		}

		ArrayList<Integer> ids = new ArrayList<Integer>();
		for (Item item : Inventory.getItems()) {
			for (String name : names) {
				if (item.getName().toLowerCase().contains(name.toLowerCase())) {
					ids.add(item.getId());
					break;
				}
			}
		}
		return listToArray(ids);
	}

	private int[] listToArray(ArrayList<Integer> arrayList) {
		int[] intArray = new int[arrayList.size()];
		for (int x = 0; x < arrayList.size(); x++) {
			intArray[x] = arrayList.get(x).intValue();
		}
		return intArray;
	}

}
