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

package sk.tuke.yolkfolk.collectables;

import sk.tuke.yolkfolk.NewWorldOrder;
import sk.tuke.yolkfolk.actors.AbstractActor;

/**
 * Kluc ku viacerym tajomstvam, z ktorych implementujem iba niektore.
 * <p/>
 * Created by Steve on 28.11.2015.
 */
public class Key extends AbstractActor implements Collectable
{
	//Constants
	public static final String NAME = "Key";

	//Variables
	private boolean moveOutInAct;

	//Initialization
	{
		this.moveOutInAct = false;
	}

	//Konstruktor na vytvorenie specialneho kluca
	public Key(String name)
	{
		super(name, "sprites/key.png", 16, 16);
	}

	public Key()
	{
		this(Key.NAME);
	}

	//Hotfix proti potencialnemu podobnemu crashu, ktory nastava u triedy Devil, nahradzuje removeFromWorld()
	public void teleportOut()
	{
		this.moveOutInAct = true;
	}

	@Override
	public void act()
	{
		if (this.moveOutInAct)
		{
			NewWorldOrder.teleportOutside(this);
			this.moveOutInAct = false;
		}
	}
}
