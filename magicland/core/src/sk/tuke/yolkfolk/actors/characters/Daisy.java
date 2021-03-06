/***********************************************************
 * Zadanie na predmet Objektove Programovanie
 * <p/>
 * scsc
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
import sk.tuke.yolkfolk.actors.Greeter;
import sk.tuke.yolkfolk.actors.Usable;
import sk.tuke.yolkfolk.actors.items.FinalDoor;
import sk.tuke.yolkfolk.actors.player.Player;
import sk.tuke.yolkfolk.cinematic.CinematicEffect;
import sk.tuke.yolkfolk.cinematic.DaisyIsFree;
import sk.tuke.yolkfolk.collectables.Ring;
import sk.tuke.yolkfolk.spaces.ExitZone;

/**
 * A generic Greeter example. Can be possibly saved by our Hero.
 * <p/>
 * Daisy is not an item, but needs to intersect.
 * <p/>
 * Created by Steve on 10.11.2015.
 */
public class Daisy extends AbstractActor implements Greeter, Item, Usable
{
	//Constants
	public static final String NAME = "Daisy";
	//Celkovy pocet pozdravov, ktore budu unikatne
	public static final int GREETINGS_NUMBER = 3;
	//Vzdialenost v nasobkoch velkosti Greetera, na ktoru ked sa hrac vzdiali, dostane za odmenu dalsi pozdrav
	public static final int FORGETTING_RADIUS = 14;

	//Variables
	//Informacia o tom, ci hrac dostal uz pozdrav, alebo ma dostat dalsi
	private boolean greetingDone;
	//Aktualny pozdrav
	private int currentGreeting;
	//Stav, ci ma na ruke prsten
	private boolean hasRing;
	//Je na konci hry
	private boolean atExit;
	//Hotfix kniznice GameLib, setPosition funguje iba pocas metody act(), premenna pre hackPosition(int, int)
	private float scheduledPosition[];

	//Objects
	//Aktualna sprava zobrazena hracovi
	private Message currentMessage;
	//Instancia prveho hraca, ktory bol pozdraveny. Aby to nebolo trapne, tak sa inym zdravit nebude.
	private Player firstPlayer;
	//Daisy's ring
	private Ring ring;
	//Devil, that owns Daisy
	private Devil devil;
	//Cinematic experience
	private CinematicEffect cinematic;

	public Daisy()
	{
		super(Daisy.NAME, "sprites/daisy.png", 25, 25);
		this.greetingDone = false;
		this.currentGreeting = 0;
		this.hasRing = true;
		this.atExit = false;
		this.scheduledPosition = new float[3];

		this.currentMessage = null;
		this.firstPlayer = null;
		this.ring = new Ring(this);
		this.devil = null;
		this.cinematic = null;
	}

	//Hotfix kniznice GameLib, setPosition funguje iba pocas metody act(), preto je toto oneskoreny setPosition
	protected final void hackPosition(float x, float y)
	{
		//Pozicia 0 reprezentuje predikat
		this.scheduledPosition[0] = 1;
		this.scheduledPosition[1] = x;
		this.scheduledPosition[2] = y;
	}

	protected void prepareCinematic()
	{
		if (this.firstPlayer != null)
		{
			this.cinematic = new CinematicEffect(new DaisyIsFree(), this.firstPlayer);
		}
	}

	//Immediately goes to the exit. Also initializes the cinematic experience.
	private void goToExit()
	{
		for (Actor actor : getWorld())
		{
			if (actor instanceof ExitZone)
			{
				hackPosition(actor.getX() + getWidth(), actor.getY());

				//The devil also visits her
				if (this.devil != null)
				{
					this.devil.setPosition(actor.getX(), actor.getY());
					getWorld().addActor(this.devil);
					this.devil.daisyEnd();
				}
				break;
			}
		}

		//Prepares the cinematic to be run
		prepareCinematic();
		this.atExit = true;
	}

	//Checks if the door is open and if it is, runs there before Player does. Only returns true before first cinematic.
	private boolean readyToLeave()
	{
		if (!this.atExit)
		{
			for (Actor actor : getWorld())
			{
				if (actor instanceof FinalDoor && ((FinalDoor) actor).isUnlocked())
				{
					return true;
				}
			}
		}
		return false;
	}

