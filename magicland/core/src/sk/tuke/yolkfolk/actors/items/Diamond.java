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

import sk.tuke.gamelib2.Actor;
import sk.tuke.gamelib2.Item;
import sk.tuke.gamelib2.Message;
import sk.tuke.yolkfolk.actors.AbstractActor;
import sk.tuke.yolkfolk.actors.Usable;
import sk.tuke.yolkfolk.actors.player.Player;

/**
 * Obycajny lacny diamant, ktory vylieci Dizzyho.
 * <p/>
 * Created by Steve on 23.11.2015.
 */
public class Diamond extends AbstractActor implements Item, Usable
{
	//Constants
	public static final String name = "Diamond";
	public static final int HEALS_HP = 50;

	//Variables
	//Hodnota diamantu, o kolko sa pocet diamantov zvacsi po jeho zodvihnuti
	private int value;

	private static String constructorAnimation(String color)
	{
		if (color.equals("silver") || color.equals("green"))
		{
			return "sprites/" + color + "diamond.png";
		}
		else //if(color == "blue")
		{
			return "sprites/bluediamond.png";
		}
	}

	public Diamond(String color, int value)
	{
		super(Diamond.name, constructorAnimation(color), 16, 16);
		this.value = value;
	}

	public Diamond(String color)
	{
		this(color, 1);
	}

	//Implicitne je diamant modry
	public Diamond()
	{
		this("blue");
	}

	public int getValue()
	{
		return this.value;
	}

	public void use(Actor actor)
	{
		if (actor instanceof Player)
		{
			Player player = (Player) actor;

			//Collect and remove the diamond from world
			player.setDiamonds(player.getNumberOfDiamonds() + getValue());
			getWorld().removeActor(this);

			//Inform relevant people of the occurred event
			System.out.println("Dizzy haz " + player.getNumberOfDiamonds() + " diamond" +
			                   (player.getNumberOfDiamonds() > 1 ? "s" : "") +
			                   ((player.getNumberOfDiamonds() > 9) ? " and his bag is getting heavy." : "."));
			new Message("Dizzy got a new treasure!", "Well, you find a diamond!", this);

			//Heal our Hero
			if (player.getEnergy() < Player.MAX_HP)
			{
				player.setEnergy(player.getEnergy() + HEALS_HP);
			}
		}
	}

	@Override
	public void act()
	{
	}
}
