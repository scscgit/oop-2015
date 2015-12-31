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

package sk.tuke.yolkfolk.actors.items;

import sk.tuke.gamelib2.Actor;
import sk.tuke.gamelib2.Animation;
import sk.tuke.gamelib2.Message;
import sk.tuke.yolkfolk.actors.AbstractActor;
import sk.tuke.yolkfolk.actors.player.Player;
import sk.tuke.yolkfolk.collectables.Key;
import sk.tuke.yolkfolk.collectables.SimpleKey;

/**
 * A generic door, initially closed, unlike most real life doors.
 * <p/>
 * Created by Steve on 27.12.2015.
 */
public class SimpleDoor extends AbstractActor implements Door
{
	//Constants
	public static final String NAME = "SimpleDoor";

	//Static link to previous door that was created before this, used for automagical intertwining of doors at creation
	private static Door previousDoor;

	//Variables
	private boolean unlocked;
	private Animation animationOpen;
	private Message closedWarning;

	//Objects
	Door destination;

	//Inicializacia
	{
		this.unlocked = false;
		this.animationOpen = new Animation("sprites/dooropen.png", 40, 50);
		this.closedWarning = null;
		this.destination = null;
	}

	//Staticka inicializacia
	static
	{
		SimpleDoor.previousDoor = null;
	}

	//Custom type of door without automagical pairing
	public SimpleDoor(String name, Door destination)
	{
		super(name, "sprites/doorlocked.png", 40, 50);

		this.destination = destination;
	}

	//Custom type of door with automagical pairing
	public SimpleDoor(String name)
	{
		super(name, "sprites/doorlocked.png", 40, 50);

		//Automagically intertwines all pairs of doors
		if (getPreviousDoor() != null)
		{
			intertwineWith(getPreviousDoor());
			setPreviousDoor(null);
		}
		else
		{
			setPreviousDoor(this);
		}
	}

	//Specific door without automagical pairing
	public SimpleDoor(Door destination)
	{
		this(SimpleDoor.NAME, destination);
	}

	//When this default constructor is used, automagical pairing of doors happens
	public SimpleDoor()
	{
		this(SimpleDoor.NAME);
	}

	//Method for automagical pairing of doors happens, no pairing happens when it returns null
	protected Door getPreviousDoor()
	{
		return SimpleDoor.previousDoor;
	}
	//Sets a door as the previous door, thhat will get automagicaly paired with the next door
	protected void setPreviousDoor(Door door)
	{
		SimpleDoor.previousDoor = door;
	}

	//Otvoria sa dvere
	protected void open()
	{
		setAnimation(animationOpen);
		this.unlocked = true;
	}

	//Dvere sa vzdy otvoria pomocou tejto metody, pokial sa otvori druha strana
	public void openFromOtherSide(Door door)
	{
		if (door != null && this.destination == door && door.isUnlocked())
		{
			open();
		}
	}

	//Pomocou kluca sa skusia otvorit dvere a metoda vrati hodnotu uspechu
	public boolean unlock(Key key)
	{
		if (key instanceof SimpleKey)
		{
			open();
			return true;
		}
		return false;
	}

	//Zatvori dvere na kluc, odporuca sa nepouzivat pretoze nevrati kluc a nie je synchronizovane s druhou stranou
	@Deprecated
	protected void lock()
	{
		setAnimation();
		this.unlocked = false;
	}

	//Zobrazenie novej spravy namiesto predchadzajucej
	@Deprecated
	protected void newMessage(String title, String message, Actor actor)
	{
		removeMessage();
		this.closedWarning = new Message(title, message, getPlayer());
	}

	//Odstranenie predchadzajucej spravy
	protected void removeMessage()
	{
		if (this.closedWarning != null)
		{
			this.closedWarning.remove();
		}
	}

	//Chybova sprava dveri bude zobrazena vzdy iba jedna
	protected boolean oneMessage(String title, String message, Actor actor)
	{
		if (this.closedWarning == null)
		{
			this.closedWarning = new Message(title, message, getPlayer());
			return true;
		}
		return false;
	}

	//Zobrazi spravu upozornujucu hraca, ze su dvere zamknute a nema kluc
	protected boolean lockedMessage(Player player)
	{
		return oneMessage(player.getName() + " is frustrated.",
		                  "The " + getName() + " seems to be locked.\nA key would probably help.",
		                  player);
	}

	//Zisti stav odomknutia dveri
	public boolean isUnlocked()
	{
		return this.unlocked;
	}

	//Rozhranim na otvaranie dveri bude ich zamok na kluc a nie ich pouzitie, inak sa cez ne da pouzitim prejst
	@Override
	public void use(Actor actor)
	{
		//Ak su dvere zamknute, zbrazi sa jedna sprava, ktora pripomenie hracovi pouzit kluc jeho hodenim na zem
		//Ak uz spravu videl, otvori sa inventar, kedze Usable item zablokuje jeho otvaranie
		if (!isUnlocked() && actor instanceof Player)
		{
			Player player = (Player) actor;
			if (!lockedMessage(player))
			{
				removeMessage();
				player.showBackpack();
			}
		}
		//Ak su dvere odomknute, tak cez ne moze hrac prejst
		else
		{
			enteredByPlayer();
		}
	}

	//Akcia, ktora sa vykona, ked sa cez otvorene dvere pokusi hrac prejst
	protected void enteredByPlayer()
	{
		if (this.destination != null)
		{
			getPlayer().setPosition(this.destination.getX(), this.destination.getY());
		}
	}

	//Nastavi cielove dvere, z dovodu bezpecnosti sa bude pouzivat iba intertwine
	@Deprecated
	protected void setDestination(Door door)
	{
		this.destination = door;
	}

	//Kvantovo previaze dve strany dveri, aby cez ne dokazal hrac navzajom prechadzat a aby mali spolocny zamok
	public void intertwineWith(Door door)
	{
		if (door != null && door != this.destination)
		{
			this.destination = door;
			door.intertwineWith(this);
		}
	}

	//Pokusi sa najst kluc v blizkosti dveri a otvorit ich
	private void tryToUnlock()
	{
		for (Actor actor : getWorld())
		{
			if (actor instanceof Key && actor.intersects(this) && unlock((Key) actor))
			{
				((Key) actor).teleportOut();
				//((Key) actor).removeFromWorld();
				break;
			}
		}
	}

	@Override
	public void act()
	{
		//Osetrenie chyby kniznice, prvy act sa nema vykonat
		if (getX() == 0 && getY() == 0)
		{
			return;
		}

		//Ak vedla dveri hrac hodi kluc, a dvere boli zamknute, tak sa magicky otvoria (ak, samozrejme, zamok pasuje)
		if (!isUnlocked())
		{
			tryToUnlock();
		}
		//Ak su dvere odomknute, tak sa odomkne aj druha strana dveri (aj ked sa navzajom previazu neskor)
		else if (this.destination != null && !this.destination.isUnlocked())
		{
			this.destination.openFromOtherSide(this);
		}
	}
}