	//As a Greeter, Daisy must implement this method and expect to be called by it when intersecting Player
	public boolean greetPlayer(Player player)
	{
		//First met player will be remembered forever
		if (this.firstPlayer == null)
		{
			this.firstPlayer = player;
		}

		//If the exit door is open, Daisy stops greeting Player and instead starts cinematic and runs to the exit
		if (readyToLeave())
		{
			goToExit();
			//Return probably is not critical, but better be safe for future reimplementation
			return true;
		}
		//If the player requires greeting, he shall receive one
		else if (!this.atExit && this.firstPlayer == player && !this.greetingDone)
		{
			nextGreeting(player);
			this.greetingDone = true;
			return true;
		}
		return false;
	}

	protected boolean haveMessage()
	{
		return /*!this.atExit &&*/ this.currentGreeting < GREETINGS_NUMBER;
	}

	//Shows a new message and deletes a possible previous one
	protected Message newMessage(String headline, String text, Actor actor)
	{
		if (this.currentMessage != null)
		{
			this.currentMessage.remove();
		}
		this.currentMessage = new Message(headline, text, this);
		return this.currentMessage;
	}

	//Shows a new message near Daisy and deletes a possible previous one
	protected Message newMessage(String headline, String text)
	{
		return newMessage(headline, text, this);
	}

	//Devil si vie privlastnit Daisy
	public void belongToDevil(Devil devil)
	{
		if (this.devil == null)
		{
			this.devil = devil;
		}
	}

	//Hrac si implicitne vyziada pozdrav svojou pritomnostou
	protected void nextGreeting(Player player)
	{
		if (haveMessage())
		{
			switch (this.currentGreeting)
			{
				case 0:
					newMessage("Greetings", "Hi " + player.getName() +
					                        "!\nI would really appreciate, if a hero\nlike you could save me from here.");
					break;
				case 1:
					newMessage("Hello",
					           "Welcome back, " + player.getName() + "!\nBut really, can you save me now?\nPleeease!");
					break;
				case 2:
					newMessage(player.getName() + "!",
					           "Why are you back so soon?\nI still can't see myself being saved by you :(");
					break;
				default:
					break;
			}
			this.currentGreeting++;
		}
		else
		{
			newMessage("Come on :(", "If you were some REAL hero,\nI would've been saved thrice already.");
		}
	}

	@Override
	public void act()
	{
		if (this.scheduledPosition[0] == 1)
		{
			setPosition(this.scheduledPosition[1], this.scheduledPosition[2]);
			this.scheduledPosition[0] = 0;
		}

		//If Daisy gets to the exit, the cinematic gets run
		if (this.atExit && this.cinematic != null)
		{
			this.cinematic.act();
		}

		//If the player already received greeting, but has gone too far, he deserves another one
		if
			(
			/*!this.atExit
			&&*/
			this.greetingDone
			&&
			this.firstPlayer != null
			&&
			!isNear(this.firstPlayer, getWidth() * Daisy.FORGETTING_RADIUS,
			        getHeight() * Daisy.FORGETTING_RADIUS)
			)
		{
			this.greetingDone = false;
			this.currentMessage.remove();
		}
	}

	//Ked hrac pekne poprosi Daisy, dostane diamantovy prsten (mozno to ale nie je pravy diamant)
	@Override
	public void use(Actor actor)
	{
		if (actor instanceof Player && this.hasRing)
		{
			Player player = (Player) actor;

			//Prida instanciu prstena do sveta
			this.ring.setPosition(getX() - getWidth(), getY());
			getWorld().addActor(this.ring);

			//Oznam hracovi nastanie danej skutocnosti
			newMessage("Ouch, my finger!",
			           player.getName()                             +
			           ", why did you do this to me!\n"             +
			           "This ancient ring is my family heritage.\n" +
			           "Do you really need to take\n"               +
			           "everything important away from me?",
			           ring);
			this.hasRing = false;
		}
	}
}
