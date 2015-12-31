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

import sk.tuke.yolkfolk.actors.AbstractActor;
import sk.tuke.yolkfolk.actors.characters.Daisy;
import sk.tuke.yolkfolk.actors.characters.Devil;

/**
 * A ring is all that Devil needs.
 * <p/>
 * Created by Steve on 28.12.2015.
 */
public class Ring extends AbstractActor implements Collectable
{
	//Constants
	public static final String NAME = "Ring";

	//Objects
	//Pravoplatny vlastnik prstena
	Daisy owner;

	public Ring(Daisy daisy)
	{
		super(Ring.NAME, "sprites/ring.png", 16, 16);
		this.owner = daisy;
	}

	public Ring()
	{
		this(null);
	}

	//Prsten moze byt ukradnuty diablom
	public void stealByDevil(Devil devil)
	{
		if (this.owner != null)
		{
			this.owner.belongToDevil(devil);
		}
		getWorld().removeActor(this);
	}

	@Override
	public void act()
	{
	}
}
