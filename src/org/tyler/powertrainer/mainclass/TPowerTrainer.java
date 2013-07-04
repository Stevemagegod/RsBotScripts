package org.tyler.powertrainer.mainclass;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.input.Mouse;
import org.tyler.powertrainer.gui.Gui;
import org.tyler.powertrainer.nodes.Droping;
import org.tyler.powertrainer.nodes.Training;
/**
 * 
 * @author _Tyler_ 2013-07-02
 * 
 */

@Manifest(authors = {"_Tyler_"}, description = "powertraine anything", name = "TPowerTrainer")

public class TPowerTrainer extends ActiveScript implements PaintListener,MessageListener {

	private Tree jobs = new Tree(new Node[]{new Training(), new Droping()});
	private Gui gui;

	public void onStart() {
		startTime = System.currentTimeMillis();
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				gui = new Gui();
			}
		});
	}

	@Override
	public int loop() {
		if ((gui != null && gui.isVisible()) || !Game.isLoggedIn()) {
			return 2500;
		}
		if (jobs != null) {
			final Node job = jobs.state();
			if (job != null) {
				jobs.set(job);
				getContainer().submit(job);
				job.join();
			}
		}

		return 600;
	}

	private final String[] messageString = {"You get some logs","You manage to mine"};

	@Override
	public void messageReceived(MessageEvent event) {
		for (String message : messageString) {
			if (event.getMessage().contains(message)) {
				objectGained++;
				break;
			}

		}

	}

	private final Color color1 = new Color(50, 50, 50, 112);
	private final Color color2 = new Color(0, 0, 0);
	private final Color color3 = new Color(51, 153, 255);
	private final Color color4 = new Color(255, 255, 255);
	private final BasicStroke stroke1 = new BasicStroke(2);

	private final Font font1 = new Font("Times New Roman", 1, 19);
	private final Font font2 = new Font("Arial", 1, 12);
	private final Font font3 = new Font("Times New Roman", 1, 12);

	private long startTime = 0;
	private long millis = 0;
	private long hours = 0;
	private long minutes = 0;
	private long seconds = 0;

	private int objectCollectedHour = 0;
	private int objectGained = 0;
	public static String status = "";

	public void onRepaint(Graphics g1) {

		millis = System.currentTimeMillis() - startTime;
		hours = millis / (1000 * 60 * 60);
		millis -= hours * (1000 * 60 * 60);
		minutes = millis / (1000 * 60);
		millis -= minutes * (1000 * 60);
		seconds = millis / 1000;

		objectCollectedHour = (int) ((objectGained) * 3600000D / (System.currentTimeMillis() - startTime));

		Graphics2D g = (Graphics2D) g1;
		g.setColor(Mouse.isPressed() ? Color.BLUE : Color.CYAN);
		g.fillOval(Mouse.getX(), Mouse.getY(), 19, 17);
		g.setColor(color2);
		g.setStroke(stroke1);
		g.drawOval(Mouse.getX(), Mouse.getY(), 19, 17);
		g.setColor(color1);
		g.fillRoundRect(546, 256, 190, 254, 16, 16);
		g.setColor(color2);
		g.setStroke(stroke1);
		g.drawRoundRect(546, 256, 190, 254, 16, 16);
		g.setFont(font1);
		g.setColor(color3);
		g.drawString("TPowerTrainer ", 550, 284);
		g.setFont(font2);
		g.setColor(color4);
		g.drawString("Status :" + status, 549, 319);
		g.drawString("Time running :" + hours + ":" + minutes + ":" + seconds,549, 355);
		g.drawString("XP gained (hour) :" + "0(0)", 552, 428);
		g.drawString("By  _tyler_", 550, 494);
		g.drawString("Objects mined/cutted (hour) : " + objectGained + " ("	+ objectCollectedHour + ")", 552, 465);
		g.setFont(font3);
		g.setColor(color3);
		g.drawString("v1.00", 701, 283);
	}

}
