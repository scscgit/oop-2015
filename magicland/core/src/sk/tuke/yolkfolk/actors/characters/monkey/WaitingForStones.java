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

package sk.tuke.yolkfolk.actors.characters.monkey;

import sk.tuke.gamelib2.Actor;
import sk.tuke.yolkfolk.actors.State;
import sk.tuke.yolkfolk.collectables.Stone;

/**
 * Stav, v ktorom opica caka na kamene.
 * <p/>
 * Created by Steve on 28.12.2015.
 */
public class WaitingForStones extends AbstractMonkeyState
{
	public WaitingForStones(Monkey monkey)
	{
		super(monkey);
	}

	@Override
	public State setNextState()
	{
		State state = new DispatchingReward(getMonkey());
		getMonkey().setState(state);
		return state;
	}

	public int getStones()
	{
		return getMonkey().getStones();
	}
	public void setStones(int stones)
	{
		getMonkey().setStones(stones);
	}

	@Override
	public void act()
	{
		if (getMonkey().getStones() < Monkey.STONES_REQUIRED)
		{
			//Zodvihne vsetky kamene vo svojom priestore pre kamene
			for (Actor actor : getMonkey().getWorld())
			{
				if (actor instanceof Stone && actor.intersects(getMonkey().getStoneSpace()))
				{
					setStones(getStones() + 1);
					//TeleportOutside hotfix nefunguje, tak budem dufat ze removeActor necrashne
					//NewWorldOrder.teleportOutside(actor);
					getMonkey().getWorld().removeActor(actor);
					break;
				}
			}
		}
		else
		{
			setNextState();
		}
	}
}
