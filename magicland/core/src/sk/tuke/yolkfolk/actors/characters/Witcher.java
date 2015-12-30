/***********************************************************
 * Zadanie na predmet Objektove Programovanie
 * <p/>
 * Stefan Ciberaj, ZS 2015/2016
 * Technicka univerzita v Kosiciach, Fakulta elektrotechniky a informatiky
 * <p/>
 * Licencia: Volny softver, Open-Source GNU GPL v3+
 * Vseobecna verejna licencia. Program je dovolene volne sirit a upravovat.
 * Upraveny program / cast programu moze ktokolvek vyuzit ako na osobne,
 * tak aj komercne ucely, ale nemoze ho vydat s vlastnym copyrightom,
 * ktory nie je kompatibilny s GNU GPL v3+.
 * gnu.org/licenses/gpl-faq.html
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see < http://www.gnu.org/licenses/ >.
 */

package sk.tuke.yolkfolk.actors.characters;

import sk.tuke.gamelib2.Actor;
import sk.tuke.gamelib2.Item;
import sk.tuke.gamelib2.Message;
import sk.tuke.yolkfolk.actors.AbstractActor;
import sk.tuke.yolkfolk.actors.characters.ghost.Ghost;
import sk.tuke.yolkfolk.actors.characters.ghost.GhostImpl;
import sk.tuke.yolkfolk.actors.characters.ghost.GreenGhostDecorator;
import sk.tuke.yolkfolk.actors.player.Player;

import java.util.Random;

/**
 * Ally of the Dizzy.
 * <p/>
 * Created by Steve on 28.12.2015.
 */
public class Witcher extends AbstractActor implements Item
{
	//Constants
	public static final String NAME = "Witcher";
	//Pocet duchov, kolko musi Witcher vytvorit, aby sa objavila nova sprava
	//Hodnota sa odporuca pouzit dost vysoka na to, aby si hrac stihol precitat prvu uvodnu spravu pred jej nahradenim
	public static final int MESSAGE_INTERVAL = 15;

	//Variables
	private int ghostTimer;
	private Message message;
	//private boolean initialization;
	private int messageCounter;

	//Objects
	//private PrinceSpace princeSpace;
	//private Player player;

	public Witcher()
	{
		super(Witcher.NAME, "sprites/witcher.png", 48, 48);
		this.ghostTimer = 0;
		this.message = null;
		//this.initialization = false;
		this.messageCounter = 0;
		//this.princeSpace = null;
		//this.player = null;
	}

	private void makeGhost()
	{
		Ghost ghost = new GreenGhostDecorator(new GhostImpl());
		ghost.setPosition(getX(), getY() + ghost.getHeight() / 2);
		getWorld().addActor(ghost);
	}

	/*
	//Inicializacia objektov suvisiacich s Witcherom, ktora sa ma vykonat v prvom cykle hry
	@Deprecated
	private void initIfNeeded()
	{
		if (!this.initialization)
		{
			//Witcher finds the Player-detecting space of the Prince (only first one, assumes no more are in the map)
			for (Actor actor : getWorld())
			{
				if (actor instanceof PrinceSpace)
				{
					this.princeSpace = (PrinceSpace) actor;
					break;
				}
			}
		}
	}
	*/

	//Po pozdraveni hraca zacne pisat nahodne spravy
	protected void randomMessage()
	{
		if (this.messageCounter > Witcher.MESSAGE_INTERVAL)
		{
			this.messageCounter = 0;
			this.message.remove();

			switch (new Random().nextInt(3))
			{
				case 0:
					this.message = new Message("This prince is strong.",
					                           "I have never met such an\n" +
					                           "experienced ghost caster,\n" +
					                           "other than me of course.",
					                           this);
					break;

				case 1:
					this.message = new Message("I am losing power.",
					                           "Does he have infinite Mana?\n" +
					                           "What a cheater.", this);
					break;

				case 2:
					this.message = new Message(getPlayer().getName() + ", hurry up!",
					                           "We don't have much time.\n" +
					                           "I don't wanna get defeated here.", this);
					break;
			}
		}
	}

	@Override
	public void act()
	{
		//V prvom cykle actu inicializuje svoje objekty
		//initIfNeeded();

		//Prveho hraca pozdravi a povie mu, co ma robit
		if (this.message == null /*&& this.princeSpace != null*/)
		{
			for (Actor actor : getWorld())
			{
				if (actor instanceof Player && actor.intersects(this))
				{
					//this.player = (Player) actor;
					this.message = new Message("Do you want to defeat the Prince?",
					                           "No offense, but you are very weak.\n" +
					                           "You are lucky I am here, but I need your help.\n" +
					                           "Can you bring me the sacred Mana Potion?",
					                           this);
				}
			}
		}

		if (this.ghostTimer >= Prince.GHOST_INTERVAL)
		{
			makeGhost();
			this.ghostTimer = 0;

			//Pokial bol uz hrac pozdraveny, zacne s nahodnymi spravami
			if (this.message != null)
			{
				this.messageCounter++;
				randomMessage();
			}
		}
		else
		{
			this.ghostTimer++;
		}
	}
}
