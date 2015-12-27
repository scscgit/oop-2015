/***********************************************************
 * Zadanie na predmet Objektove Programovanie
 *
 * Stefan Ciberaj, ZS 2015/2016
 * Technicka univerzita v Kosiciach, Fakulta elektrotechniky a informatiky
 *
 * Licencia: Volny softver, Open-Source GNU GPL v3+
 * Vseobecna verejna licencia. Program je dovolene volne sirit a upravovat.
 * Upraveny program / cast programu moze ktokolvek vyuzit ako na osobne,
 * tak aj komercne ucely, ale nemoze ho vydat s vlastnym copyrightom,
 * ktory nie je kompatibilny s GNU GPL v3+.
 * gnu.org/licenses/gpl-faq.html
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see < http://www.gnu.org/licenses/ >.
 */

package sk.tuke.yolkfolk.actors.items;

import sk.tuke.gamelib2.Actor;
import sk.tuke.gamelib2.Animation;
import sk.tuke.gamelib2.Item;
import sk.tuke.gamelib2.Message;
import sk.tuke.yolkfolk.actors.AbstractActor;
import sk.tuke.yolkfolk.actors.Usable;
import sk.tuke.yolkfolk.collectables.Key;

/**
 * A generic door, initially closed, unlike most real life doors.
 * <p/>
 * Created by Steve on 27.12.2015.
 */
public class Door extends AbstractActor implements Item, Usable
{
	//Constants
	public static final String name = "Door";

	//Static link to other door, that was created before, used for automagical intertwining of doors at creation
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
		Door.previousDoor = null;
	}

	public Door()
	{
		super(Door.name, "sprites/doorlocked.png", 40, 50);

		//Automagically intertwines all pairs of doors
		if (Door.previousDoor != null)
		{
			intertwineWith(Door.previousDoor);
			Door.previousDoor = null;
		}
		else
		{
			Door.previousDoor = this;
		}
	}

	//Otvori dvere
	protected final void open()
	{
		setAnimation(animationOpen);
		this.unlocked = true;
	}

	//Zatvori dvere na kluc
	protected final void lock()
	{
		setAnimation();
		this.unlocked = false;
	}

	//Zisti stav odomknutia dveri
	public boolean isUnlocked()
	{
		return this.unlocked;
	}

	//Rozhranim na otvaranie dveri bude ich zamok na kluc
	@Override
	public void use(Actor actor)
	{
		//Ak su dvere zamknute, zbrazi sa jedna sprava, ktora pripomenie hracovi pouzit kluc jeho hodenim na zem
		if (!isUnlocked())
		{
			if (this.closedWarning != null)
			{
				this.closedWarning.remove();
			}
			this.closedWarning = new Message(getPlayer().getName() + " is frustrated.",
			                                 "The " + Door.name + " seems to be locked.\nA key would probably help.",
			                                 getPlayer());
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

	//Nastavi cielove dvere
	protected void setDestination(Door door)
	{
		this.destination = door;
	}

	//Kvantovo previaze dve strany dveri, aby cez ne dokazal hrac navzajom prechadzat a aby mali spolocny zamok
	public void intertwineWith(Door door)
	{
		if (door != null)
		{
			setDestination(door);
			door.setDestination(this);
		}
	}

	@Override
	public void act()
	{
		//Ak vedla dveri hrac hodi kluc, a dvere boli zamknute, tak sa magicky otvoria
		if (!isUnlocked())
		{
			for (Actor actor : getWorld())
			{
				if (actor instanceof Key)
				{
					open();
					((Key) actor).removeFromWorld();
					break;
				}
			}
		}
		//Ak su dvere odomknute, tak sa odomkne aj druha strana dveri (aj ked sa navzajom previazu neskor)
		else
		{
			if (this.destination != null && !this.destination.isUnlocked())
			{
				this.destination.open();
			}
		}
	}
}
