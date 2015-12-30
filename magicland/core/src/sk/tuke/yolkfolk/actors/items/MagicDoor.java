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

package sk.tuke.yolkfolk.actors.items;

import sk.tuke.yolkfolk.actors.player.Player;
import sk.tuke.yolkfolk.collectables.Key;
import sk.tuke.yolkfolk.collectables.MagicKey;

/**
 * Dvere na specialny kluc.
 * <p/>
 * Created by Steve on 27.12.2015.
 */
public class MagicDoor extends SimpleDoor
{
	//Constants
	public static final String NAME = "MagicDoor";

	//Static link to previous door that was created before this, used for automagical intertwining of doors at creation
	private static Door previousDoor;

	//Staticka inicializacia
	static
	{
		MagicDoor.previousDoor = null;
	}

	//Specific magic door without automagical pairing
	public MagicDoor(Door destination)
	{
		super(MagicDoor.NAME, destination);
	}

	//When this default constructor is used, automagical pairing of doors happens
	public MagicDoor()
	{
		super(MagicDoor.NAME);
	}

	//Method for automagical pairing of doors happens, no pairing happens when it returns null
	@Override
	protected Door getPreviousDoor()
	{
		return MagicDoor.previousDoor;
	}
	//Sets a door as the previous door, that will get automagicaly paired with the next door
	@Override
	protected void setPreviousDoor(Door door)
	{
		MagicDoor.previousDoor = door;
	}

	//Pomocou kluca sa skusia otvorit dvere a metoda vrati hodnotu uspechu
	@Override
	public boolean unlock(Key key)
	{
		if (key instanceof MagicKey)
		{
			open();
			return true;
		}
		return false;
	}

	@Override
	protected boolean lockedMessage(Player player)
	{
		return oneMessage(player.getName() + " is frustrated.",
		                  "This locked " + getName() + " can probably lead to\nthe Throne Room of the Prince.", player);
	}
}
