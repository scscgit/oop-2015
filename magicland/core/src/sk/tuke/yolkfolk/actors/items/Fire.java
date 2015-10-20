/***********************************************************
 * Zadanie na predmet Objektove Programovanie
 *
 * scsc
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

import sk.tuke.gamelib2.Item;
import sk.tuke.yolkfolk.actors.AbstractActor;
import sk.tuke.yolkfolk.actors.Cursable;
import sk.tuke.yolkfolk.actors.CurseEvent;
import sk.tuke.yolkfolk.actors.Observer;
import sk.tuke.yolkfolk.actors.player.Player;

/**
 * Ohen.
 * Po nastani kliatby zhasne, aby vzbudil lepsiu atmosferu.
 *
 * Created by Steve on 9.11.2015.
 */
public class Fire extends AbstractActor implements Item, Observer<Cursable>
{
    public Fire()
    {
        super("Fire","sprites/fire.png",16,16);
		CurseEvent.register(this);
    }

	public void notified(Cursable actor)
	{
		if(actor instanceof Player)
		{
			//System.out.println("Player acted on "+getName()+" by his curse.");
			getWorld().removeActor(this);
		}
	}
}