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

import sk.tuke.yolkfolk.collectables.FinalKey;
import sk.tuke.yolkfolk.collectables.Key;

/**
 * FinalDoor that requires FinalKey before allowing a Final cinematic to be shown to a Player (by Daisy).
 * <p/>
 * Created by Steve on 1.1.2016.
 */
public class FinalDoor extends SimpleDoor
{
	//Constants
	public static final String NAME = "FinalDoor";

	//Static link to previous door that was created before this, used for automagical intertwining of doors at creation
	private static Door previousDoor;

	//Staticka inicializacia
	static
	{
		FinalDoor.previousDoor = null;
	}

	//Specific magic door without automagical pairing
	public FinalDoor(Door destination)
	{
		super(FinalDoor.NAME, destination);
	}

	//When this default constructor is used, automagical pairing of doors happens
	public FinalDoor()
	{
		super(FinalDoor.NAME);
	}

	//Method for automagical pairing of doors happens, no pairing happens when it returns null
	@Override
	protected Door getPreviousDoor()
	{
		return FinalDoor.previousDoor;
	}
	//Sets a door as the previous door, that will get automagicaly paired with the next door
	@Override
	protected void setPreviousDoor(Door door)
	{
		FinalDoor.previousDoor = door;
	}

	//Pomocou kluca sa skusia otvorit dvere a metoda vrati hodnotu uspechu
	@Override
	public boolean unlock(Key key)
	{
		if (key instanceof FinalKey)
		{
			open();
			return true;
		}
		return false;
	}
}
