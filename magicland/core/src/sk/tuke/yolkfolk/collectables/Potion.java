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

import sk.tuke.gamelib2.Actor;
import sk.tuke.yolkfolk.actors.AbstractActor;
import sk.tuke.yolkfolk.actors.characters.SpellCaster;

/**
 * A generic Mana Potion as seen in most MMOs.
 * <p/>
 * Created by Steve on 31.12.2015.
 */
public class Potion extends AbstractActor implements Collectable
{
	public static final String NAME = "Potion";

	public Potion()
	{
		super(Potion.NAME, "sprites/potion.png", 16, 16);
	}

	@Override
	public void act()
	{
		//On interaction with any kind of Witch or Witcher boost their Mana.
		for (Actor actor : getWorld())
		{
			if ((actor instanceof SpellCaster) && actor.intersects(this) && ((SpellCaster) actor).boostMana())
			{
				removeFromWorld();
				break;
			}
		}
	}
}
